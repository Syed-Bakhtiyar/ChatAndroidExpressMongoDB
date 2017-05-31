package com.example.syed_bakhtiyar.chatappmongodb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edemail, edpassword;

    String email, password;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edemail = (EditText) findViewById(R.id.input_email);

        edpassword = (EditText) findViewById(R.id.input_password);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edemail.getText().toString().trim();

                password = edpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

                    Toast.makeText(MainActivity.this, "Please complete form", Toast.LENGTH_SHORT).show();

                    return;

                }

                progressDialog.show();

                Create create = new Create();

                String jsonString = jsonConvert();

                create.execute(jsonString);

            }
        });


        findViewById(R.id.link_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });
    } // end of main method


    class Create extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {


            HttpPost httpPost = new HttpPost(StaticClass.Url+"login");


            try {
                StringEntity stringEntity = new StringEntity(params[0]);

//                Log.d("stringEntity", params[0]+" getServerResponse: ");

                httpPost.setEntity(stringEntity);

                httpPost.setHeader("Content-type","application/json");

                DefaultHttpClient client = new DefaultHttpClient();

                BasicResponseHandler handler = new BasicResponseHandler();

                String res =  client.execute(httpPost,handler);

                Log.d("response", "getServerResponse: "+res);

                return res;

            } catch (Exception e) {
                Log.d("post", "getServerResponse: "+e);
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Toast.makeText(getApplicationContext(), " "+s, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("us");

                if(jsonArray.length() == 0){

                    Toast.makeText(getApplicationContext(), "Not Valid", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                    return;
                }
                else {

                    StaticClass.myuid = jsonArray.getJSONObject(0).getString("_id");

                    StaticClass.name = jsonArray.getJSONObject(0).getString("name");
                }



                progressDialog.dismiss();

                startActivity();

//                jsonObject = (JSONObject) jsonArray.get(0);
//
//                Log.d("jsonArray", "onPostExecute: "+jsonObject.toString());

            } catch (JSONException e) {

                Log.d("onPostExecute", "onPostExecute: "+e);
//                e.printStackTrace();
            }




        }
    }




    public String jsonConvert(){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);

            jsonObject.put("password", password);

            return jsonObject.toString(1);

        }catch (Exception e){

            Log.d("jsonConvert", "jsonConvert: "+e);


        }

        return null;
    }

    public void startActivity(){

        startActivity(new Intent(MainActivity.this,ProfileActivity.class));


    }

}
