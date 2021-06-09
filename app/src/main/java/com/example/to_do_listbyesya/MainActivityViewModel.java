package com.example.to_do_listbyesya;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private List<Task> tasks = new ArrayList<>();
    MutableLiveData<Task> task_liveData = new MutableLiveData<>();

    public void select_task(Task task)
    {
        task_liveData.setValue(task);
    }
    public void select_task(int position)
    {
        task_liveData.setValue(tasks.get(position));
    }

    public MutableLiveData<Task> get_selected_task()
    {
        return task_liveData;
    }

    public void add_task(Task task)
    {
        tasks.add(task);
    }

    public void add_task(int position, Task task)
    {
        tasks.add(position, task);
    }

    public List<Task> get_all_tasks()
    {
        return tasks;
    }

//    public MutableLiveData<Task> get_task()
//    {
//        task_liveData.setValue(new Task("Доделать To-Do-List", "Не, ну почему бы и не доделать, правда?)", ""));
//        task_liveData.setValue(new Task("Протестировать описание", "Тестим описание", ""));
//        task_liveData.setValue(new Task("Протестировать тестирование", "Тестим тестирование", ""));
//        task_liveData.setValue(new Task("Протестировать очень супер длинное название задачи", "Тестим... дальше лень писать", ""));
//
//        return task_liveData;
//    }

}
