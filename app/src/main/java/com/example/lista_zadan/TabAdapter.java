package com.example.lista_zadan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TabAdapter extends ArrayAdapter<Tab> {
    public TabAdapter(Context context, List<Tab> tabs) {

        super(context, 0, tabs);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tab tab = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_cell, parent, false);

        TextView title = convertView.findViewById(R.id.tabCellTitle);

        title.setText(tab.getTitle());
        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
