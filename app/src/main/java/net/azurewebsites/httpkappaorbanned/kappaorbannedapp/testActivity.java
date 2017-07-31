package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        final EditText v1 = findViewById(R.id.textView3);
        final android.widget.EditText v2 = findViewById(R.id.textView5);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
       final String streamername = intent.getStringExtra("streamername");

        v1.setText(username);
        v2.setText(streamername);

    }
}
