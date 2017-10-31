package com.example.android.softlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText password,userName;
    Button login,resister;
    ProgressBar progressBar;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new PrefManager(MainActivity.this);
        password=(EditText) findViewById(R.id.editText2);
        userName=(EditText) findViewById(R.id.editText1);
        login=(Button) findViewById(R.id.button1);
        resister=(Button) findViewById(R.id.button2);

        //progess_msz.setVisibility(View.GONE);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);


        resister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent  intent=new Intent(MainActivity.this,RegisterUser.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String s1=userName.getText().toString();
                String s2=password.getText().toString();
                new ExecuteTask().execute(s1,s2);

            }
        });


    }

    class ExecuteTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=PostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this, result,Toast.LENGTH_SHORT).show();
            authenticate(result);

        }

    }

    public String PostData(String[] valuse) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://slab-env.us-west-2.elasticbeanstalk.com/Login");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name", valuse[0]));
            list.add(new BasicNameValuePair("pass",valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse=  httpClient.execute(httpPost);

            HttpEntity httpEntity=httpResponse.getEntity();
//            s= readResponse(httpResponse);
            JSONObject jsonObject = null;
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();
                jsonObject = new JSONObject(json);
                Log.d("abcd",jsonObject.getString("status"));
                s=jsonObject.getString("status");
                Log.d("svalue",s);

            } catch(Exception e){
                e.printStackTrace();
            }


        }
        catch(Exception exception)  {
            Log.d("a",exception.toString());
        }
        return s;


    }
//    public String readResponse(HttpResponse res) {
//        InputStream is=null;
//        String return_text="";
//        try {
//            is=res.getEntity().getContent();
//            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
//            String line="";
//            StringBuffer sb=new StringBuffer();
//            while ((line=bufferedReader.readLine())!=null)
//            {
//                sb.append(line);
//            }
//            return_text=sb.toString();
//        } catch (Exception e)
//        {
//
//        }
//        return return_text;
//    }
    public void authenticate(String status){
        //user authentication successful
        if(status.equals("Success")){
            prefManager.setIsSignedIn(true);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            showToast("Login Error. Please check your credentials and try again.");
        }
    }
    public void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT ).show();
    }
}
