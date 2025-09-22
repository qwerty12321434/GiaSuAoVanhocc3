package com.example.giasuaovanhocc3.network;

import android.util.Log;

import com.example.giasuaovanhocc3.BuildConfig;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    private static final String TAG = "ApiClient";

    public static JSONObject postJson(String path, JSONObject body) throws Exception {
        String base = BuildConfig.API_BASE_URL;
        URL url = new URL(base + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        byte[] data = body.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(data);
        }

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        String resp = sb.toString();
        Log.d(TAG, "POST " + path + " -> " + code + ": " + resp);
        if (code >= 200 && code < 300) {
            return new JSONObject(resp);
        } else {
            throw new Exception("HTTP " + code + ": " + resp);
        }
    }

    public static JSONObject getJson(String path) throws Exception {
        String base = BuildConfig.API_BASE_URL;
        URL url = new URL(base + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        String resp = sb.toString();
        Log.d(TAG, "GET " + path + " -> " + code + ": " + resp);
        if (code >= 200 && code < 300) {
            return new JSONObject(resp);
        } else {
            throw new Exception("HTTP " + code + ": " + resp);
        }
    }

    public static JSONArray getJsonArray(String path) throws Exception {
        String base = BuildConfig.API_BASE_URL;
        URL url = new URL(base + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        String resp = sb.toString();
        Log.d(TAG, "GET " + path + " -> " + code + ": " + resp);
        if (code >= 200 && code < 300) {
            return new JSONArray(resp);
        } else {
            throw new Exception("HTTP " + code + ": " + resp);
        }
    }

    public static JSONObject putJson(String path, JSONObject body) throws Exception {
        String base = BuildConfig.API_BASE_URL;
        URL url = new URL(base + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        byte[] data = body.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(data);
        }

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
                StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        String resp = sb.toString();
        Log.d(TAG, "PUT " + path + " -> " + code + ": " + resp);
        if (code >= 200 && code < 300) {
            return new JSONObject(resp);
        } else {
            throw new Exception("HTTP " + code + ": " + resp);
        }
    }

    public static int deleteJson(String path) throws Exception {
        String base = BuildConfig.API_BASE_URL;
        URL url = new URL(base + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        int code = conn.getResponseCode();
        Log.d(TAG, "DELETE " + path + " -> " + code);
        return code;
    }
}



