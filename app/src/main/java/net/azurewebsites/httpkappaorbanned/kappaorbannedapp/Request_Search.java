package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

/**
 * Created by dilha on 31/7/2017.
 */

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

public class Request_Search extends StringRequest {
    private static final String Search_REQUEST_URL = "http://kappaorbanned.azurewebsites.netsearch";
    private Map<String, String> params;

    public Request_Search(String streamername, Response.Listener<String> listener) {
        super(Method.POST, Search_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("streamer", streamername);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}



