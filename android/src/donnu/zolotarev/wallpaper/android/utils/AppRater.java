package donnu.zolotarev.wallpaper.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.afollestad.materialdialogs.MaterialDialog;

import donnu.zolotarev.wallpaper.android.R;

public class AppRater {

    private final static int DAYS_UNTIL_PROMPT = 4;
    private final static int LAUNCHES_UNTIL_PROMPT = 10;
    private static final int DAY_IN_MILISEC = 86400000;//24 * 60 * 60 * 1000;

    public static boolean appLaunched(Activity mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return true; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT+1) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * DAY_IN_MILISEC)) {
                showRateDialog(mContext, editor);
                editor.putLong("launch_count", 0);
                editor.commit();
                return false;
            }
        }

        editor.apply();
        return true;
    }

    public static void showRateDialog(final Activity mContext, final SharedPreferences.Editor editor) {

        new MaterialDialog.Builder(mContext)
                .title(R.string.please_rate_me)
                .titleColorRes(R.color.text_color)
                .contentColorRes(R.color.text_color)
                .positiveColorRes(R.color.btndialog_yes_btn_color)
                .negativeColorRes(R.color.text_color)
                .content(R.string.rate_rate)
                .positiveText(R.string.rate_yes)
                .negativeText(R.string.rate_letter)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (editor != null) {
                            editor.putBoolean("dontshowagain", true);
                            editor.commit();
                        }
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_PNAME)));
                        dialog.dismiss();
                        mContext.finish();
                    }
                })
                .show();

    }
}
