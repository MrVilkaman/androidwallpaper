package donnu.zolotarev.wallpaper.android.fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogRadio extends DialogFragment {

    public static final String VALUE = "VALUE";
    public static final String NAMES = "NAMES";
    public static final String VALUES = "VALUES";

    public static AlertDialogRadio get(int names, int values, String value) {
        AlertDialogRadio radio = new AlertDialogRadio();
        Bundle b = new Bundle();
        b.putString(VALUE,value);
        b.putInt(NAMES, names);
        b.putInt(VALUES, values);
        radio.setArguments(b);
        return radio;
    }

    public  interface AlertPositiveListener {
        public void onPositiveClick(String newVal);
    }

    private AlertPositiveListener alertPositiveListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int pos = 0;
        int res = bundle.getInt(NAMES);
        String val = bundle.getString(VALUE);
        String[] values = getResources().getStringArray(bundle.getInt(VALUES));
        for (int i = 0;i<values.length;i++){
            if (values[i].equals(val)) {
                pos = i;
                break;
            }
        }

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Choose your version");
        b.setSingleChoiceItems(res, pos, null);
        b.setPositiveButton("OK", null);
        AlertDialog d = b.create();
        return d;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        AlertDialog alert = (AlertDialog)getDialog();
        int position = alert.getListView().getCheckedItemPosition();
        Bundle bundle = getArguments();
        String val = bundle.getString(VALUE);
        String[] values = getResources().getStringArray(bundle.getInt(VALUES));
        alertPositiveListener.onPositiveClick(values[position]);
        super.onDismiss(dialog);
    }

    public void setOnClickListener(AlertPositiveListener listener){
        alertPositiveListener = listener;
    }


}
