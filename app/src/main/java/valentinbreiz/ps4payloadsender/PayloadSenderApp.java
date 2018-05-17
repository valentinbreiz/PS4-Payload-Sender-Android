package valentinbreiz.ps4payloadsender;

import android.app.Application;
import android.content.ContextWrapper;

import valentinbreiz.ps4payloadsender.Utils.FontsOverride;
import valentinbreiz.ps4payloadsender.Utils.Prefs;

public class PayloadSenderApp extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        FontsOverride.setDefaultFont(this, "DEFAULT", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "SF/SF-Pro-Display-Light.otf");
    }
}
