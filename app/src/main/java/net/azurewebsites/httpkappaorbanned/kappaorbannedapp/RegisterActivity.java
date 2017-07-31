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
    private static final Logger LOGGER = Logger.getLogger( StartActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText tf_register_username = findViewById(R.id.tf_register_username);
        final EditText tf_register_email = findViewById(R.id.tf_register_email);
        final EditText pf_register_password =  findViewById(R.id.pf_register_password);
        final Button bt_register_register =  findViewById(R.id.bt_register_register);

        bt_register_register.setOnClickListener(new View.OnClickListener() {// On click login button
              @Override
              public void onClick(View view) {
                  LOGGER.log(Level.FINE, "Register attempt");
                  final String username = tf_register_username.getText().toString();
                  final String email = tf_register_email.getText().toString();
                  final String password = pf_register_password.getText().toString();

                  if (!username.equals("") || !email.equals("")||!password.equals("")) {// Check if fields are empty

                      Response.Listener<String> responseListener = new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {// Create response listner
                              try {
                                  JSONObject jsonResponse = new JSONObject(response);
                                  LOGGER.log(Level.FINE, jsonResponse.toString());
                                  String status = jsonResponse.getString("status");
                                  if (status.equals("Account created")) {// If login is valid move on
                                      LOGGER.log(Level.FINE, "Register sucessfull");
                                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                      builder.setMessage("Account created").setPositiveButton("Ok", null).create().show();

                                      RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                  } else {//If not valid display error dialog
                                      LOGGER.log(Level.FINE, "Register Failed");
                                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                      builder.setMessage("Register Failed : Account all ready there").setNegativeButton("Retry", null).create().show();
                                  }

                              } catch (JSONException e) {
                                  AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                  builder.setMessage("Register Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
                              }
                          }
                      };
                      //Create and send respronce
                      Request_Login login = new Request_Login(username, password, responseListener);
                      RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                      queue.add(login);

                  }
                  else{
                      AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                      builder.setMessage("Fill in the details").setNegativeButton("Ok", null).create().show();
                  }
              }
          }
        );
    }

}

