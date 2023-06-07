package com.bilan.postsApp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilan.postsApp.DaybooksItem;
import com.bilan.postsApp.R;

import static com.bilan.postsApp.R.id.*;

public class NoteDetailsFragment extends Fragment {
    private DaybooksItem item;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts_details, container, false);
        Bundle args = getArguments();
        if (args != null) {
            this.item = new DaybooksItem();
            item.setTitle(args.getString("title"));
            item.setDescription(args.getString("description"));
            TextView title = view.findViewById(posts_title);
            title.setText(item.getTitle());
            TextView description = view.findViewById(R.id.description);
            description.setText(item.getDescription());
        }

        return view;
    }
}