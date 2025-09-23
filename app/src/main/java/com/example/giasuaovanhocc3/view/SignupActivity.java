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

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignup = findViewById(R.id.btnCreateAccount);
        TextView tvLogin = findViewById(R.id.tvGoLogin);
        ImageButton btnBack = findViewById(R.id.btnBackSignup);
        EditText edtName = findViewById(R.id.edtName);
        EditText edtEmail = findViewById(R.id.edtEmailSignup);
        EditText edtPassword = findViewById(R.id.edtPasswordSignup);

        btnSignup.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString();
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(() -> {
                try {
                    JSONObject body = new JSONObject();
                    body.put("name", name);
                    body.put("email", email);
                    body.put("password", password);
                    JSONObject resp = ApiClient.postJson("/auth/signup", body);
                    String token = resp.optString("token", null);
                    if (token != null) {
                        SessionManager.saveToken(this, token);
                    }
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
        tvLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        btnBack.setOnClickListener(v -> finish());
    }
}


