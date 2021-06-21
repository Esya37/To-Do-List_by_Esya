package com.example.to_do_listbyesya;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesSingleton {
    private static SharedPreferencesSingleton sharedPreferencesSingleton;
    private Gson gson = new Gson();
    SharedPreferences settings;

    private SharedPreferencesSingleton() {
    }

    ;

    public static SharedPreferencesSingleton getSharedPreferencesSingleton() {
        if (sharedPreferencesSingleton == null) {
            sharedPreferencesSingleton = new SharedPreferencesSingleton();
        }
        return sharedPreferencesSingleton;
    }

    public void saveTasks(List<Task> tasks) {
        settings = MainActivity.getMainActivityContext().getSharedPreferences("Tasks", MODE_PRIVATE);
        String tasksString = gson.toJson(tasks);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Tasks", tasksString);
        editor.apply();
    }

    public List<Task> loadTasks() {
        settings = MainActivity.getMainActivityContext().getSharedPreferences("Tasks", MODE_PRIVATE);
        String tasksString = settings.getString("Tasks", null);
        if (tasksString != null) {
            Type type = new TypeToken<List<Task>>() {
            }.getType();
            List<Task> tasks = gson.fromJson(tasksString, type);
            return tasks;
        }
        return new ArrayList<Task>();
    }

}
