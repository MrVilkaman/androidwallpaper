package donnu.zolotarev.wallpaper.android;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import donnu.zolotarev.wallpaper.android.fragments.MainFragment;


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
        	getFragmentManager().beginTransaction().replace(android.R.id.content, new MainFragment()).commit();


        
    } // onCreate

    public static class MyPreferenceFragment extends PreferenceFragment {

        private static final String APP_PNAME = "market://details?id=donnu.zolotarev.rushgame.android";
        private static final String MORE_APPS = "market://search?q=Zahar+Zolotarev";

        private Preference customimage;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
            customImage();
            rateUs();
            moreApps();
            shareIt();
            setWallpaperButton();
        }

        private void moreApps() {

            Preference button = (Preference) getPreferenceManager().findPreference("more_apps");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MORE_APPS)));
                        return false;
                    }
                });
            }
        }

        private void shareIt() {
            Preference button = (Preference) getPreferenceManager().findPreference("share");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        share(getActivity(), getString(R.string.share_text));
                        return false;
                    }
                });
            }
        }

        private void rateUs() {
            Preference button = (Preference) getPreferenceManager().findPreference("rateus");
            if (button != null) {
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(APP_PNAME)));
                        return false;
                    }
                });
            }

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            PhotoUtils.onActivityResult(getActivity(), requestCode, resultCode, data);
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putString("customPhoto", PhotoUtils.getLastPhotoPath())
                    .commit();
            if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                customimage.setTitle(R.string.set_custom_image);
            } else {
                customimage.setTitle(R.string.clear_custom_photo);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

        private void customImage() {
            customimage = (Preference) getPreferenceManager().findPreference("customimage");
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
            Preference button = (Preference) getPreferenceManager().findPreference("setwall");
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

        @Override
        public void onResume() {
            super.onResume();
            if (PhotoUtils.getLastPhotoPath().isEmpty()) {
                customimage.setTitle(R.string.set_custom_image);
            } else {
                customimage.setTitle(R.string.clear_custom_photo);
            }
        }

    } // MyPreferenceFragment


    public static void share(Activity context, String text){

        Resources resources = context.getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, text); /*Html.fromHtml(resources.getString(R.string.share_email_native)));*/
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));
        emailIntent.setType("message/rfc822");

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");


        Intent openInChooser = Intent.createChooser(emailIntent, resources.getString(R.string.share_chooser_text));

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if(packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert intentList to array
        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        context.startActivity(openInChooser);
    }
} // Prefs
