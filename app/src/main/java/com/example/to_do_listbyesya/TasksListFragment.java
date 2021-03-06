package com.example.to_do_listbyesya;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TasksListFragment extends Fragment {

    public TasksListFragment() {
        // Required empty public constructor
    }

    public static TasksListFragment newInstance() {
        TasksListFragment fragment = new TasksListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView tasks;
    RecyclerViewAdapter adapter;
    View inflatedView;
    MainActivityViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        Button addTaskButton = (Button) inflatedView.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = AddTaskFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
            }
        });

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        if (model.getAllTasks().isEmpty() == true) {          //Начальная инициализация данных
            model.addTask(new Task("Доделать To-Do-List", "Не, ну почему бы и не доделать, правда?)", ""));
            model.addTask(new Task("Протестировать описание", "Тестим описание", ""));
            model.addTask(new Task("Протестировать тестирование", "Тестим тестирование", ""));
            model.addTask(new Task("Протестировать очень супер длинное название задачи", "Тестим... дальше лень писать", ""));
        }

        tasks = (RecyclerView) inflatedView.findViewById(R.id.tasksRecyclerView);

        tasks.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        adapter = new RecyclerViewAdapter(inflatedView.getContext(), model.getAllTasks());
        adapter.setClickListener(this::onItemClick);
        tasks.setAdapter(adapter);

        SwipeAdapter swipeAdapter = new SwipeAdapter(new SwipeAdapterActions()
        {
            @Override
            public void onEditClick(int position) {
                model.selectTask(position);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = AddTaskFragment.newInstance(true, position);
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
                super.onEditClick(position);
            }

            @Override
            public void onDeleteClick(int position) {
                model.deleteTask(position);
                adapter.notifyItemRemoved(position);
                super.onDeleteClick(position);
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeAdapter);
        itemTouchhelper.attachToRecyclerView(tasks);
        tasks.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeAdapter.onDraw(c);
            }
        });
        tasks.addItemDecoration(new RecyclerViewAdapter.SpacesItemDecoration());

        return inflatedView;
    }

    public void onItemClick(View view, int position) {

        model.selectTask(position);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = ShowTaskFragment.newInstance();
        fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
    }

}