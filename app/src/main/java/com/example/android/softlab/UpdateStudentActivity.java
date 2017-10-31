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

public class UpdateStudentActivity extends AppCompatActivity {

    EditText regNo,course,name,fname,mname,address,district,city,state,pno,sex,dob,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        regNo = (EditText)findViewById(R.id.upregNo);
        course=(EditText)findViewById(R.id.upcourse);
        name=(EditText)findViewById(R.id.upname);
        fname=(EditText)findViewById(R.id.upfathersName);
        mname=(EditText)findViewById(R.id.upmothersName);
        address=(EditText)findViewById(R.id.upaddrss);
        district=(EditText)findViewById(R.id.updistrict);
        city=(EditText)findViewById(R.id.upcity);
        state=(EditText)findViewById(R.id.upstate);
        pno=(EditText)findViewById(R.id.upphoneNumber);
        sex=(EditText)findViewById(R.id.upsex);
        dob=(EditText)findViewById(R.id.updob);
        email=(EditText)findViewById(R.id.upemail);

        Intent intent=getIntent();

        regNo.setText(intent.getStringExtra("regNo"));
        course.setText(intent.getStringExtra("course"));
        name.setText(intent.getStringExtra("name"));
        fname.setText(intent.getStringExtra("fname"));
        mname.setText(intent.getStringExtra("mname"));
        address.setText(intent.getStringExtra("address"));
        district.setText(intent.getStringExtra("district"));
        city.setText(intent.getStringExtra("city"));
        state.setText(intent.getStringExtra("state"));
        pno.setText(intent.getStringExtra("pno"));
        sex.setText(intent.getStringExtra("sex"));
        dob.setText(intent.getStringExtra("dob"));
        email.setText(intent.getStringExtra("email"));



    }

    public void addStudent(View view){
        Toast.makeText(this, "add student", Toast.LENGTH_SHORT).show();
        String s1=regNo.getText().toString();
        String s2=course.getText().toString();
        String s3=name.getText().toString();
        String s4=fname.getText().toString();
        String s5=mname.getText().toString();
        String s6=address.getText().toString();
        String s7=district.getText().toString();
        String s8=city.getText().toString();
        String s9=state.getText().toString();
        String s10=pno.getText().toString();
        String s11=sex.getText().toString();
        String s12=dob.getText().toString();
        String s13=email.getText().toString();

        new ExecuteTask().execute(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13);
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
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(UpdateStudentActivity.this, result,Toast.LENGTH_SHORT).show();

        }

    }

    public String PostData(String[] valuse) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://slab-env.us-west-2.elasticbeanstalk.com/AddStudent");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("regNo", valuse[0]));
            list.add(new BasicNameValuePair("course",valuse[1]));
            list.add(new BasicNameValuePair("name",valuse[2]));
            list.add(new BasicNameValuePair("fname",valuse[3]));
            list.add(new BasicNameValuePair("mname",valuse[4]));
            list.add(new BasicNameValuePair("address",valuse[5]));
            list.add(new BasicNameValuePair("district",valuse[6]));
            list.add(new BasicNameValuePair("city",valuse[7]));
            list.add(new BasicNameValuePair("state",valuse[8]));
            list.add(new BasicNameValuePair("pno",valuse[9]));
            list.add(new BasicNameValuePair("sex",valuse[10]));
            list.add(new BasicNameValuePair("dob",valuse[11]));
            list.add(new BasicNameValuePair("email",valuse[12]));

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
                showToast("Login Error. Please check your credentials and try again.");
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
    public void showToast(String msg){
        Toast.makeText(UpdateStudentActivity.this, msg, Toast.LENGTH_SHORT ).show();
    }
}
