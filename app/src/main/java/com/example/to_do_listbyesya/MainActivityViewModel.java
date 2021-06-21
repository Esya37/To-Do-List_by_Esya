package com.example.to_do_listbyesya;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private List<Task> tasks = new ArrayList<>();
    MutableLiveData<Task> taskLiveData = new MutableLiveData<>();
    SharedPreferencesSingleton sharedPreferencesSingleton = SharedPreferencesSingleton.getSharedPreferencesSingleton();

    public void selectTask(Task task) {
        taskLiveData.setValue(task);
    }

    public void selectTask(int position) {
        taskLiveData.setValue(tasks.get(position));
    }

    public MutableLiveData<Task> getSelectedTask() {
        return taskLiveData;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTask(int position, Task task) {
        tasks.add(position, task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void setAllTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void saveTasks() {
        sharedPreferencesSingleton.saveTasks(getAllTasks());
    }

    public void loadTasks() {
        setAllTasks(sharedPreferencesSingleton.loadTasks());
    }

    public void editTask(int position, Task newTask){
        tasks.remove(position);
        addTask(position, newTask);
    }
    public void deleteTask(int position){
        tasks.remove(position);
    }

}
