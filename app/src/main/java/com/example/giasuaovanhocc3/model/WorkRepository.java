package com.example.giasuaovanhocc3.model;

import com.example.giasuaovanhocc3.network.ApiClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkRepository {

    public List<Work> getAll() {
        List<Work> result = new ArrayList<>();
        try {
            JSONArray arr = ApiClient.getJsonArray("/works");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                Work w = jsonToWork(o);
                result.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Work> getByGrade(String grade) {
        List<Work> result = new ArrayList<>();
        try {
            JSONArray arr = ApiClient.getJsonArray("/works?grade=" + grade);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                Work w = jsonToWork(o);
                result.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean create(Work work) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", work.getTitle());
            body.put("grade", work.getGrade());
            body.put("summary", work.getSummary());
            body.put("analysis", work.getAnalysis());
            body.put("authorid", work.getAuthorId());
            JSONObject resp = ApiClient.postJson("/works", body);
            return resp != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Integer id, Work work) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", work.getTitle());
            body.put("grade", work.getGrade());
            body.put("summary", work.getSummary());
            body.put("analysis", work.getAnalysis());
            body.put("authorid", work.getAuthorId());
            ApiClient.putJson("/works/" + id, body);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Integer id) {
        try {
            int code = ApiClient.deleteJson("/works/" + id);
            return code == 204 || code == 200;
        } catch (Exception e) {
            return false;
        }
    }

    private Work jsonToWork(JSONObject o) {
        Work w = new Work();
        w.setId(o.optInt("id", 0));
        w.setTitle(o.optString("title", null));
        w.setGrade(o.optInt("grade", 0));
        w.setSummary(o.optString("summary", null));
        w.setAnalysis(o.optString("analysis", null));
        w.setAuthorId(o.optInt("authorid", 0));
        return w;
    }
}

