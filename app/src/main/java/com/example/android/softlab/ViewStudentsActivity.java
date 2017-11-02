package com.example.android.softlab;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {

    ListView listView;
    List<Student> students;
    StudentAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        listView = (ListView) findViewById(R.id.studentsListView);
        progressBar = (ProgressBar) findViewById(R.id.studentsProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        students = new ArrayList<>();
        adapter = new StudentAdapter(ViewStudentsActivity.this, students);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student currentStudent = adapter.getItem(position);
                Intent intent = new Intent(ViewStudentsActivity.this, RetrieveStudent.class);
                intent.putExtra("regNo", currentStudent.getRegNo());
                intent.putExtra("course", currentStudent.getCourse());
                intent.putExtra("name", currentStudent.getName());
                intent.putExtra("fname", currentStudent.getFname());
                intent.putExtra("mname", currentStudent.getMname());
                intent.putExtra("address", currentStudent.getAddress());
                intent.putExtra("state", currentStudent.getState());
                intent.putExtra("city", currentStudent.getCity());
                intent.putExtra("pno", currentStudent.getPhoneNo());
                intent.putExtra("sex", currentStudent.getSex());
                intent.putExtra("dob", currentStudent.getDob());
                intent.putExtra("email", currentStudent.getEmail());
                intent.putExtra("district",currentStudent.getDistrict());
                startActivity(intent);
            }
        });

        new ExecuteTask().execute("test", "test");

    }

    public String PostData(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://slab-env.us-west-2.elasticbeanstalk.com/StudentInfo");

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name", valuse[0]));
            list.add(new BasicNameValuePair("pass", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                String json = reader.readLine();
                s = json;
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception exception) {
            Log.d("a", exception.toString());
        }
        return s;
    }

    public void addStudents(String json) {
        JSONArray jsonArray;
        JSONObject jsonObject;
        Log.d("result", json);
        try {
            jsonArray = new JSONArray(json.substring(json.indexOf("["), json.lastIndexOf("]") + 1));
            for (int i = 0; i < jsonArray.length() - 1; i++) {
                Log.d("ivalue", jsonArray.getJSONObject(i).getString("name"));
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.isNull("status") == true) {
                    Student temp = null;
                    try {
                        temp = new Student(
                                jsonObject.getString("regNo"),
                                jsonObject.getString("course"),
                                jsonObject.getString("name"),
                                jsonObject.getString("fname"),
                                jsonObject.getString("mname"),
                                jsonObject.getString("address"),
                                jsonObject.getString("district"),
                                jsonObject.getString("state"),
                                jsonObject.getString("city"),
                                jsonObject.getString("pno"),
                                jsonObject.getString("sex"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("email")
                        );
                        students.add(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            adapter = new StudentAdapter(ViewStudentsActivity.this, students);
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showToast(String msg) {
        Toast.makeText(ViewStudentsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    class ExecuteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String res = PostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
//            showToast(result);
            addStudents(result);
        }

    }
}