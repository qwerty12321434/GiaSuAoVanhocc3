package com.example.giasuaovanhocc3.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.giasuaovanhocc3.R;
import com.example.giasuaovanhocc3.model.User;
import com.example.giasuaovanhocc3.viewmodel.MainViewModel;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        EditText fullName = findViewById(R.id.edtFullName);
        EditText className = findViewById(R.id.edtClass);
        EditText goals = findViewById(R.id.edtGoals);
        Button btnSave = findViewById(R.id.btnSaveProfile);

        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                fullName.setText(user.getFullName());
                className.setText(user.getClassName());
                if (user.getGoals() != null) {
                    String joined = String.join(", ", user.getGoals());
                    goals.setText(joined);
                }
            }
        });

        btnSave.setOnClickListener(v -> {
            String name = fullName.getText().toString().trim();
            String cls = className.getText().toString().trim();
            String goalsStr = goals.getText().toString().trim();

            User u = new User();
            u.setFullName(name);
            u.setClassName(cls);
            if (!goalsStr.isEmpty()) {
                u.setGoals(Arrays.asList(goalsStr.split(",\\s*")));
            }

            if (viewModel.getCurrentUser().getValue() == null) {
                viewModel.createUser(u);
            } else {
                // Replace with real user id when available from backend
                viewModel.updateUser("me", u);
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        });
    }
}


