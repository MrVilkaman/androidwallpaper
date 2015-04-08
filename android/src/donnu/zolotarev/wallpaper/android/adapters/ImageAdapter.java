package donnu.zolotarev.wallpaper.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.R;

public class ImageAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    private int selectedIndex;

    public ImageAdapter(Activity activity) {
        super(activity, R.layout.dialog_image);
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflateNewView(parent);
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
      //  ListViewItems someItem = getSomeItem(i);
        holder.image.setImageResource(R.drawable.image3);


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
        notifyDataSetInvalidated();
    }

    private View inflateNewView(ViewGroup parent) {
        View view  = layoutInflater.inflate(R.layout.dialog_image, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }


    private static class ViewHolder {

        private final ImageView image;
        private final RadioButton radio;

        public ViewHolder(View view) {
            image =  ButterKnife.findById(view, R.id.image);
            radio =  ButterKnife.findById(view, R.id.radioButton);
        }
    }
}
