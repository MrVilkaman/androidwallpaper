package donnu.zolotarev.wallpaper.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import donnu.zolotarev.wallpaper.android.R;

public class Utils {

    public static void open(Activity context, String url){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void sendEmail(Activity context){
        Intent gmail = new Intent(Intent.ACTION_VIEW);
        gmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        gmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"ericpoleo@gmail.com"});
        gmail.setData(Uri.parse("ericpoleo@gmail.com"));
        gmail.putExtra(Intent.EXTRA_SUBJECT, "App rate!");
        gmail.setType("plain/text");
        context. startActivity(gmail);
    }

    public static void share(Activity context, String text){

        Resources resources = context.getResources();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, resources.getString(R.string.share_chooser_text)));

      /*

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, text); *//*Html.fromHtml(resources.getString(R.string.share_email_native)));*//*
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
        context.startActivity(openInChooser);*/
    }
}
