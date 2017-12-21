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

public class AdvancedExampleAdapter extends ArrayAdapter<AdvancedExampleDataPOJO> {
    private List<AdvancedExampleDataPOJO> items;
    private Context context;

    public AdvancedExampleAdapter(@NonNull Context context, int resource, @NonNull List<AdvancedExampleDataPOJO> objects) {
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
        AdvancedExampleAdapter.ViewHolder holder;

        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.advanced_example_list_item, parent, false);
            holder = new AdvancedExampleAdapter.ViewHolder();
            holder.text = convertView.findViewById(R.id.custom_cell_text);
            holder.icon = convertView.findViewById(R.id.custom_cell_image);
            convertView.setTag(holder);
        } else {
            holder = (AdvancedExampleAdapter.ViewHolder) convertView.getTag();
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
