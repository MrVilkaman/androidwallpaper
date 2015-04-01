package donnu.zolotarev.wallpaper.android;

import android.app.Application;

import ru.elifantiev.android.roboerrorreporter.RoboErrorReporter;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboErrorReporter.bindReporter(this);
    }
}
