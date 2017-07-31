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

public class RegisterActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger( RegisterActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText tf_register_username = findViewById(R.id.tf_register_username);
        final EditText tf_register_email = findViewById(R.id.tf_register_email);
        final EditText pf_register_password =  findViewById(R.id.pf_delete_password);
        final Button bt_register_register =  findViewById(R.id.bt_delete_delete);
        final ProgressBar pb_register = findViewById(R.id.pb_register);

        bt_register_register.setOnClickListener(new View.OnClickListener() {// On click login button
              @Override
              public void onClick(View view) {
                  pb_register.setVisibility(View.VISIBLE);
                  bt_register_register.setEnabled(false);
                  LOGGER.log(Level.FINE, "Register attempt");
                  final String username = tf_register_username.getText().toString();
                  final String email = tf_register_email.getText().toString();
                  final String password = pf_register_password.getText().toString();

                  if (!username.equals("") && !email.equals("")&&!password.equals("")) {// Check if fields are empty

                      Response.Listener<String> responseListener = new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {// Create response listner
                              try {
                                  JSONObject jsonResponse = new JSONObject(response);
                                  LOGGER.log(Level.FINE, jsonResponse.toString());
                                  tf_register_username.setText(jsonResponse.toString());
                                  String status = jsonResponse.getString("status");
                                  if (status.equals("Account created")) {// If registers  move on
                                      bt_register_register.setEnabled(true);
                                      pb_register.setVisibility(View.INVISIBLE);
                                      LOGGER.log(Level.FINE, "Register sucessfull");
                                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                      builder.setMessage("Account created").setPositiveButton("Ok", null).create().show();
                                      RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                                  } else {//If not valid display error dialog
                                      bt_register_register.setEnabled(true);
                                      pb_register.setVisibility(View.INVISIBLE);
                                      LOGGER.log(Level.FINE, "Register Failed");
                                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                      builder.setMessage("Register Failed : Account all ready there").setNegativeButton("Retry", null).create().show();
                                  }

                              } catch (JSONException e) {
                                  LOGGER.log(Level.SEVERE, e.toString());
                                  bt_register_register.setEnabled(true);
                                  pb_register.setVisibility(View.INVISIBLE);
                                  AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                  builder.setMessage("Register Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
                              }
                          }
                      };
                      //Create and send respronce
                      Request_Register Register = new Request_Register(username,email, password, responseListener);
                      RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                      queue.add(Register);

                  }
                  else{
                      bt_register_register.setEnabled(true);
                      pb_register.setVisibility(View.INVISIBLE);
                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                      builder.setMessage("Fill in the details").setNegativeButton("Ok", null).create().show();
                  }
              }
          }
        );
    }

}

