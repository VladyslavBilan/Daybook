package com.bilan.postsApp.fragments;

import static com.bilan.postsApp.R.id.buttonSubmit;
import static com.bilan.postsApp.R.id.editTextDescription;
import static com.bilan.postsApp.R.id.editTextTitle;
import static com.bilan.postsApp.R.layout.fragment_posts_create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bilan.postsApp.db.DatabaseHelper;



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

        Button submitButton = view.findViewById(buttonSubmit);
        submitButton.setOnClickListener(v -> {
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
        });
    }
}