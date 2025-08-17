package com.example.comp2000referral;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClient {private static final String BASE_URL = "http://10.224.41.18/comp2000/library/";

    // callback interface to communicate with the UI thread
    public interface ApiCallback {
        void onSuccess(String result);
        void onError(Exception e);
    }

    // GET request
    public static void get(String endpoint, ApiCallback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(BASE_URL + endpoint);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                int responseCode = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                (responseCode >= 200 && responseCode < 300)
                                        ? connection.getInputStream()
                                        : connection.getErrorStream()
                        )
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                final String result = response.toString();
                reader.close();
                connection.disconnect();

                // passes back results to UI
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }

    // POST request (JSON body)
    public static void post(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("POST", endpoint, requestBody, callback);
    }

    // PUT request (with JSON body)
    public static void put(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("PUT", endpoint, requestBody, callback);
    }

    // DELETE request (can be with or without body)
    public static void delete(String endpoint, JSONObject requestBody, ApiCallback callback) {
        sendRequestWithBody("DELETE", endpoint, requestBody, callback);
    }

    // Helper for POST/PUT/DELETE
    private static void sendRequestWithBody(String method, String endpoint, JSONObject requestBody, ApiCallback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(BASE_URL + endpoint);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                if (requestBody != null) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    writer.write(requestBody.toString());
                    writer.flush();
                    writer.close();
                }

                int responseCode = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                (responseCode >= 200 && responseCode < 300)
                                        ? connection.getInputStream()
                                        : connection.getErrorStream()
                        )
                );

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                final String result = response.toString();
                reader.close();
                connection.disconnect();

                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(result));

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }
}