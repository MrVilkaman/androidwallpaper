package donnu.zolotarev.wallpaper.android.activity;

import android.support.v4.app.Fragment;

import donnu.zolotarev.wallpaper.android.fragments.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
