package com.example.android.softlab;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    EditText regNo,course,name,fname,mname,address,district,city,state,pno,sex,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        regNo = (EditText)findViewById(R.id.regNo);
        course=(EditText)findViewById(R.id.course);
        name=(EditText)findViewById(R.id.name);
        fname=(EditText)findViewById(R.id.course);
        mname=(EditText)findViewById(R.id.course);
        course=(EditText)findViewById(R.id.course);
        course=(EditText)findViewById(R.id.course);
        course=(EditText)findViewById(R.id.course);
        course=(EditText)findViewById(R.id.course);

    }

    public void addStudent(View view){
        Toast.makeText(this, "add student", Toast.LENGTH_SHORT).show();

//        new MainActivity.ExecuteTask().execute(s1,s2);
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
//            progressBar.setVisibility(View.GONE);
//            //progess_msz.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this, result,Toast.LENGTH_SHORT).show();

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

            } catch(Exception e){
                e.printStackTrace();
            }

            //user authentication successful
            if(jsonObject.getString("status").equals("success")){
//                prefManager.setIsSignedIn(true);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            else{
//               showToast("Login Error. Please check your credentials and try again.");
            }
        }
        catch(Exception exception)  {
            s="exception";
        }
        return s;


    }
    public void cancel(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
