package donnu.zolotarev.wallpaper.android.ads;

import android.content.Context;
import android.view.View;

public interface IAds {


    void showBigBanner();
    void showBanner();
    void hileBanner();
    void onResume();
    void onPause();
    void onDestroy();

    View getBannerView();

    void setContext(Context context);
}
