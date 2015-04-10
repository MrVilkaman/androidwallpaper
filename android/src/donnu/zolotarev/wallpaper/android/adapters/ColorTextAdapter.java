package donnu.zolotarev.wallpaper.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.R;

public class ColorTextAdapter extends ArrayAdapter<String> {
    private final LayoutInflater layoutInflater;
    private int selectedIndex;

    public ColorTextAdapter(Activity activity) {
        super(activity, R.layout.dialog_image);
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflateNewView(parent);
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.textView.setText(getItem(position));

        if(selectedIndex == position){
            holder.radio.setChecked(true);
        }
        else{
            holder.radio.setChecked(false);
        }
        return convertView;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    private View inflateNewView(ViewGroup parent) {
        View view  = layoutInflater.inflate(R.layout.view_simple_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    private static class ViewHolder {

        private final TextView textView;
        private final RadioButton radio;

        public ViewHolder(View view) {
            textView =  ButterKnife.findById(view, R.id.text);
            radio =  ButterKnife.findById(view, R.id.radioButton);
        }
    }
}
