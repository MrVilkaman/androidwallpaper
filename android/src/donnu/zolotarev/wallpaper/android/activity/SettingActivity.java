package donnu.zolotarev.wallpaper.android.activity;

import android.support.v4.app.Fragment;

import donnu.zolotarev.wallpaper.android.fragments.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }
}
