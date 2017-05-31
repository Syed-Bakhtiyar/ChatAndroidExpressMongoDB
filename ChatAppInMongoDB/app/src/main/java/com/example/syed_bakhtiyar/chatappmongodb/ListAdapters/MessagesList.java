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
public class MessagesList extends BaseAdapter {
    ArrayList<JSONObject> arrayList;

    Context context;

    LayoutInflater inflater;

    TextView name, message;

    public MessagesList(ArrayList<JSONObject> arrayList, Context context) {
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
        convertView = inflater.from(context).inflate(R.layout.read_msgs_list,parent,false);

        JSONObject jsonObject = arrayList.get(position);

        name = (TextView) convertView.findViewById(R.id.name);

        message = (TextView) convertView.findViewById(R.id.msg);

        try {
            name.setText(jsonObject.getString("name"));

            message.setText(jsonObject.getString("message"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
