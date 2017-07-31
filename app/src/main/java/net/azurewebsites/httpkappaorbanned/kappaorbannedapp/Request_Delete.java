package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

/**
 * Created by dilha on 31/7/2017.
 */

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

public class Request_Delete extends StringRequest {
    private static final String Delete_REQUEST_URL = "http://kappaorbanned.azurewebsites.net/delete";
    private Map<String, String> params;

    public Request_Delete(String username,String password, Response.Listener<String> listener) {
        super(Method.POST, Delete_REQUEST_URL , listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}