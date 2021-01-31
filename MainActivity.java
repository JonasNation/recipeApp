package com.example.android.androidsqlconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText prep, cook, serving, ingred, method, create;
    Connection connect;
    String ConnectionResult;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(my_toolbar);

        prep = findViewById(R.id.preperation);
        cook = findViewById(R.id.cook);
        serving = findViewById(R.id.nutrition);
        ingred = findViewById(R.id.ingredients);
        method = findViewById(R.id.methods);
        create = findViewById(R.id.createdBY);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void getRecordFromSQL(View view) {
      SQLInsertTask sqlInsertTask = new SQLInsertTask();
      sqlInsertTask.execute();
    }

    public void onOptionGetRecipe(MenuItem item) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class SQLInsertTask extends AsyncTask<String, String, String> {

        SQLInsertTask() {}

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    String query = "insert into dbo.recipe_table (preparation_time,cooking_time,nutrition_per_serving,ingredients,methods,created_by)" +
                            " values ('"+prep.getText().toString()+"','"+cook.getText().toString()+"','"+serving.getText().toString()+"','"+ingred.getText().toString()+"','"+method.getText().toString()+"','"+create.getText().toString()+"')";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                }
                publishProgress();
            }catch (SQLException | java.sql.SQLException sqlException) {
                Log.e("SQL Error 1: ", sqlException.getMessage());
            }

            return "Recipe insert complete";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Insert in progress!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressBar.getProgress();

        }
    }
}

