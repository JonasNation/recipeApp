package com.example.android.androidsqlconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity2 extends AppCompatActivity  {
    Connection connect;
    String ConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    public void getRecipe(View view) {
        DisplayTask displayTask = new DisplayTask();
        displayTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onOptionMakeNewRecipe(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class DisplayTask extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String recipes;
        TextView records;

        DisplayTask() {}

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect == null) {
                    String query = "SELECT * FROM dbo.recipe_table";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    records = (TextView) findViewById(R.id.textViewRecords);
                    recipes = rs.getString("recipeId");
                    records.setText(recipes);
                  /*  while (rs.next()) {
                        recipes = rs.getString("recipeId");
                        z = "Query successful!";
                        isSuccess = true;
                    }*/
                }else {
                    z = "Invalid Query";
                    isSuccess = false;
                }
                publishProgress();
            }catch (java.sql.SQLException sqlException) {
                ConnectionResult = "Check connection";
                Log.e("SQL Error 1: ", sqlException.getMessage());
            }

            return "Your Recipes";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity2.this, "Getting records", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity2.this, s, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                records = (TextView) findViewById(R.id.textViewRecords);
                records.setText(recipes);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }
}