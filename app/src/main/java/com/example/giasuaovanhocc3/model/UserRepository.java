package com.example.giasuaovanhocc3.model;

import com.example.giasuaovanhocc3.network.ApiClient;
import org.json.JSONObject;

public class UserRepository {

    public User getMe(String token) {
        // Optional: add token handling via headers if ApiClient supports it later
        return null;
    }

    public boolean create(User user) {
        try {
            JSONObject body = new JSONObject();
            body.put("full_name", user.getFullName());
            body.put("email", user.getEmail());
            body.put("class", user.getClassName());
            JSONObject resp = ApiClient.postJson("/users", body);
            return resp != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(String id, User userUpdate) {
        try {
            JSONObject body = new JSONObject();
            body.put("full_name", userUpdate.getFullName());
            body.put("email", userUpdate.getEmail());
            body.put("class", userUpdate.getClassName());
            ApiClient.putJson("/users/" + id, body);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            int code = ApiClient.deleteJson("/users/" + id);
            return code == 204 || code == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public User getById(String id) {
        try {
            JSONObject json = ApiClient.getJson("/users/" + id);
            User u = new User();
            u.setId(json.optString("id", null));
            u.setFullName(json.optString("full_name", null));
            u.setEmail(json.optString("email", null));
            u.setClassName(json.optString("class", null));
            return u;
        } catch (Exception e) {
            return null;
        }
    }
}

