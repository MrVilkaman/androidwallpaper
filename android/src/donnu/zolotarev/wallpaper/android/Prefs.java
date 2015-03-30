package donnu.zolotarev.wallpaper.android;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


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
    	
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
            Preference button = (Preference)getPreferenceManager().findPreference("setwall");
            if (button != null)
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
        
    } // MyPreferenceFragment

} // Prefs
