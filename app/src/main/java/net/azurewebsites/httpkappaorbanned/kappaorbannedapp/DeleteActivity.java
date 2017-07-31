package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

import android.app.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import java.util.logging.*;
import org.json.*;

public class DeleteActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(  LoginActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        final EditText tf_delete_username = findViewById(R.id.tf_delete_username);
        final EditText pf_delete_password = findViewById(R.id.pf_delete_password);
        final Button bt_delete_delete = findViewById(R.id.bt_delete_delete);
        final ProgressBar pb_delete = findViewById(R.id.pb_delete);


        bt_delete_delete.setOnClickListener(new View.OnClickListener() {// On click login button
                                              @Override
                                              public void onClick(View view) {
                                                  pb_delete.setVisibility(View.VISIBLE);
                                                  bt_delete_delete.setEnabled(false);
              LOGGER.log(Level.FINE, "Delete attempt");
              final String username = tf_delete_username.getText().toString();
              final String password = pf_delete_password.getText().toString();

              if (!username.equals("") && !password.equals("")) {// Check if fields are empty

                  Response.Listener<String> responseListener = new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {// Create respoones listner
                          try {
                              JSONObject jsonResponse = new JSONObject(response);
                              LOGGER.log(Level.FINE, jsonResponse.toString());
                              String status = jsonResponse.getString("status");
                              if (status.equals("Delete")) {// If login is valid move on
                                  bt_delete_delete.setEnabled(true);
                                  pb_delete.setVisibility(View.INVISIBLE);
                                  LOGGER.log(Level.FINE, "Delete");
                                  DeleteActivity.this.startActivity(new Intent(DeleteActivity.this, StartActivity.class));
                              } else {//If not valid display error dialog
                                  pb_delete.setVisibility(View.INVISIBLE);
                                  LOGGER.log(Level.FINE, "Delete Failed");
                                  AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                                  builder.setMessage("Delete Failed : Invalid Details").setNegativeButton("Retry", null).create().show();
                                  bt_delete_delete.setEnabled(true);
                              }

                          } catch (JSONException e) {
                              LOGGER.log(Level.SEVERE, e.toString());
                              pb_delete.setVisibility(View.INVISIBLE);
                              AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                              builder.setMessage("Delete Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
                              bt_delete_delete.setEnabled(true);
                          }
                      }
                  };
                  //Create and send respronce
                  Request_Delete delete = new Request_Delete(username, password, responseListener);
                  RequestQueue queue = Volley.newRequestQueue(DeleteActivity.this);
                  queue.add(delete);

              }
              else{
                  pb_delete.setVisibility(View.INVISIBLE);
                  AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                  builder.setMessage("Fill in the details").setNegativeButton("Ok", null).create().show();
                  bt_delete_delete.setEnabled(true);
              }

          }
      }
        );
    }
}