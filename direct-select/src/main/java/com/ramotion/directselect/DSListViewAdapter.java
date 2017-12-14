package com.ramotion.directselect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Default implementation of ArrayAdapter for DSPickerView
 */
public class DSListViewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;
    private Integer cellHeight;

    DSListViewAdapter(Context context, int textViewResourceId, ArrayList<String> stringItems, Integer cellHeight) {
        super(context, textViewResourceId, stringItems);
        this.items = stringItems;
        this.context = context;
        this.cellHeight = cellHeight;
    }

    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private class ViewHolder {
        TextView text;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.ds_default_list_item, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.list_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != holder) {
            holder.text.setText("" + items.get(position));

            convertView.setMinimumHeight(cellHeight);

//            // Default adapter works with picker configured from XML with default cell layout, so we need to set a proper cell height here.
//            if (holder.text.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
//                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.text.getLayoutParams();
//                p.height = cellHeight;
//                holder.text.setLayoutParams(p);
//                holder.text.requestLayout();
//            }
        }
        return convertView;
    }
}
