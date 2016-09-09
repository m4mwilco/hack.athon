package com.move4mobile.hack.athon.teamblue;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class ProductContract extends BaseContract {
    public static final String TABLE = "product";
    public static final Uri URI = UberProvider.URI_BASE.buildUpon().appendPath(TABLE).build();

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String IMAGE = "image";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `" + TABLE + "` (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                DESCRIPTION + " TEXT NOT NULL, " +
                CATEGORY + " TEXT NOT NULL, " +
                PRICE + " INTEGER NOT NULL DEFAULT 0, " +
                IMAGE + " TEXT NOT NULL" +
                ");" );
    }
}
