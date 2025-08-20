package com.example.comp2000referral;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {
    private static final String BASE_URL = "http://10.240.72.69/comp2000/library/";

    // GET members
    public static void getMembers(ApiCallback callback) {
        get("members", callback);
    }

    // GET user
    public static void getMember(String username, ApiCallback callback) {
        get("members/" + username, callback);
    }

    // POST user
    public static void addMember(JSONObject body, ApiCallback callback) {
        post("members", body, callback);
    }

    // PUT(update) user
    public static void updateMember(String username, JSONObject body, ApiCallback callback) {
        put("members/" + username, body, callback);
    }

    //DELETE user
    public static void deleteMember(String username, ApiCallback callback) {
        delete("members/" + username, null, callback);
    }

    // GET book
    public static void issuedBooks(String username, ApiCallback callback) {
        get("books/" + username, callback);
    }

    // POST book
    public static void issueBook(JSONObject body, ApiCallback callback) {
        post("books", body, callback);
    }

    // DELETE book
    public static void deleteBook(JSONObject body, ApiCallback callback) {
        delete("books", body, callback);
    }


    // callback interface to communicate with the UI thread / HTTP Helper
    public interface ApiCallback {
        void onSuccess(String result);
        void onError(Exception e);
    }

    // GET request (all members)
    public static void get(String endpoint, ApiCallback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(BASE_URL + endpoint);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                int responseCode = connection.getResponseCode();
                Log.d("API_CALL", "GET URL: " + url.toString());
                Log.d("API_CALL", "GET Response code: " + responseCode);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                (responseCode >= 200 && responseCode < 300)
                                        ? connection.getInputStream()
                                        : connection.getErrorStream()
                        )
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                String result = response.toString();
                connection.disconnect();

                // passes back results to UI
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));

            } catch (Exception e) {
                Log.e("API_ERROR", "GET failed for " + endpoint, e);
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }

    // POST request
    public static void post(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("POST", endpoint, requestBody, callback);
    }

    // PUT request
    public static void put(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("PUT", endpoint, requestBody, callback);
    }

    // DELETE request
    public static void delete(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("DELETE", endpoint, requestBody, callback);
    }

    // Helper for POST/PUT/DELETE
    private static void sendRequestWithBody(String method, String endpoint, JSONObject requestBody, ApiCallback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(BASE_URL + endpoint);
                Log.d("API_CALL", method + " URL: " + url.toString());

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                if (requestBody != null) {
                    Log.d("API_CALL", method + " Body: " + requestBody.toString());

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write(requestBody.toString());
                    writer.flush();
                    writer.close();
                }

                int responseCode = connection.getResponseCode();
                Log.d("API_CALL", method + " Response code: " + responseCode);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                (responseCode >= 200 && responseCode < 300)
                                        ? connection.getInputStream()
                                        : connection.getErrorStream()
                        )
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                String result = response.toString();
                connection.disconnect();

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));

            } catch (Exception e) {
                Log.e("API_ERROR", "GET failed for " + endpoint, e);
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }
}