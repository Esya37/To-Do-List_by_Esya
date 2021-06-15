package com.example.to_do_listbyesya;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ShowTaskFragment extends Fragment {

    public ShowTaskFragment() {
        // Required empty public constructor
    }

    public static ShowTaskFragment newInstance() {
        ShowTaskFragment fragment = new ShowTaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MainActivityViewModel model;
    TextView nameTextView;
    TextView descriptionTextView;
    ImageView photoImageView;
    View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        inflatedView = inflater.inflate(R.layout.fragment_show_task, container, false);
        nameTextView = inflatedView.findViewById(R.id.nameShowTaskFragmentTextView);
        descriptionTextView = inflatedView.findViewById(R.id.descriptionShowTaskFragmentTextView);
        photoImageView = inflatedView.findViewById(R.id.photoShowTaskFragmentImageView);

        LiveData<Task> taskLiveData = model.getSelectedTask();
        taskLiveData.observe(this.getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                nameTextView.setText(task.getName());
                descriptionTextView.setText((task.getDescription()));
                photoImageView.setImageURI(Uri.parse(task.getImageUri()));

            }
        });

        return inflatedView;
    }
}