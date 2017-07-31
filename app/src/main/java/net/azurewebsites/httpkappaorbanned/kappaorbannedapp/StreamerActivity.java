package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamerActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger( StreamerActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streamer);


        final TextView tv_streamer_streamer_name = findViewById(R.id.tv_streamer_streamer_name);
        final TextView  tv_streamer_real_name = findViewById(R.id.tv_streamer_real_name);
        final TextView  tv_streamer_schedule = findViewById(R.id.tv_streamer_schedule);
        final TextView tv_streamer_type = findViewById(R.id.tv_streamer_type);
        final TextView  tv_streamer_bio = findViewById(R.id.tv_streamer_bio);

        final Button bt_streamer_comment = findViewById(R.id.bt_streamer_comment);
        final Button bt_streamer_emote = findViewById(R.id.bt_streamer_emote);

        final ProgressBar pb_streamer = findViewById(R.id.pb_streamer);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String streamername = intent.getStringExtra("streamername");

//        bt_streamer_comment.setEnabled(false);
//        bt_streamer_emote.setEnabled(false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {// Create response listner
                try {
                    tv_streamer_streamer_name.setText(response);
                    JSONObject jsonResponse = new JSONObject(response);

                    LOGGER.log(Level.FINE, jsonResponse.toString());
                    tv_streamer_streamer_name.setText(jsonResponse.getString("streamer_name"));
                    tv_streamer_real_name.setText(jsonResponse.getString("real_name"));
                    tv_streamer_schedule.setText(jsonResponse.getString("schedule"));
                    tv_streamer_type.setText(jsonResponse.getString("streamer_type"));
                    tv_streamer_bio.setText(jsonResponse.getString("bio"));
                    pb_streamer.setVisibility(View.INVISIBLE);
                    bt_streamer_comment.setEnabled(true);
                    bt_streamer_emote.setEnabled(true);

                } catch (JSONException e) {
                    pb_streamer.setVisibility(View.INVISIBLE);
                    LOGGER.log(Level.SEVERE, e.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(StreamerActivity.this);
                    builder.setMessage("Search Failed : Sever error"+e).setNegativeButton("Retry", null).create().show();e.printStackTrace();
                    //StreamerActivity.this.startActivity( new Intent(StreamerActivity.this, StartActivity.class));
                }
            }
        };
        //Create and send response
        Request_Streamer Streamer = new Request_Streamer(streamername , responseListener);
        RequestQueue queue = Volley.newRequestQueue(StreamerActivity.this);
        queue.add(Streamer);

        bt_streamer_comment.setOnClickListener(new View.OnClickListener() {// On click login button
            @Override
            public void onClick(View view) {
                LOGGER.log( Level.FINE, "Moving to Comment" );
                StreamerActivity.this.startActivity(new Intent(StreamerActivity.this, CommentActivity.class).putExtra("username", username).putExtra("streamername",streamername));
            }
        });
        bt_streamer_emote.setOnClickListener(new View.OnClickListener() {// On click login button
            @Override
            public void onClick(View view) {
                LOGGER.log( Level.FINE, "Moving to Emote" );
                StreamerActivity.this.startActivity(new Intent(StreamerActivity.this,EmoteActivity.class).putExtra("streamername",streamername));
            }
        });
    }

}
//AdmiralBulldog