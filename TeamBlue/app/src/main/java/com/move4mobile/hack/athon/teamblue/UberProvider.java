package com.move4mobile.hack.athon.teamblue;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class UberProvider extends ContentProvider {

    public static final Uri URI_BASE = Uri.parse("content://"+ BuildConfig.AUTHORITY);

    private static final String FORMAT_TYPE = "%s/%s";

    private static final String QUERY_WITH_ON_CONFLICT = "withOnConflict";
    private static final String QUERY_GROUP_BY = "groupBy";
    private static final String QUERY_TABLE_ALIAS = "tableAlias";
    private static final String QUERY_JOIN = "joinClause";
    private static final String QUERY_COUNT = "count";

    private static UriMatcher sMatcher;
    private static final int TYPE_SINGLE = 0;
    private static final int TYPE_DIR = 1;
    static {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(BuildConfig.AUTHORITY, "*/#", TYPE_SINGLE);
        sMatcher.addURI(BuildConfig.AUTHORITY, "*", TYPE_DIR);
    }

    public String getTable(Uri uri) {
        return uri.getPathSegments().get(0);
    }

    private void notifyChanges(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    private String fixSelection(String selection) {
        if(TextUtils.isEmpty(selection)) {
            return "_id=?";
        } else {
            return String.format("_id=? AND (%s)", selection);
        }
    }

    private String[] fixSelectionArgs(String[] selectionArgs, long id) {
        if(selectionArgs==null || selectionArgs.length==0) {
            return new String[] {
                    String.valueOf(id)
            };
        } else {
            String[] fixed = new String[selectionArgs.length+1];
            fixed[0]=String.valueOf(id);
            System.arraycopy(selectionArgs, 0, fixed, 1, selectionArgs.length);
            return fixed;
        }
    }

    private Uri withConflictAlgorithm(Uri uri, int conflictAlgoritm) {
        return uri.buildUpon().appendQueryParameter(QUERY_WITH_ON_CONFLICT, String.valueOf(conflictAlgoritm)).build();
    }

    public static Uri withGroupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }

    public static Uri withGroupByAndTableAlias(Uri uri, String groupBy, String tableAlias) {
        return uri.buildUpon()
                .appendQueryParameter(QUERY_GROUP_BY, groupBy)
                .appendQueryParameter(QUERY_TABLE_ALIAS, tableAlias)
                .build();
    }

    public static Uri withJoinClause(Uri uri, String joinClause) {
        return uri.buildUpon().appendQueryParameter(QUERY_JOIN, joinClause).build();
    }

    public static Uri withCount(Uri uri, int count) {
        return uri.buildUpon().appendQueryParameter(QUERY_COUNT, String.valueOf(count)).build();
    }

    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        mDb = new HackedDatabaseHelper(getContext(), "hacked.db", null, 1).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = getTable(uri);
        String count = uri.getQueryParameter(QUERY_COUNT);
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        String tableAlias = uri.getQueryParameter(QUERY_TABLE_ALIAS);
        if(!TextUtils.isEmpty(tableAlias)) {
            table = table.concat(" ").concat(tableAlias);
        }

        String joinClause = uri.getQueryParameter(QUERY_JOIN);
        if(!TextUtils.isEmpty(joinClause)) {
            table+=" " + joinClause;
        }

        if(sMatcher.match(uri)==TYPE_SINGLE) {
            selection=fixSelection(selection);
            selectionArgs=fixSelectionArgs(selectionArgs, ContentUris.parseId(uri));
        }

        Cursor cursor = mDb.query(
                table,
                projection,
                selection,
                selectionArgs,
                groupBy,
                null,
                sortOrder,
                count
        );
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int type = sMatcher.match(uri);
        String table = getTable(uri);
        switch (type) {
            case TYPE_SINGLE:
                return String.format(FORMAT_TYPE, ContentResolver.CURSOR_ITEM_BASE_TYPE, table);
            case TYPE_DIR:
                return String.format(FORMAT_TYPE, ContentResolver.CURSOR_DIR_BASE_TYPE, table);
            default:
                return String.format(FORMAT_TYPE, "unknown", "unknown");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTable(uri);
        long insertId;
        String conflictAlgorithm = uri.getQueryParameter(QUERY_WITH_ON_CONFLICT);
        if (conflictAlgorithm!=null) {
            insertId = mDb.insertWithOnConflict(
                    table,
                    null,
                    values,
                    Integer.parseInt(conflictAlgorithm)
            );
        } else {
            insertId = mDb.insert(
                    table,
                    null,
                    values
            );
        }
        notifyChanges(uri);
        return ContentUris.withAppendedId(uri, insertId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTable(uri);
        if(sMatcher.match(uri)==TYPE_SINGLE) {
            selection=fixSelection(selection);
            selectionArgs=fixSelectionArgs(selectionArgs, ContentUris.parseId(uri));
        }
        int deletes = mDb.delete(
                table,
                selection,
                selectionArgs
        );
        notifyChanges(uri);
        return deletes;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String table = getTable(uri);
        if(sMatcher.match(uri)==TYPE_SINGLE) {
            selection=fixSelection(selection);
            selectionArgs=fixSelectionArgs(selectionArgs, ContentUris.parseId(uri));
        }
        int updates = mDb.update(
                table,
                values,
                selection,
                selectionArgs
        );
        notifyChanges(uri);
        return updates;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] valueses) {
        mDb.beginTransaction();
        for(ContentValues values : valueses) {
            int count = update(
                    ContentUris.withAppendedId(uri, values.getAsLong("_id")),
                    values,
                    null,
                    null
            );
            if(count==0) {
                insert(uri, values);
            }
        }
        mDb.setTransactionSuccessful();
        mDb .endTransaction();
        return valueses.length;
    }
}
