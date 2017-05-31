package com.example.syed_bakhtiyar.chatappmongodb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.syed_bakhtiyar.chatappmongodb.ListAdapters.MessagesList;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    String msg;

    String name;


    EditText editText;

    ArrayList<JSONObject> arrayList;

    Button button;

    ListView listView;

    MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editText = (EditText) findViewById(R.id.messageEditText);

        button = (Button) findViewById(R.id.sendButton);

        arrayList = new ArrayList<>();

        messagesList =  new MessagesList(arrayList,getApplicationContext());

        listView = (ListView) findViewById(R.id.messageListView);

        listView.setAdapter(messagesList);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().length()>0){
                    button.setEnabled(true);
                }else {
                    button.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msg = editText.getText().toString().trim();

                new Send().execute();

            }
        });

        new Read().execute();

    }


    public class Send extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                if(jsonObject.getBoolean("send")){

                    Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();

                    editText.setText("");

                    new Read().execute();


                }
                else {

                    Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                Log.d("error", "onPostExecute: "+e);
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            String jsonString = jsonConvert();

            HttpPost httpPost = new HttpPost(StaticClass.Url+"chat");

            try {
                StringEntity stringEntity = new StringEntity(jsonString);

                httpPost.setEntity(stringEntity);

                httpPost.setHeader("Content-type","application/json");

                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

                BasicResponseHandler basicResponseHandler = new BasicResponseHandler();

                String response = defaultHttpClient.execute(httpPost,basicResponseHandler);

                return response;

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    public class Read extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i=0; i<jsonArray.length();i++){

                    arrayList.add((JSONObject) jsonArray.get(i));

                }

                messagesList.notifyDataSetChanged();


            } catch (Exception e) {
                Log.d("post", "onPostExecute: "+e);
            }


        }

        @Override
        protected String doInBackground(Void... params) {

            try{

                URL url = new URL(StaticClass.Url+"message/"+StaticClass.myuid+"/"+StaticClass.youuid);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String temp = null;

                String complete = "";

                while ((temp = bufferedReader.readLine())!=null){

                    complete += temp;
                }

                return complete;

            }catch (Exception e){

                Log.d("message", "doInBackground: "+e);

            }



            return null;
        }
    }








    public String jsonConvert(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("myid",StaticClass.myuid);

            jsonObject.put("yourid",StaticClass.youuid);

            jsonObject.put("name",StaticClass.name);

            jsonObject.put("message",msg);

            return  jsonObject.toString(1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
