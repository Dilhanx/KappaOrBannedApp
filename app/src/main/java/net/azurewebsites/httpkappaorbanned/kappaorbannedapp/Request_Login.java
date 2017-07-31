package net.azurewebsites.httpkappaorbanned.kappaorbannedapp;

/**
 * Created by dilha on 31/7/2017.
 */

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

public class Request_Login extends StringRequest {
        private static final String LOGIN_REQUEST_URL = "http://10.0.2.2:5000/login";
        private Map<String, String> params;

        public Request_Login(String username, String password, Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }




