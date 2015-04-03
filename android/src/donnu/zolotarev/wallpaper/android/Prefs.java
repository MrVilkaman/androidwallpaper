package donnu.zolotarev.wallpaper.android;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


public class Prefs extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Старый код
        if( Build.VERSION.SDK_INT < 11 )
            addPreferencesFromResource(R.xml.prefs);
        // Новый код
        else 
        	getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


        
    } // onCreate

    public static class MyPreferenceFragment extends PreferenceFragment{

        private Preference customimage;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);

            customImage();
            setWallpaperButton();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            PhotoUtils.onActivityResult(getActivity(), requestCode, resultCode, data);
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putString("customPhoto",PhotoUtils.getLastPhotoPath())
                    .commit();
            if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                customimage.setTitle(R.string.set_custom_image);
            }else {
                customimage.setTitle(R.string.clear_custom_photo);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        private void customImage() {
            customimage = (Preference)getPreferenceManager().findPreference("customimage");
            if (customimage != null) {
                customimage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
                        if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                            PhotoUtils.importInGalery(MyPreferenceFragment.this, PhotoUtils.IN_GALLERY, PhotoUtils.TEMP_NAME);
                        } else {
                            PhotoUtils.clearLastPhotoPath();
                            PreferenceManager.getDefaultSharedPreferences(getActivity())
                                    .edit()
                                    .putString("customPhoto", PhotoUtils.getLastPhotoPath())
                                    .commit();
                            customimage.setTitle(R.string.set_custom_image);
                        }
                        return true;
                    }
                });
            }
        }

        private void setWallpaperButton() {
            Preference button = (Preference)getPreferenceManager().findPreference("setwall");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference arg0) {
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
                        return true;
                    }
                });
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                customimage.setTitle(R.string.set_custom_image);
            }else {
                customimage.setTitle(R.string.clear_custom_photo);
            }
        }
    } // MyPreferenceFragment

} // Prefs
