package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

/**
 * Created by dilha on 31/7/2017.
 */
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

public class Request_Comment_update extends StringRequest {

    private Map<String, String> params;


    public Request_Comment_update(String streamername,String username,String comment,String type, Response.Listener<String> listener) {
        super(Method.POST,"http://kappaorbanned.azurewebsites.net/streamer/"+streamername+"/comment", listener, null);
        params = new HashMap<>();
        params.put("streamername", streamername);
        params.put("username", username);
        params.put("comment", comment);
        params.put("type", type);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}