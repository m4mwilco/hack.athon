package com.move4mobile.hack.athon.teamblue.util;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListenableAppCompatActivity extends AppCompatActivity {

    private MultiListener multiListener = new MultiListener();
    List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        multiListener.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        multiListener.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        multiListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        multiListener.onPostResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        multiListener.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        multiListener.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        multiListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        multiListener.onDestroy();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        multiListener.onContentChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        multiListener.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        multiListener.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        multiListener.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        multiListener.onBackPressed();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        multiListener.onLowMemory();
    }

    private class MultiListener implements Listener {
        private void forEach(Function function) {
            for (int i = listeners.size() - 1; i >= 0; i--) {
                Listener listener = listeners.get(i);
                function.apply(listener);
            }
        }

        @Override
        public void onCreate(@Nullable final Bundle savedInstanceState) {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onCreate(savedInstanceState);
                }
            });
        }

        @Override
        public void onPostCreate(@Nullable final Bundle savedInstanceState) {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onPostCreate(savedInstanceState);
                }
            });
        }

        @Override
        public void onConfigurationChanged(final Configuration newConfig) {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onConfigurationChanged(newConfig);
                }
            });
        }

        @Override
        public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onActivityResult(requestCode, resultCode, data);
                }
            });
        }

        @Override
        public void onPostResume() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onPostResume();
                }
            });
        }

        @Override
        public void onStart() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onStart();
                }
            });
        }

        @Override
        public void onStop() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onStop();
                }
            });
        }

        @Override
        public void onDestroy() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onDestroy();
                }
            });
        }

        @Override
        public void onContentChanged() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onContentChanged();
                }
            });
        }

        @Override
        public void onSaveInstanceState(final Bundle outState) {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onSaveInstanceState(outState);
                }
            });
        }

        @Override
        public void onPause() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onPause();
                }
            });
        }

        @Override
        public void onResume() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onResume();
                }
            });
        }

        @Override
        public void onBackPressed() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onBackPressed();
                }
            });
        }

        @Override
        public void onLowMemory() {
            forEach(new Function() {
                @Override
                public void apply(Listener listener) {
                    listener.onLowMemory();
                }
            });
        }
    }

    private interface Function {
        void apply(Listener listener);
    }

    public interface Listener {

        void onCreate(@Nullable Bundle savedInstanceState);

        void onPostCreate(@Nullable Bundle savedInstanceState);

        void onConfigurationChanged(Configuration newConfig);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onPostResume();

        void onStart();

        void onStop();

        void onDestroy();

        void onContentChanged();

        void onSaveInstanceState(Bundle outState);

        void onPause();

        void onResume();

        void onBackPressed();

        void onLowMemory();

    }

    public static class BaseListener implements Listener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onPostCreate(@Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

        @Override
        public void onPostResume() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onContentChanged() {

        }

        @Override
        public void onSaveInstanceState(Bundle outState) {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onBackPressed() {

        }

        @Override
        public void onLowMemory() {

        }
    }
}
