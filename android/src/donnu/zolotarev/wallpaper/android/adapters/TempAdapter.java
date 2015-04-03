package donnu.zolotarev.wallpaper.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import donnu.zolotarev.wallpaper.android.R;
import donnu.zolotarev.wallpaper.android.models.ListViewItems;

public class TempAdapter extends BaseAdapter {

    //todo change SomeItem
    private final ArrayList<ListViewItems> items;
    private final LayoutInflater layoutInflater;

    public TempAdapter(Activity context, ArrayList<ListViewItems> items) {
       this.items = items;
       layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public ListViewItems getSomeItem(int i){
        return (ListViewItems)getItem(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
           view = inflateNewView(viewGroup);
        }
        ViewHolder holder = (ViewHolder)view.getTag();
        ListViewItems someItem = getSomeItem(i);
        holder.title.setText(someItem.getTitle());
        return view;
    }

    private View inflateNewView(ViewGroup parent) {
        View view  = layoutInflater.inflate(R.layout.item_list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    public void click(int position) {
        getSomeItem(position).execute();
    }

    private static class ViewHolder {

        private final TextView title;

        public ViewHolder(View view) {
           title =  ButterKnife.findById(view, R.id.text);
        }
    }
}
