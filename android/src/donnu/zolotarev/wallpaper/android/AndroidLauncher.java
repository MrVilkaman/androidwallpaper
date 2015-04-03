package donnu.zolotarev.wallpaper.android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;

import donnu.zolotarev.wallpaper.WallPaper;

/** @author cb ** http://vk.com/id17317 **/

public class AndroidLauncher extends AndroidLiveWallpaperService implements SharedPreferences.OnSharedPreferenceChangeListener  {

	static SharedPreferences mySharedPreferences;
    MyLiveWallpaperListener listener;

    @Override
    public void onCreateApplication () {
        super.onCreateApplication();

        final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.disableAudio = true;
        config.useCompass = false;

        listener = new MyLiveWallpaperListener();
        initialize(listener, config);

        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mySharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Вручную вызываем метод, чтобы инициализировать настройки приложения
        listener.onPreferenceChanged(mySharedPreferences);
        System.gc();

    } // onCreateApplication


    
    public static class MyLiveWallpaperListener extends WallPaper implements AndroidWallpaperListener {
    	
            @Override
            public void offsetChange (float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset)
            {
            	setScreenOffset(xOffset);
            }

            @Override
            public void previewStateChange (boolean isPreview) { }

            /**
             * Передача настроек в MAIN-проект
             */
			public void onPreferenceChanged( SharedPreferences sp ) {

                boolean water = sp.getBoolean("ripple",true);
                boolean moveripple = sp.getBoolean("moveripple", true);
                String customImage = sp.getString("customPhoto", "");
                int time = Integer.parseInt(sp.getString("time", "5"));
                boolean rain = sp.getBoolean("rain", false);
                int rainTime = Integer.parseInt(sp.getString("rainTime","5"));

				/*scene = parseIntValue(sp, "scene",  "1");
				cam_actors = parseIntValue(sp, "camera",  "2");
				scene_color = parseIntValue(sp, "scene_color",  "6");
				bg_color = parseIntValue(sp, "bg_color",  "3");
				scene_spec = sp.getBoolean("spec", true);
				bg_fog = sp.getBoolean("fog", true);
				*/
                setSettingChanged(time,water,moveripple,rain,rainTime,customImage);
				//settings_changed_flag = true; // Ставим флаг, чтобы приложение узнало, что настроки изменились
			}
			
    } // MyLiveWallpaperListener

    
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// Вручную вызываем нужный нам метод
		listener.onPreferenceChanged(sharedPreferences);
		
	}
}
