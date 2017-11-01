package com.example.android.softlab;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RetrieveStudent extends AppCompatActivity {

    TextView course,name,fname,mname,address,district,city,state,pno,sex,email;
    TextView dob;
    TextView regNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_student);
        regNo = (TextView) findViewById(R.id.upregNo);
        course=(TextView) findViewById(R.id.upcourse);
        name=(TextView) findViewById(R.id.upname);
        fname=(TextView) findViewById(R.id.upfathersName);
        mname=(TextView) findViewById(R.id.upmothersName);
        address=(TextView) findViewById(R.id.upaddrss);
        district=(TextView) findViewById(R.id.updistrict);
        city=(TextView) findViewById(R.id.upcity);
        state=(TextView) findViewById(R.id.upstate);
        pno=(TextView) findViewById(R.id.upphoneNumber);
        sex=(TextView) findViewById(R.id.upsex);
        dob=(TextView)findViewById(R.id.updob);
        email=(TextView) findViewById(R.id.upemail);

//        Intent intent=getIntent();
        Log.d("UpdateActivity",getIntent().getStringExtra("regNo"));
        regNo.setText(getIntent().getStringExtra("regNo"));
        course.setText(getIntent().getStringExtra("course"));
        name.setText(getIntent().getStringExtra("name"));
        fname.setText(getIntent().getStringExtra("fname"));
        mname.setText(getIntent().getStringExtra("mname"));
        address.setText(getIntent().getStringExtra("address"));
        district.setText(getIntent().getStringExtra("district"));
        city.setText(getIntent().getStringExtra("city"));
        state.setText(getIntent().getStringExtra("state"));
        pno.setText(getIntent().getStringExtra("pno"));
        sex.setText(getIntent().getStringExtra("sex"));
        dob.setText(getIntent().getStringExtra("dob"));
        email.setText(getIntent().getStringExtra("email"));



    }


    public void showToast(String msg){
        Toast.makeText(RetrieveStudent.this, msg, Toast.LENGTH_SHORT ).show();
    }
}
