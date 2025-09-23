package com.example.giasuaovanhocc3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giasuaovanhocc3.R;
import com.example.giasuaovanhocc3.network.ApiClient;
import com.example.giasuaovanhocc3.network.SessionManager;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvSignup = findViewById(R.id.tvSignup);
        ImageButton btnBack = findViewById(R.id.btnBackLogin);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(() -> {
                try {
                    JSONObject body = new JSONObject();
                    body.put("email", email);
                    body.put("password", password);
                    JSONObject resp = ApiClient.postJson("/auth/login", body);
                    String token = resp.optString("token", null);
                    if (token != null) {
                        SessionManager.saveToken(this, token);
                    }
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
        tvSignup.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }
}


