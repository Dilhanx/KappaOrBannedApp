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


public class EmoteActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(  EmoteActivity.class.getName() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emote);


        final ListView lv_Emote = findViewById(R.id.lv_emote);

        Intent intent = getIntent();

        final String streamername = intent.getStringExtra("streamername");

        String[] comment_array = new String[]{

        };

        final List<String> emote_array_list = new ArrayList<String>(Arrays.asList(comment_array));
        final ArrayAdapter eAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,emote_array_list);
        lv_Emote.setAdapter(eAdapter);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {// Create response listner
                try {

                    JSONArray  jsonArray = new JSONArray (response);

                    for(int i=0;i<jsonArray.length();i++){
                        emote_array_list .add(jsonArray.getJSONObject(i).getString("rank")+":"+jsonArray.getJSONObject(i).getString("emote"));
                    }
                    eAdapter.notifyDataSetChanged();

                } catch (JSONException e) {

                    LOGGER.log(Level.SEVERE, e.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(EmoteActivity.this);
                    builder.setMessage("Faild").setNegativeButton("Retry", null).create().show();
                    e.printStackTrace();
                    //StreamerActivity.this.startActivity( new Intent(StreamerActivity.this, StartActivity.class));
                } finally {

                }
            }
        };
        //Create and send response
        Request_Emote Emote = new Request_Emote(streamername, responseListener);
        RequestQueue queue = Volley.newRequestQueue(EmoteActivity.this);
        queue.add(Emote);

    }
}
