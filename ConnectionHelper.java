package com.example.android.androidsqlconnect;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
// This connection helper is what makes the connection to your database
public class ConnectionHelper {
    Connection con;
    String username, password, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionclass() {
        ip = "recipeserver.database.windows.net";
        port = "1433";
        database = "recipeDB";
        username = "jonasNation@recipeserver"; 
        password = "****************";
        // StrictMode is responsible for the connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
                
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //you can find your connection string in your azure sql database overview
            ConnectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="+username+";password="+password+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (Exception ex) {
            Log.e("Error; ", ex.getMessage());
        }
        return connection;
    }
}
