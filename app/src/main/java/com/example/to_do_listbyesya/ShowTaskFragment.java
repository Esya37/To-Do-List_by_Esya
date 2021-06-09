package com.example.to_do_listbyesya;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowTaskFragment newInstance(String param1, String param2) {
        ShowTaskFragment fragment = new ShowTaskFragment();
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

    MainActivityViewModel model;
    TextView name_textView;
    TextView description_textView;
    View inflatedView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        inflatedView = inflater.inflate(R.layout.fragment_show_task, container, false);
        name_textView = inflatedView.findViewById(R.id.name_textView);
        description_textView = inflatedView.findViewById(R.id.description_textView);

        LiveData<Task> task_livedata = model.get_selected_task();
        task_livedata.observe(this.getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                name_textView.setText(task.get_name());
                description_textView.setText((task.get_description()));
            }
        });

        // Inflate the layout for this fragment
        return inflatedView;
    }
}