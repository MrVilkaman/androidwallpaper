package donnu.zolotarev.wallpaper.android.ads;

import android.app.Activity;
import android.view.View;

public interface IAds {


    boolean showBigBanner();
    void showBanner();
    void hileBanner();
    void onResume();
    void onPause();
    void onDestroy();

    View getBannerView();

    void setContext(Activity context);
}
