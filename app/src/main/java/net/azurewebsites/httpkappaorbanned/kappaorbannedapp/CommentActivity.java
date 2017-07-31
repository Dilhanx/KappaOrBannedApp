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

import java.util.*;


public class CommentActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(  LoginActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final EditText et_comment_comment = findViewById(R.id.et_comment_comment);
        final Button bt_comment_send = findViewById(R.id.bt_comment_send);
        final ListView lv_comment_list = findViewById(R.id.lv_comment_list);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String streamername = intent.getStringExtra("streamername");


        String[] comment_array = new String[] {

        };

        final List<String> comment_array_list = new ArrayList<String>(Arrays.asList(comment_array));
        final ArrayAdapter cAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comment_array_list );
        lv_comment_list.setAdapter(cAdapter);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {// Create response listner
                try {



                    JSONArray  jsonArray = new JSONArray (response);

                    for(int i=0;i<jsonArray.length();i++){
                        comment_array_list.add(jsonArray.getJSONObject(i).getString("user_name")+":"+jsonArray.getJSONObject(i).getString("comment"));


                    }
                    cAdapter.notifyDataSetChanged();


                } catch (JSONException e) {

                    LOGGER.log(Level.SEVERE, e.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setMessage(e.toString()).setNegativeButton("Retry", null).create().show();e.printStackTrace();
                    //StreamerActivity.this.startActivity( new Intent(StreamerActivity.this, StartActivity.class));
                }
                finally {

                }
            }
        };
        //Create and send response
        Request_Comment Comment  = new Request_Comment(streamername , responseListener);
        RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
        queue.add(Comment);





        bt_comment_send.setOnClickListener(new View.OnClickListener() {// On click send button
            @Override
            public void onClick(View view) {
                bt_comment_send.setEnabled(false);
                LOGGER.log(Level.FINE, "comment attempt");
                final String bt_comment_comment = et_comment_comment.getText().toString();
                if (!et_comment_comment.equals("")) {// Check if fields are empty

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {// Create respoones listner
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                LOGGER.log(Level.FINE, jsonResponse.toString());
                                String status = jsonResponse.getString("status");
                                if (status.equals("Add")|| status.equals("Deleted")) {// If streamer found is valid move on
                                    bt_comment_send.setEnabled(true);

                                    LOGGER.log(Level.FINE, "Found");
                                    CommentActivity.this.startActivity( new Intent(CommentActivity.this, CommentActivity.class).putExtra("username", username).putExtra("streamername",streamername));
                                } else {//If not found display error dialog
                                    bt_comment_send.setEnabled(true);
                                    LOGGER.log(Level.FINE, "Comment Failed");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                                    builder.setMessage("Comment Failed : Streamer not found").setNegativeButton("Retry", null).create().show();

                                }

                            } catch (JSONException e) {
                                LOGGER.log(Level.SEVERE, e.toString());
                                bt_comment_send.setEnabled(true);

                                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                                builder.setMessage("Search Failed : Sever error").setNegativeButton("Retry", null).create().show();e.printStackTrace();
                            }
                        }
                    };
                    //Create and send respronce
                    Request_Comment_update Comment_update = new Request_Comment_update(streamername,username,bt_comment_comment,"add" , responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
                    queue.add(Comment_update);

                }
                else{
                    bt_comment_send.setEnabled(true);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setMessage("Fill in the comment").setNegativeButton("Ok", null).create().show();

                }

            }



        });

    }
}
