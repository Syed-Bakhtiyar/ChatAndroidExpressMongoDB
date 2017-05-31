package com.example.syed_bakhtiyar.chatappmongodb;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Signup extends AppCompatActivity {

    EditText edname, edemail, edpassword;

    String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edname = (EditText) findViewById(R.id.name);

        edemail = (EditText) findViewById(R.id.input_email);

        edpassword = (EditText) findViewById(R.id.input_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = edname.getText().toString().trim();

                email = edemail.getText().toString().trim();

                password = edpassword.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

                    Toast.makeText(Signup.this, "Please Complete form", Toast.LENGTH_SHORT).show();

                    return;
                }


                new CreateUser().execute();

            }
        });


    }

    public class CreateUser extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                if(jsonObject.getBoolean("check")){

                    Toast.makeText(getApplicationContext(), "Created", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Already Exist", Toast.LENGTH_SHORT).show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... params) {


            String Jsonstring = jsonConvert();

            if(jsonConvert() == null){

                return null;

            }

            HttpPost httpPost = new HttpPost(StaticClass.Url+"create");

            try {
                StringEntity stringEntity = new StringEntity(Jsonstring);

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


    public String jsonConvert(){

        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("name",name);

            jsonObject.put("email",email);

            jsonObject.put("password",password);


            return jsonObject.toString();

        } catch (Exception e) {
            Log.d("JsonConvert", "jsonConvert: ");
        }


        return null;
    }

}
