package com.example.lista_zadan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends ArrayAdapter<Tab> {
    private Context context;
    int resource;
    public TabAdapter(Context context, int resource, ArrayList<Tab> tabs) {

        super(context, resource, tabs);
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null)
            convertView = inflater.inflate(resource, parent, false);

        Tab tab = getItem(position);
        TextView tTitle = convertView.findViewById(R.id.tabCellTitle);
        tTitle.setText(tab.getTitle());

        return convertView;
    }
}
