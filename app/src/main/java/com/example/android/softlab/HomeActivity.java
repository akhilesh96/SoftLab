package com.example.android.softlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefManager = new PrefManager(HomeActivity.this);
    }

    public void viewStudentInfo(View view){
        Toast.makeText(HomeActivity.this, "View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, ViewStudentsActivity.class);
        startActivity(intent);
    }

    public void addStudentInfo(View view){
//        Toast.makeText(HomeActivity.this, "Add", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, AddStudentActivity.class);
        startActivity(intent);
    }

    public void editStudentInfo(View view){
        Toast.makeText(HomeActivity.this, "Edit", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EditStudentActivity.class);
        startActivity(intent);
    }

    public void signOut(View view){
        prefManager.setIsSignedIn(false);
        Toast.makeText(HomeActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
