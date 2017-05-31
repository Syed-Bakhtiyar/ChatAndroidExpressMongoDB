package com.example.syed_bakhtiyar.chatappmongodb.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.syed_bakhtiyar.chatappmongodb.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Syed_Bakhtiyar on 5/30/2017.
 */
public class ProfileListAdapters extends BaseAdapter {

    ArrayList<JSONObject> arrayList;

    Context context;

    TextView textView;

    LayoutInflater inflater;

    public ProfileListAdapters(ArrayList<JSONObject> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JSONObject jsonObject = arrayList.get(position);

        convertView = inflater.from(context).inflate(R.layout.all_users,parent,false);

        textView = (TextView) convertView.findViewById(R.id.user);


        try {
            textView.setText(jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
