package com.ramotion.directselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Default implementation of ArrayAdapter for DSListView
 * Used by default for strings with default cell layout through simple and clean xml configuration
 */
public class DSDefaultAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;

    // Things to provide proper cell configuration comparing to configuration of picker box element
    // Any custom implementation doesn't need this three vars
    private int cellHeight;
    private int cellTextSize;
    private DSDefaultPickerBox dsPickerBoxDefault;

    DSDefaultAdapter(Context context, int textViewResourceId, ArrayList<String> stringItems, Integer cellHeight, Integer cellTextSize) {
        super(context, textViewResourceId, stringItems);
        this.items = stringItems;
        this.context = context;
        this.cellHeight = cellHeight;
        this.cellTextSize = cellTextSize;
    }

    void setDsPickerBoxDefault(DSDefaultPickerBox dsPickerBoxDefault) {
        this.dsPickerBoxDefault = dsPickerBoxDefault;
    }

    void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    void setCellTextSize(Integer cellTextSize) {
        this.cellTextSize = cellTextSize;
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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        DSDefaultAdapter.ViewHolder holder;

        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.ds_default_list_item, parent, false);
            holder = new DSDefaultAdapter.ViewHolder();
            holder.text = convertView.findViewById(R.id.ds_default_cell_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != holder) {
            holder.text.setText(items.get(position));

            if (cellHeight > 0)
                convertView.setMinimumHeight(cellHeight);

            if (cellTextSize > 0)
                holder.text.setTextSize(cellTextSize);

            if (null != this.dsPickerBoxDefault)
                convertView.setPadding(dsPickerBoxDefault.getPaddingLeft(), dsPickerBoxDefault.getPaddingTop(),
                        dsPickerBoxDefault.getPaddingRight(), dsPickerBoxDefault.getPaddingBottom());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView text;
    }

}
