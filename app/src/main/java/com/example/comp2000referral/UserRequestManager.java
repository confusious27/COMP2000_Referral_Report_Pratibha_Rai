package com.example.comp2000referral;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


// manages/saves request SHARED PREFERENCES
public class UserRequestManager {
    private static final String PREFS_NAME = "user_requests";
    private static final String KEY_REQUESTS = "requests";

    private SharedPreferences prefs;
    private Gson gson;

    public UserRequestManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<UserRequest> getRequests() {
        String json = prefs.getString(KEY_REQUESTS, null);
        if (json == null) return new ArrayList<>();
        Type listType = new TypeToken<List<UserRequest>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    public void addRequest(UserRequest request) {
        List<UserRequest> requests = getRequests();
        requests.add(request);
        saveRequests(requests);
    }

    private void saveRequests(List<UserRequest> requests) {
        String json = gson.toJson(requests);
        prefs.edit().putString(KEY_REQUESTS, json).apply();
    }

    // prevents duplicate requests
    public boolean hasRequested(String bookTitle) {
        List<UserRequest> requests = getRequests();
        for (UserRequest request : requests) {
            if (request.getBookTitle().equalsIgnoreCase(bookTitle)) {
                return true;
            }
        }
        return false;
    }

    // updates the request
    public void updateRequests(List<UserRequest> requests) {
        saveRequests(requests);
    }

    public void clearRequests() {
        prefs.edit().remove(KEY_REQUESTS).apply();
    }
}