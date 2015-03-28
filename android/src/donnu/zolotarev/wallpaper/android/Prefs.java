package donnu.zolotarev.wallpaper.android;

import android.os.Build;
import android.os.Bundle;
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
        }
        
    } // MyPreferenceFragment

} // Prefs
