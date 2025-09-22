package com.example.giasuaovanhocc3.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giasuaovanhocc3.R;
import com.example.giasuaovanhocc3.model.Work;
import com.example.giasuaovanhocc3.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorksActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private WorksAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        RecyclerView rv = findViewById(R.id.rvWorks);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorksAdapter();
        rv.setAdapter(adapter);

        AutoCompleteTextView gradeInput = findViewById(R.id.inputGrade);
        String[] grades = new String[]{"6","7","8","9","10","11","12"};
        gradeInput.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, grades));
        gradeInput.setOnItemClickListener((parent, view, position, id) -> {
            String g = (String) parent.getItemAtPosition(position);
            viewModel.loadWorksByGrade(g);
        });

        viewModel.getWorks().observe(this, works -> adapter.submit(works != null ? works : new ArrayList<>()));

        viewModel.loadAllWorks();
    }

    static class WorksAdapter extends RecyclerView.Adapter<WorkViewHolder> {
        private final List<Work> items = new ArrayList<>();

        void submit(List<Work> data) {
            items.clear();
            items.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public WorkViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            android.view.View v = android.view.LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new WorkViewHolder(v);
        }

        @Override
        public void onBindViewHolder(WorkViewHolder holder, int position) {
            Work w = items.get(position);
            holder.title.setText(w.getTitle());
            holder.subtitle.setText("Lớp: " + (w.getGrade() != null ? w.getGrade() : "-"));
        }

        @Override
        public int getItemCount() { return items.size(); }
    }

    static class WorkViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView title;
        android.widget.TextView subtitle;
        WorkViewHolder(android.view.View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}


