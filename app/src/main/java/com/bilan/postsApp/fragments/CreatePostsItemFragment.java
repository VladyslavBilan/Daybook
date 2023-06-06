package com.bilan.postsApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bilan.postsApp.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;
import static com.bilan.postsApp.R.id.*;
import static com.bilan.postsApp.R.layout.fragment_posts_create;
import static com.bilan.postsApp.R.layout.spinner_item_layout;


public class CreatePostsItemFragment extends Fragment {
    private DatabaseHelper databaseHelper;

    public CreatePostsItemFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.databaseHelper = new DatabaseHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(fragment_posts_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(),
                spinner_item_layout
        );
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);

        Button submitButton = view.findViewById(buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = view.findViewById(editTextTitle);
                EditText description = view.findViewById(editTextDescription);
                databaseHelper.addNote(
                        title.getText().toString(),
                        description.getText().toString()
                );
                title.clearFocus();
                title.clearComposingText();
                title.clearComposingText();
                description.clearComposingText();
                requireActivity().onBackPressed();
            }
        });
    }
}