package com.example.android.softlab;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditStudentActivity extends AppCompatActivity {

    EditText regNoEditText, nameEditText, phoneEditText;
    Button clearButton, editButton, deleteButton;
    LinearLayout orLL;
    ProgressBar progressBar;
    int type;
    String regNo1, course1, name1, fname1, mname1, address1, state1, city1, pno1, sex1, dob1, email1, district1;
    PrefManager pref;
    String endpoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        pref = new PrefManager(EditStudentActivity.this);
        endpoint=pref.getUrl();
        orLL = (LinearLayout) findViewById(R.id.orLL);

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
                 if (s.length() != 0) {
                     orLL.setVisibility(View.GONE);
                     nameEditText.setVisibility(View.GONE);
                     phoneEditText.setVisibility(View.GONE);
                 } else {
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
                if (s.length() != 0) {
                    regNoEditText.setVisibility(View.GONE);
                    orLL.setVisibility(View.GONE);
                } else {
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
        String regno, name, phone;
        regno = regNoEditText.getText().toString();
        name = nameEditText.getText().toString();
        phone = phoneEditText.getText().toString();

        if (regno.length() == 0) {
            if (name.length() == 0 || phone.length() == 0) {
                type = 0;
            } else {
                type = -1;
            }
        } else {
            type = 1;
        }

        if (type == 1) {
            new DeleteExecuteTask().execute(regno);
        } else if (type == -1) {
            new DeleteExecuteTask().execute(name, phone);
        } else {
            Toast.makeText(EditStudentActivity.this, " " + type + " ", Toast.LENGTH_SHORT).show();
        }
    }

    public void retrieveStudent(View view) {
        String regno, name, phone;
        regno = regNoEditText.getText().toString();
        name = nameEditText.getText().toString();
        phone = phoneEditText.getText().toString();

        if (regno.length() == 0) {
            if (name.length() == 0 || phone.length() == 0) {
                type = 0;
            } else {
                type = -1;
            }
        } else {
            type = 1;
        }
        if (type == 1) {
            new RetrieveExecuteTask().execute(regno);
        } else if (type == -1) {
            new RetrieveExecuteTask().execute(name, phone);
        } else {
            Toast.makeText(EditStudentActivity.this, " " + type + " ", Toast.LENGTH_SHORT).show();
        }

    }

    public void editStudent(View view) {
        String regno, name, phone;
        regno = regNoEditText.getText().toString();
        name = nameEditText.getText().toString();
        phone = phoneEditText.getText().toString();

        if (regno.length() == 0) {
            if (name.length() == 0 || phone.length() == 0) {
                type = 0;
            } else {
                type = -1;
            }
        } else {
            type = 1;
        }
        if (type == 1) {
            new EditExecuteTask().execute(regno);
        } else if (type == -1) {
            new EditExecuteTask().execute(name, phone);
        } else {
            Toast.makeText(EditStudentActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
        }

    }

    public String deletePostData(String[] value) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(endpoint+"DeleteStudent");

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (type == 1) {
                list.add(new BasicNameValuePair("regNo", value[0]));
                list.add(new BasicNameValuePair("name", ""));
                list.add(new BasicNameValuePair("pno", ""));
            } else if (type == -1) {
                list.add(new BasicNameValuePair("regNo", ""));
                list.add(new BasicNameValuePair("name", value[0]));
                list.add(new BasicNameValuePair("pno", value[1]));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
//            s= readResponse(httpResponse);
            JSONObject jsonObject = null;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();
                jsonObject = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
                Log.d("abcd", jsonObject.getString("status"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            s = jsonObject.getString("status");

            //user authentication successful

        } catch (Exception exception) {
            s = "exception";
        }
        return s;


    }

    public String editPostData(String[] value) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(endpoint+"UpdateInfo");

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (type == 1) {
                list.add(new BasicNameValuePair("regNo", value[0]));
                list.add(new BasicNameValuePair("name", ""));
                list.add(new BasicNameValuePair("pno", ""));
            } else if (type == -1) {
                list.add(new BasicNameValuePair("regNo", ""));
                list.add(new BasicNameValuePair("name", value[0]));
                list.add(new BasicNameValuePair("pno", value[1]));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
//            s= readResponse(httpResponse);
            JSONObject jsonObject = null;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();
                Log.d("json", json);
//                jsonObject = new JSONObject(json);
                jsonObject = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));

                Log.d("abcd", jsonObject.getString("status"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            //user authentication successful
            s = jsonObject.getString("status");


//                Intent intent = new Intent(this, UpdateStudentActivity.class);
            regNo1 = jsonObject.getString("regNo");
            course1 = jsonObject.getString("course");
            name1 = jsonObject.getString("name");
            fname1 = jsonObject.getString("fname");
            mname1 = jsonObject.getString("mname");
            address1 = jsonObject.getString("address");
            state1 = jsonObject.getString("state");
            city1 = jsonObject.getString("city");
            pno1 = jsonObject.getString("pno");
            sex1 = jsonObject.getString("sex");
            dob1 = jsonObject.getString("dob");
            email1 = jsonObject.getString("email");
            district1 = jsonObject.getString("district");


//                showToast("Succesufully Deleted");

        } catch (Exception exception) {
            s = "exception";
        }
        return s;


    }

    void launchRetrieveActivity(String s) {
        Log.d("launch", s);
        if (s.equals("success")) {
            Intent intent = new Intent(this, RetrieveStudent.class);
            intent.putExtra("regNo", regNo1);
            intent.putExtra("course", course1);
            intent.putExtra("name", name1);
            intent.putExtra("fname", fname1);
            intent.putExtra("mname", mname1);
            intent.putExtra("address", address1);
            intent.putExtra("state", state1);
            intent.putExtra("city", city1);
            intent.putExtra("pno", pno1);
            intent.putExtra("sex", sex1);
            intent.putExtra("dob", dob1);
            intent.putExtra("email", email1);
            intent.putExtra("district", district1);
            startActivity(intent);
            finish();
        } else {
            showToast("Invalid Credentials");
        }

    }

    void launchUpdateActivity(String s) {
        Log.d("launch", s);
        if (s.equals("success")) {
//            showToast("Update Sucessful");
            Intent intent = new Intent(this, UpdateStudentActivity.class);
            intent.putExtra("regNo", regNo1);
            intent.putExtra("course", course1);
            intent.putExtra("name", name1);
            intent.putExtra("fname", fname1);
            intent.putExtra("mname", mname1);
            intent.putExtra("address", address1);
            intent.putExtra("state", state1);
            intent.putExtra("city", city1);
            intent.putExtra("pno", pno1);
            intent.putExtra("sex", sex1);
            intent.putExtra("dob", dob1);
            intent.putExtra("email", email1);
            intent.putExtra("district", district1);
            startActivity(intent);
            finish();
        } else {
            showToast("Inavalid Credentials");
        }

    }

    public void showToast(String msg) {
        Toast.makeText(EditStudentActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    class DeleteExecuteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = deletePostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            if (result.equals("success")) {
                Intent intent = new Intent(EditStudentActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                showToast("Succesufully Deleted");
            } else {
                showToast("Deletetion Error. Please try again.");
            }
//            Toast.makeText(EditStudentActivity.this, result,Toast.LENGTH_SHORT).show();

        }

    }

    class RetrieveExecuteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = editPostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
//            Toast.makeText(EditStudentActivity.this, result,Toast.LENGTH_SHORT).show();
            launchRetrieveActivity(result);

        }

    }

    class EditExecuteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = editPostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
//            Toast.makeText(EditStudentActivity.this, result,Toast.LENGTH_SHORT).show();
            launchUpdateActivity(result);

        }

    }

}
