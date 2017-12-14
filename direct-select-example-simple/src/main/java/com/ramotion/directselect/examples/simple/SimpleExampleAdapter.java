package com.ramotion.directselect.examples.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.directselect.R;

import java.util.List;

public class SimpleExampleAdapter extends ArrayAdapter<SimpleExampleDataPOJO> {
    private List<SimpleExampleDataPOJO> items;
    private Context context;

    public SimpleExampleAdapter(@NonNull Context context, int resource, @NonNull List<SimpleExampleDataPOJO> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SimpleExampleAdapter.ViewHolder holder;

        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.simple_example_list_item, null);
            holder = new SimpleExampleAdapter.ViewHolder();
            holder.text = convertView.findViewById(R.id.list_item_text);
            holder.icon = convertView.findViewById(R.id.list_item_img);
            convertView.setTag(holder);
        } else {
            holder = (SimpleExampleAdapter.ViewHolder) convertView.getTag();
        }

        if (null != holder) {
            holder.text.setText(items.get(position).getTitle());
            holder.icon.setImageResource(items.get(position).getIcon());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }

}
