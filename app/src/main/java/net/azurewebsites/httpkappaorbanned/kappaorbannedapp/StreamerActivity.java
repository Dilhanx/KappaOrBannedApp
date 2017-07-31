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

public class StreamerActivity extends AppCompatActivity {

    final EditText tv_streamer_streamer_name = findViewById(R.id.tv_streamer_streamer_name);
    final EditText tv_streamer_real_name = findViewById(R.id.tv_streamer_real_name);
    final EditText tv_streamer_schedule = findViewById(R.id.tv_streamer_schedule);
    final EditText tv_streamer_type = findViewById(R.id.tv_streamer_type);
    final EditText tv_streamer_bio = findViewById(R.id.tv_streamer_bio);

    final Button bt_search_search = findViewById(R.id.bt_search_search);
    final ProgressBar pb_streamer = findViewById(R.id.pb_streamer);



    Intent intent = getIntent();
    final String username = intent.getStringExtra("username");
    final String streamername = intent.getStringExtra("streamername");

    private static final Logger LOGGER = Logger.getLogger( StartActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streamer);

//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {// Create response listner
//                try {
//                    JSONObject jsonResponse = new JSONObject(response);
//                    LOGGER.log(Level.FINE, jsonResponse.toString());
//                    tv_streamer_streamer_name.setText(jsonResponse.getString("streamer_name"));
//                    tv_streamer_real_name.setText(jsonResponse.getString("real_name"));
//                    tv_streamer_schedule.setText(jsonResponse.getString("schedule"));
//                    tv_streamer_type.setText(jsonResponse.getString("streamer_type"));
//                    tv_streamer_bio.setText(jsonResponse.getString("bio"));
//                    pb_streamer.setVisibility(View.INVISIBLE);
//
//
//                } catch (JSONException e) {
//                    pb_streamer.setVisibility(View.INVISIBLE);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(StreamerActivity.this);
//                    builder.setMessage("Search Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
//                }
//            }
//        };
//        //Create and send response
//        Request_Streamer Streamer = new Request_Streamer(streamername , responseListener);
//        RequestQueue queue = Volley.newRequestQueue(StreamerActivity.this);
//        queue.add(Streamer);
    }
}
//AdmiralBulldog