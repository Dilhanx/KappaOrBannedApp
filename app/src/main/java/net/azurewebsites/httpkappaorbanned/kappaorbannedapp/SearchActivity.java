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

public class SearchActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger( StartActivity.class.getName() );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText tf_search_name = findViewById(R.id.tf_search_name);
        final Button bt_search_search = findViewById(R.id.bt_search_search);
        final ProgressBar pb_search = findViewById(R.id.pb_search);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        bt_search_search.setOnClickListener(new View.OnClickListener() {// On click Search button
          @Override
          public void onClick(View view) {

          pb_search.setVisibility(View.VISIBLE);
          LOGGER.log(Level.FINE, "Search attempt");
          final String streamername = tf_search_name.getText().toString();

          if (!tf_search_name.equals("")) {// Check if fields are empty

              Response.Listener<String> responseListener = new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {// Create respoones listner
                      try {
                          JSONObject jsonResponse = new JSONObject(response);
                          LOGGER.log(Level.FINE, jsonResponse.toString());
                          String status = jsonResponse.getString("status");
                          if (status.equals("Found")) {// If login is valid move on
                              LOGGER.log(Level.FINE, "Found");
                              pb_search.setVisibility(View.INVISIBLE);
                              SearchActivity.this.startActivity(new Intent(SearchActivity.this, StreamerActivity.class).putExtra("username", username).putExtra("streamername",streamername));
                          } else {//If not valid display error dialog
                              pb_search.setVisibility(View.INVISIBLE);
                              LOGGER.log(Level.FINE, "Search Failed");
                              AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                              builder.setMessage("Search Failed : Streamer not found").setNegativeButton("Retry", null).create().show();

                          }

                      } catch (JSONException e) {
                          pb_search.setVisibility(View.INVISIBLE);
                          AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                          builder.setMessage("Search Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
                      }
                  }
              };
              //Create and send respronce
              Request_Search search = new Request_Search(streamername , responseListener);
              RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
              queue.add(search);

              }
              else{
                  pb_search.setVisibility(View.INVISIBLE);
                  AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                  builder.setMessage("Fill in the details").setNegativeButton("Ok", null).create().show();

              }

                }
                }
            );
    }
}
