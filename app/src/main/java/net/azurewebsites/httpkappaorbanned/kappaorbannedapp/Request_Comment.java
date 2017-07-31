package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

/**
 * Created by dilha on 31/7/2017.
 */
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

public class Request_Comment extends StringRequest {

    private Map<String, String> params;

    public Request_Comment(String streamername, Response.Listener<String> listener) {
        super(Method.GET,"http://kappaorbanned.azurewebsites.net/streamer/"+streamername+"/comment", listener, null);
        params = new HashMap<>();

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}