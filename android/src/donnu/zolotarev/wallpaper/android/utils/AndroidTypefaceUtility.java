package donnu.zolotarev.wallpaper.android.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public  class AndroidTypefaceUtility
{

    //Refer to the code block beneath this one, to see how to create a typeface.
    public static void SetTypefaceOfView(View view, Typeface customTypeface) throws Exception {
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
                SetTypefaceOfViewGroup((ViewGroup)view, customTypeface);
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

    public static void SetTypefaceOfViewGroup(ViewGroup layout, Typeface customTypeface) throws Exception {
        if (customTypeface != null && layout != null)
        {
            for (int i = 0; i < layout.getChildCount(); i++)
            {
                SetTypefaceOfView(layout.getChildAt(i), customTypeface);
            }
        }
        else
        {
//            Console.Error.WriteLine("AndroidTypefaceUtility: customTypeface / layout parameter should not be null");
        }
    }

}
