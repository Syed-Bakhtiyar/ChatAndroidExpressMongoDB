package com.example.syed_bakhtiyar.chatappmongodb.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.syed_bakhtiyar.chatappmongodb.ChatActivity;
import com.example.syed_bakhtiyar.chatappmongodb.ListAdapters.ProfileListAdapters;
import com.example.syed_bakhtiyar.chatappmongodb.R;
import com.example.syed_bakhtiyar.chatappmongodb.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersList extends Fragment {

    ListView listView;

    ArrayList<JSONObject> arrayList;

    ProfileListAdapters profileListAdapters;

    public UsersList() {
        // Required empty public constructor
    }

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_users_list, container, false);

        listView = (ListView) v.findViewById(R.id.list);

        arrayList = new ArrayList<>();

        profileListAdapters = new ProfileListAdapters(arrayList,getContext());

        listView.setAdapter(profileListAdapters);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONObject jsonObject = arrayList.get(position);

                try {
                    StaticClass.youuid = jsonObject.getString("_id");

                    Toast.makeText(getContext(), " "+StaticClass.youuid, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity(), ChatActivity.class));

                } catch (Exception e) {
                    Log.d("errr", "onItemClick: "+e);
                }


            }
        });

        new Users().execute();

        return v;
    }


    public class Users extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i=0;i<jsonArray.length();i++){

                    if(!(StaticClass.myuid.equals(jsonArray.getJSONObject(i).getString("_id"))))

                            arrayList.add((JSONObject) jsonArray.get(i));

                }

                profileListAdapters.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(StaticClass.Url+"users");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String temp = null;

                String completeData = "";

                while((temp = bufferedReader.readLine())!=null){

                    completeData += temp;
                }

                return completeData;

            } catch (Exception e) {
                Log.d("Users", "doInBackground: ");
            }


            return null;
        }
    }

}
