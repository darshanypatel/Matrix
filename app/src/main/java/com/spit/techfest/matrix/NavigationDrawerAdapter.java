package com.spit.techfest.matrix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Mustafa on 13/09/2014.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    int res;
    public NavigationDrawerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        res = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(res,parent,false);
        ((TextView) convertView).setText(getItem(position));
        return convertView;
    }
}
