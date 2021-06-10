package com.example.to_do_listbyesya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;
    MainActivityViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("Tasks", MODE_PRIVATE);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        load_tasks();
    }

    @Override
    protected void onPause(){
        super.onPause();

        save_tasks();
    }

    public void save_tasks()
    {
        List<Task> tasks = new ArrayList<Task>();
        tasks = model.get_all_tasks();
        String tasks_string = new Gson().toJson(tasks);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Tasks", tasks_string);
        editor.apply();
    }
    public void load_tasks()
    {
        String tasks_string = settings.getString("Tasks", null);
        if(tasks_string!=null) {
            Type type = new TypeToken<List<Task>>() {}.getType();
            List<Task> tasks = new Gson().fromJson(tasks_string, type);
            model.set_all_tasks(tasks);
        }
    }
}