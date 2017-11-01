package com.example.android.softlab;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        listView = (ListView) findViewById(R.id.studentsListView);
        final StudentAdapter adapter;
        List<Student> students = new ArrayList<>();
        Student temp = null;

        temp = new Student(
                "83", "course",
                "pk", "f",
                "m", "add",
                "dist", "state",
                "city", "87123",
                "M", "26/10/1996", "email");


        students.add(temp);
        temp = new Student(
                "100", "course",
                "at", "f",
                "m", "add",
                "dist", "state",
                "city", "87123",
                "M", "26/10/1996", "email");


        students.add(temp);
        adapter = new StudentAdapter(ViewStudentsActivity.this, students);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Student currentStudent = adapter.getItem(position);
                Toast.makeText(ViewStudentsActivity.this, currentStudent.getName(), Toast.LENGTH_SHORT).show();
            }

        });

        new ExecuteTask().execute("test","test");

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
                jsonObject = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
                s=jsonObject.toString();
//                jsonArray = new JSONArray(json);
//                jsonArray = new JSONArray(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
//                s = jsonArray.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception exception) {
            Log.d("a", exception.toString());
        }
        return s;


    }

    public void addStudents(String json){
        JSONObject jsonObject;
        JSONObject[] studs;
        try{
            jsonObject = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
            Log.d("jsonch",jsonObject.toString());
            //todo: extract students and add to list view


        }
        catch(JSONException e){

        }


    }

    public void showToast(String msg) {
        Toast.makeText(ViewStudentsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
