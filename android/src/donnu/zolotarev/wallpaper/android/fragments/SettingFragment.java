package donnu.zolotarev.wallpaper.android.fragments;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rey.material.widget.Switch;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.wallpaper.android.AndroidLauncher;
import donnu.zolotarev.wallpaper.android.PhotoUtils;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.fragments.Dialogs.AlertDialogRadio;
import donnu.zolotarev.wallpaper.android.fragments.Dialogs.ImageDialogRadio;
import donnu.zolotarev.wallpaper.android.utils.Constants;
import donnu.zolotarev.wallpaper.android.utils.Utils;

import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.FONT_ROBOTO_LIGHT;
import static donnu.zolotarev.wallpaper.android.utils.AndroidTypefaceUtility.setTypefaceOfView;

public class SettingFragment extends BaseFragment {

    @InjectView(R.id.main_set_wallpaper)
    Button setWallPaperBtn;

    @InjectView(R.id.setting_list_water)
    Switch waterRipple;

    @InjectView(R.id.setting_list_ripple_mode)
    Switch rippleInMove;

    private SharedPreferences setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = injectView(R.layout.fragment_setting,inflater, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setTitle(R.string.main_settings);
        setting = PreferenceManager.getDefaultSharedPreferences(getActivity());

        loadFonts();
       updateUI();
    }

    private void loadFonts() {
        try {
            setTypefaceOfView(getActivity(), setWallPaperBtn, FONT_ROBOTO_LIGHT);
            setTypefaceOfView(getActivity(), ButterKnife.findById(getView(), R.id.setting_list_top_market_title), FONT_ROBOTO_LIGHT);
        } catch (Exception e) {
        }
    }

    private void updateUI() {
        waterRipple.setChecked(setting.getBoolean("ripple",true));
        rippleInMove.setChecked(setting.getBoolean("moveripple", false));
    }

    @OnClick(R.id.setting_list_water)
    void onClickWater(){
        setting.edit()
                .putBoolean("ripple", waterRipple.isChecked())
                .commit();
    }

    @OnClick(R.id.setting_list_ripple_mode)
    void onClickRipple(){
        setting.edit()
                .putBoolean("moveripple", rippleInMove.isChecked())
                .commit();
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

        AlertDialogRadio alert = AlertDialogRadio.get(R.array.change_image_time,R.array.change_image_time_value,setting.getString("time",""),R.string.setting_list_change_image_time);
        alert.show(getFragmentManager(), "alert_dialog_radio");
        alert.setOnClickListener(new AlertDialogRadio.AlertPositiveListener() {
            @Override
            public void onPositiveClick(String newVal) {
                setting.edit()
                        .putString("time", newVal)
                        .commit();
            }
        });
    }

    @OnClick(R.id.setting_list_rain_time)
    void onRainTime(){
        AlertDialogRadio alert = AlertDialogRadio.get(R.array.rain_time,R.array.rain_time_value,setting.getString("rainTime",""), R.string.setting_list_rain_time);
        alert.show(getFragmentManager(), "alert_dialog_radio");
        alert.setOnClickListener(new AlertDialogRadio.AlertPositiveListener() {
            @Override
            public void onPositiveClick(String newVal) {
                setting.edit()
                        .putString("rainTime", newVal)
                        .commit();
            }
        });
    }

    @OnClick(R.id.setting_list_restore)
    void onRestore(){
        setting.edit().clear().commit();
        updateUI();
        setting.edit()
                .putBoolean("moveripple", rippleInMove.isChecked())
                .commit();
    }


    @OnClick(R.id.setting_list_more_apps)
    void onMoreApps(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MORE_APPS)));
    }

    @InjectView(R.id.setting_list_set_custom_image_text)
    TextView imageTextView;

    @OnClick(R.id.setting_list_set_custom_image)
    void onSetCustomImage(){

        int pos = getImagePos();

        ImageDialogRadio alert = ImageDialogRadio.get(R.string.setting_list_set_custom_image,pos);
        alert.show(getFragmentManager(), "alert_dialog_radio");
        alert.setOnClickListener(new ImageDialogRadio.AlertPositiveListener() {
            @Override
            public void onPositiveClick(int newVal) {
                if (newVal == -1) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .edit()
                            .putString("customPhoto", "")
                            .commit();
                } else if (newVal != -2) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .edit()
                            .putString("customPhoto", "#" + newVal)
                            .commit();
                }
            }

            @Override
            public void onChoose() {
                PhotoUtils.importInGalery(SettingFragment.this, PhotoUtils.IN_GALLERY, PhotoUtils.TEMP_NAME);
            }
        });
       /* if (PhotoUtils.getLastPhotoPath().isEmpty()) {
            PhotoUtils.importInGalery(this, PhotoUtils.IN_GALLERY, PhotoUtils.TEMP_NAME);
        } else {
            PhotoUtils.clearLastPhotoPath();
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putString("customPhoto", PhotoUtils.getLastPhotoPath())
                    .commit();
            imageTextView.setText(R.string.setting_list_set_custom_image);
        }*/
    }

    private int getImagePos() {
        String s = setting.getString("customPhoto", "");
        if (s.isEmpty()) {
            return -1;
        }
        if (s.charAt(0) == '#') {
            s =  s.substring(1,s.length());
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoUtils.IN_GALLERY) {
                PhotoUtils.onActivityResult(getActivity(), requestCode, resultCode, data);
                PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .edit()
                        .putString("customPhoto", PhotoUtils.getLastPhotoPath())
                        .commit();
                if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                    imageTextView.setText(R.string.setting_list_set_custom_image);
                } else {
                    imageTextView.setText(R.string.setting_list_clear_custom_photo);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_setting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_share_app:
                Utils.share(getActivity(), getString(R.string.share_text));
                break;
        }

        return false;
    }
}
