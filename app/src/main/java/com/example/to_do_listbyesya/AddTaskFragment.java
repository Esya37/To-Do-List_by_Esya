package com.example.to_do_listbyesya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskFragment extends Fragment {

    private static final String ARG_PARAM1 = "isEdited";
    private static final String ARG_PARAM2 = "position";
    private boolean isEdited = false;
    private int position = 0;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
        return fragment;
    }

    public static AddTaskFragment newInstance(boolean isEdited, int position) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdited);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEdited = getArguments().getBoolean(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    MainActivityViewModel model;
    EditText nameEditText;
    EditText descriptionEditText;
    View inflatedView;
    ImageView imageView;
    Uri imageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        inflatedView = inflater.inflate(R.layout.fragment_add_task, container, false);
        nameEditText = inflatedView.findViewById(R.id.nameEditText);
        descriptionEditText = inflatedView.findViewById(R.id.descriptionEditText);
        imageView = (ImageView) inflatedView.findViewById(R.id.imageView2);
        LiveData<Task> taskLiveData;
        if(isEdited==true)
        {
            TextView textView = inflatedView.findViewById(R.id.addTaskTextView);
            textView.setText(getResources().getString(R.string.change_task));
            taskLiveData = model.getSelectedTask();
            taskLiveData.observe(this.getViewLifecycleOwner(), new Observer<Task>() {
                @Override
                public void onChanged(Task task) {
                    nameEditText.setText(task.getName());
                    descriptionEditText.setText((task.getDescription()));
                    imageUri = Uri.parse(task.getImageUri());
                    imageView.setImageURI(imageUri);

                }
            });
        }
        else {
            nameEditText.setText("");
            descriptionEditText.setText("");
        }

        Button confirmButton = (Button) inflatedView.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty((String) nameEditText.getText().toString().trim())) {
                    Toast.makeText(getContext(), "Нельзя добавить задачу без названия", Toast.LENGTH_SHORT).show();
                } else {
                    Task newTask;
                    if(isEdited==true)
                    {
                        newTask = new Task(nameEditText.getText().toString(), descriptionEditText.getText().toString(), getRealPathFromURI(getContext(), imageUri));
                        model.editTask(position ,newTask);
                    }
                    else {
                        if (imageUri == null) {
                            newTask = new Task(nameEditText.getText().toString(), descriptionEditText.getText().toString(), "");
                        } else {
                            newTask = new Task(nameEditText.getText().toString(), descriptionEditText.getText().toString(), getRealPathFromURI(getContext(), imageUri));
                        }
                        model.addTask(newTask);
                    }
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
            }
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageView.setImageURI(imageUri);
                        }
                    }
                });

        Button addPhoto = (Button) inflatedView.findViewById(R.id.addPhotoButton);
        addPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                someActivityResultLauncher.launch(photoPickerIntent);
            }
        });

        return inflatedView;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if(cursor==null)
            {
                return contentUri.toString();
            }
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

