package com.example.android.softlab;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditStudentActivity extends AppCompatActivity {

    EditText regNoEditText,nameEditText,phoneEditText;
    Button clearButton,editButton,deleteButton;
    LinearLayout orLL;
    ProgressBar progressBar;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        orLL = (LinearLayout)findViewById(R.id.orLL);

        regNoEditText = (EditText) findViewById(R.id.editRegNo);
        nameEditText = (EditText) findViewById(R.id.editName);
        phoneEditText = (EditText) findViewById(R.id.editPhoneNo);

        progressBar = (ProgressBar) findViewById(R.id.progressBarEditActivity);
        progressBar.setVisibility(View.GONE);

        clearButton = (Button) findViewById(R.id.clearButton);
        editButton = (Button) findViewById(R.id.editStudent);
        deleteButton = (Button) findViewById(R.id.deleteStudent);


        regNoEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() != 0){
                            orLL.setVisibility(View.GONE);
                            nameEditText.setVisibility(View.GONE);
                            phoneEditText.setVisibility(View.GONE);
                        }
                        else{
                            orLL.setVisibility(View.VISIBLE);
                            nameEditText.setVisibility(View.VISIBLE);
                            phoneEditText.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }


        );

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() != 0){
                    regNoEditText.setVisibility(View.GONE);
                    orLL.setVisibility(View.GONE);
                }
                else{
                    regNoEditText.setVisibility(View.VISIBLE);
                    orLL.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        nameEditText.addTextChangedListener(textWatcher);
        phoneEditText.addTextChangedListener(textWatcher);



    }


    public void clearForm(View view) {
        regNoEditText.setVisibility(View.VISIBLE);
        nameEditText.setVisibility(View.VISIBLE);
        phoneEditText.setVisibility(View.VISIBLE);
        orLL.setVisibility(View.VISIBLE);

        regNoEditText.setText("");
        nameEditText.setText("");
        phoneEditText.setText("");
    }

    public void deleteStudent(View view) {
        String regno,name,phone;
        regno=regNoEditText.getText().toString();
        name=nameEditText.getText().toString();
        phone=phoneEditText.getText().toString();

        if(regno.length()==0){
            if(name.length()==0 || phone.length()==0){
                type=0;
            }
            else{
                type=-1;
            }
        }
        else{
            type=1;
        }

        if(type==1){
            new DeleteExecuteTask().execute(regno);
        }
        else if(type==-1){
            new DeleteExecuteTask().execute(name,phone);
        }
        else{
            Toast.makeText(EditStudentActivity.this," "+type+" ",Toast.LENGTH_SHORT).show();
        }
    }

    public void editStudent(View view) {
        String regno,name,phone;
        regno=regNoEditText.getText().toString();
        name=nameEditText.getText().toString();
        phone=phoneEditText.getText().toString();

        if(regno.length()==0){
            if(name.length()==0 || phone.length()==0){
                type=0;
            }
            else{
                type=-1;
            }
        }
        else{
            type=1;
        }
        Toast.makeText(EditStudentActivity.this,"  "+type+" ",Toast.LENGTH_SHORT).show();
    }


    class DeleteExecuteTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=deletePostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(EditStudentActivity.this, result,Toast.LENGTH_SHORT).show();

        }

    }

    public String deletePostData(String[] value) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://slab-env.us-west-2.elasticbeanstalk.com/DeleteStudent");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            if(type==1){
                list.add(new BasicNameValuePair("regNo", value[0]));
                list.add(new BasicNameValuePair("name", ""));
                list.add(new BasicNameValuePair("pno",""));
            }
            else if(type==-1){
                list.add(new BasicNameValuePair("regNo", ""));
                list.add(new BasicNameValuePair("name", value[0]));
                list.add(new BasicNameValuePair("pno",value[1]));
            }

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
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                showToast("Succesufully Deleted");
            }
            else{
                showToast("Deletetion Error. Please check your credentials and try again.");
            }
        }
        catch(Exception exception)  {
            s="exception";
        }
        return s;


    }
    public String readResponse(HttpResponse res) {
        InputStream is=null;
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
        } catch (Exception e)
        {

        }
        return return_text;
    }


    class EditExecuteTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=deletePostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(EditStudentActivity.this, result,Toast.LENGTH_SHORT).show();

        }

    }

    public String editPostData(String[] value) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://slab-env.us-west-2.elasticbeanstalk.com/EditStudent");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            if(type==1){
                list.add(new BasicNameValuePair("regNo", value[0]));
                list.add(new BasicNameValuePair("name", ""));
                list.add(new BasicNameValuePair("pno",""));
            }
            else if(type==-1){
                list.add(new BasicNameValuePair("regNo", ""));
                list.add(new BasicNameValuePair("name", value[0]));
                list.add(new BasicNameValuePair("pno",value[1]));
            }

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

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("regNo",jsonObject.getString("regNo"));
                intent.putExtra("course",jsonObject.getString("course"));
                intent.putExtra("name",jsonObject.getString("name"));
                intent.putExtra("fname",jsonObject.getString("fname"));
                intent.putExtra("mname",jsonObject.getString("mname"));
                intent.putExtra("address",jsonObject.getString("address"));
                intent.putExtra("state",jsonObject.getString("state"));
                intent.putExtra("city",jsonObject.getString("city"));
                intent.putExtra("pno",jsonObject.getString("pno"));
                intent.putExtra("sex",jsonObject.getString("sex"));
                intent.putExtra("dob",jsonObject.getString("dob"));
                intent.putExtra("email",jsonObject.getString("email"));

                startActivity(intent);
                finish();
//                showToast("Succesufully Deleted");
            }
            else{
                showToast("Error. Please check your credentials and try again.");
            }
        }
        catch(Exception exception)  {
            s="exception";
        }
        return s;


    }



    public void showToast(String msg){
        Toast.makeText(EditStudentActivity.this, msg, Toast.LENGTH_SHORT ).show();
    }

}
