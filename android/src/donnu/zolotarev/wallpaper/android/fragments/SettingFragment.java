package donnu.zolotarev.wallpaper.android.fragments;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.wallpaper.android.AndroidLauncher;
import donnu.zolotarev.wallpaper.android.R;

public class SettingFragment extends BaseFragment {

    @InjectView(R.id.setting_list_water)
    com.rey.material.widget.Switch waterRipple;

    @InjectView(R.id.setting_list_ripple_mode)
    com.rey.material.widget.Switch rippleInMove;

    private SharedPreferences setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = injectView(R.layout.fragment_setting,inflater, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setTitle(R.string.main_settings);
        setting = PreferenceManager.getDefaultSharedPreferences(getActivity());

        waterRipple.setChecked(setting.getBoolean("ripple",true));
        rippleInMove.setChecked(setting.getBoolean("moveripple",false));
    }

    @OnClick(R.id.main_set_wallpaper)
    void onSetWallpaper(){
        Intent i = new Intent();
        if (Build.VERSION.SDK_INT > 15) {
            i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

            String p = AndroidLauncher.class.getPackage().getName();
            String c = AndroidLauncher.class.getCanonicalName();
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(p, c));
        } else {
            i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
        startActivityForResult(i, 0);
    }

    @OnClick(R.id.setting_list_change_image_time)
    void onChangeTime(){

    }

    @OnClick(R.id.setting_list_rain_time)
    void onRainTime(){

    }

    @OnClick(R.id.setting_list_set_custom_image)
    void onSetCustomImage(){

    }
}
