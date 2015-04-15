package donnu.zolotarev.wallpaper.android;

import android.app.Application;

import com.ironsource.mobilcore.MobileCore;

import donnu.zolotarev.wallpaper.android.utils.Constants;
import ru.elifantiev.android.roboerrorreporter.RoboErrorReporter;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboErrorReporter.bindReporter(this);
        MobileCore.init(this, Constants.MOBICORE_DEV_HASH, MobileCore.LOG_TYPE.DEBUG, MobileCore.AD_UNITS.INTERSTITIAL);
    }
}
