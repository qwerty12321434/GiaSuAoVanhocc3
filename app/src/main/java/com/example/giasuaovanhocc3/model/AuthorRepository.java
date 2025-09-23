package com.example.giasuaovanhocc3.model;

import com.example.giasuaovanhocc3.network.ApiClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    public List<Author> getAll() {
        List<Author> result = new ArrayList<>();
        try {
            JSONArray arr = ApiClient.getJsonArray("/authors");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                Author a = jsonToAuthor(o);
                result.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean create(Author author) {
        try {
            JSONObject body = new JSONObject();
            body.put("name", author.getName());
            body.put("bio", author.getBio());
            JSONObject resp = ApiClient.postJson("/authors", body);
            return resp != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Integer id, Author author) {
        try {
            JSONObject body = new JSONObject();
            body.put("name", author.getName());
            body.put("bio", author.getBio());
            ApiClient.putJson("/authors/" + id, body);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Integer id) {
        try {
            int code = ApiClient.deleteJson("/authors/" + id);
            return code == 204 || code == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private Author jsonToAuthor(JSONObject o) {
        Author a = new Author();
        a.setId(o.optInt("id", 0));
        a.setName(o.optString("name", null));
        a.setBio(o.optString("bio", null));
        return a;
    }
}

