package com.example.giasuaovanhocc3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giasuaovanhocc3.model.Author;
import com.example.giasuaovanhocc3.model.AuthorRepository;
import com.example.giasuaovanhocc3.model.User;
import com.example.giasuaovanhocc3.model.UserRepository;
import com.example.giasuaovanhocc3.model.Work;
import com.example.giasuaovanhocc3.model.WorkRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository = new UserRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();
    private final WorkRepository workRepository = new WorkRepository();
    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<Author>> authors = new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<List<Work>> works = new MutableLiveData<>(Collections.emptyList());

    public LiveData<User> getCurrentUser() { return currentUser; }
    public LiveData<List<Author>> getAuthors() { return authors; }
    public LiveData<List<Work>> getWorks() { return works; }

    public void loadAuthors() {
        ioExecutor.execute(() -> {
            try {
                authors.postValue(authorRepository.getAll());
            } catch (Throwable t) {
                android.util.Log.e("MainViewModel", "loadAuthors failed", t);
                authors.postValue(Collections.emptyList());
            }
        });
    }

    public void loadAllWorks() {
        ioExecutor.execute(() -> {
            try {
                works.postValue(workRepository.getAll());
            } catch (Throwable t) {
                android.util.Log.e("MainViewModel", "loadAllWorks failed", t);
                works.postValue(Collections.emptyList());
            }
        });
    }

    public void loadWorksByGrade(String grade) {
        ioExecutor.execute(() -> {
            try {
                works.postValue(workRepository.getByGrade(grade));
            } catch (Throwable t) {
                android.util.Log.e("MainViewModel", "loadWorksByGrade failed", t);
                works.postValue(Collections.emptyList());
            }
        });
    }

    public void createUser(User user) {
        ioExecutor.execute(() -> {
            try {
                boolean ok = userRepository.create(user);
                if (ok) currentUser.postValue(user);
            } catch (Throwable t) {
                android.util.Log.e("MainViewModel", "createUser failed", t);
            }
        });
    }

    public void updateUser(String userId, User userUpdate) {
        ioExecutor.execute(() -> {
            try {
                boolean ok = userRepository.update(userId, userUpdate);
                if (ok) currentUser.postValue(userUpdate);
            } catch (Throwable t) {
                android.util.Log.e("MainViewModel", "updateUser failed", t);
            }
        });
    }
}
