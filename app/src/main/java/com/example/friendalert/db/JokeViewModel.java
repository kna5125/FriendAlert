package com.example.friendalert.db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JokeViewModel extends AndroidViewModel {
    private LiveData<List<Joke>> jokes;

    public JokeViewModel (Application application) {
        super(application);
    }

    public void filterJokes(boolean onlyLiked) {
        if (onlyLiked)
            jokes = JokeDatabase.getDatabase(getApplication()).jokeDAO().getLiked(true);
        else
            jokes = JokeDatabase.getDatabase(getApplication()).jokeDAO().getAll();
    }

    public LiveData<List<Joke>> getAllJokes() {
        return jokes;
    }
}