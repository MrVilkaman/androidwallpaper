package donnu.zolotarev.wallpaper.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import donnu.zolotarev.wallpaper.android.ads.Admob;
import donnu.zolotarev.wallpaper.android.ads.IAds;

public class StoreFragment extends Fragment {

    public static final String TAG = "StoreFragment";

    private IAds ads;

    public StoreFragment() {
        ads = new Admob();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public IAds getAds() {
        return ads;
    }
}
