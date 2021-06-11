package com.example.to_do_listbyesya;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksListFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public TasksListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksListFragment.
     */

    public static TasksListFragment newInstance(String param1, String param2) {
        TasksListFragment fragment = new TasksListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView tasks;
    RecyclerViewAdapter adapter;
    View inflatedView;
    MainActivityViewModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
        Button add_task_button = (Button) inflatedView.findViewById(R.id.add_task_button);
        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(TasksListFragment.this).navigate(R.id.action_tasksListFragment_to_addTaskFragment);
            }
        });

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        if(model.get_all_tasks().isEmpty()==true) {          //Начальная инициализация данных
            model.add_task(new Task("Доделать To-Do-List", "Не, ну почему бы и не доделать, правда?)", ""));
            model.add_task(new Task("Протестировать описание", "Тестим описание", ""));
            model.add_task(new Task("Протестировать тестирование", "Тестим тестирование", ""));
            model.add_task(new Task("Протестировать очень супер длинное название задачи", "Тестим... дальше лень писать", ""));
        }
//        model.task_liveData.observe(this.getViewLifecycleOwner(), new Observer<Task>() {
//            @Override
//            public void onChanged(Task task) {
//
//            }
//        });

        tasks = (RecyclerView) inflatedView.findViewById(R.id.tasks_recycler_view);

        tasks.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        adapter = new RecyclerViewAdapter(inflatedView.getContext(), model.get_all_tasks());
        adapter.setClickListener(this::onItemClick);
        tasks.addItemDecoration(new RecyclerViewAdapter.SpacesItemDecoration(20));
        tasks.setAdapter(adapter);

        // Inflate the layout for this fragment
        return inflatedView;
    }

    public void onItemClick(View view, int position) {
//        model.add_task(2, new Task("Test", "test", ""));
//        adapter.notifyItemInserted(2);
        model.select_task(position);
        NavHostFragment.findNavController(TasksListFragment.this).navigate(R.id.showTaskFragment);
    }

}