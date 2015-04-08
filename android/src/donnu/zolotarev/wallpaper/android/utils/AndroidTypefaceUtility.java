package donnu.zolotarev.wallpaper.android.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public  class AndroidTypefaceUtility {

    private static final String FONTS_FOLDER = "fonts/";
    public static final String FONT_ROBOTO_THIN = FONTS_FOLDER+"Roboto-Thin.ttf";
    public static final String FONT_ROBOTO_LIGHT = FONTS_FOLDER+"Roboto-Light.ttf";
    public static final String FONT_ROBOTO_BOLD = FONTS_FOLDER+"Roboto-Bold.ttf";

    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);


    //Refer to the code block beneath this one, to see how to create a typeface.
    public static void setTypefaceOfView(Context context, View view, String typeface) throws Exception {
        Typeface customTypeface = getFont(context, typeface);

        if (customTypeface != null && view != null)
        {
            try
            {
                if (view instanceof TextView)
                ((TextView)view).setTypeface(customTypeface);
                /*else if (view instanceof Button)
                    ((Button)view).setTypeface(customTypeface);
                else if (view instanceof EditText)
                    ((EditText)view).setTypeface(customTypeface);*/
                else if (view instanceof ViewGroup)
                setTypefaceOfViewGroup(context, (ViewGroup) view, typeface);
//                Console.Error.WriteLine("AndroidTypefaceUtility: {0} is type of {1} and does not have a typeface property", view.Id, typeof(View));
            }
            catch (Exception ex)
            {
//                Console.Error.WriteLine("AndroidTypefaceUtility threw:\n{0}\n{1}", ex.GetType(), ex.StackTrace);
                throw ex;
            }
        }
        else
        {
//            Console.Error.WriteLine("AndroidTypefaceUtility: customTypeface / view parameter should not be null");
        }
    }

    public static Typeface getFont(Context context, String typefaceName) {
        Typeface mTypeface = sTypefaceCache.get(typefaceName);

        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getApplicationContext()
                    .getAssets(), typefaceName);
            sTypefaceCache.put(typefaceName, mTypeface);
        }
        return mTypeface;
    }

    public static void setTypefaceOfViewGroup(Context context, ViewGroup layout, String typeface) throws Exception {
        if (typeface != null && layout != null)
        {
            for (int i = 0; i < layout.getChildCount(); i++)
            {
                setTypefaceOfView(context, layout.getChildAt(i), typeface);
            }
        }
        else
        {
//            Console.Error.WriteLine("AndroidTypefaceUtility: customTypeface / layout parameter should not be null");
        }
    }

}
