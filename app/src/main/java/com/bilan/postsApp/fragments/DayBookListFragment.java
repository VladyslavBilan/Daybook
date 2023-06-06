package com.bilan.postsApp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bilan.postsApp.DaybooksItem;
import com.bilan.postsApp.DayBookListAdapter;
import com.bilan.postsApp.R;

import com.bilan.postsApp.databinding.FragmentPostsListBinding;
import com.bilan.postsApp.db.DatabaseHelper;
import com.bilan.postsApp.list.OnRecyclerViewItemClickListener;
import com.bilan.postsApp.list.OnRemoveButtonClickListener;

import java.util.List;

import static android.app.AlertDialog.*;
import static androidx.navigation.Navigation.findNavController;
import static com.bilan.postsApp.R.id.*;
import static com.bilan.postsApp.databinding.FragmentPostsListBinding.inflate;


public class DayBookListFragment extends Fragment implements OnRecyclerViewItemClickListener, OnRemoveButtonClickListener {
    private FragmentPostsListBinding fragmentPostsListBinding;
    private DayBookListAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentPostsListBinding = inflate(getLayoutInflater());
        databaseHelper = new DatabaseHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initRecyclerView();
        adapter.setPostsList(databaseHelper.loadAllNote());
        adapter.setDatabaseHelper(databaseHelper);
        adapter.setItemClickListener(this);
        adapter.setRemoveButtonClickListener(this);
        View rootView = fragmentPostsListBinding.getRoot();
        Button createButton = rootView.findViewById(R.id.createArticleBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(v).navigate(action_postsListFragment_to_postsCreateFragment, null);
            }
        });
        Button searchButton = rootView.findViewById(buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClick(rootView);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setPostsList(databaseHelper.loadAllNote());
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        RecyclerView postsRecyclerView = fragmentPostsListBinding.recyclerViewList;
        if(adapter == null) {
            adapter = new DayBookListAdapter();
            postsRecyclerView.setAdapter(adapter);
            postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    public void onSearchButtonClick(View rootView) {
        EditText searchText = rootView.findViewById(searchEditText);
        String searchTextStr = searchText.getText().toString();
        if (searchTextStr.equals("")) {
            adapter.setPostsList(databaseHelper.loadAllNote());
        } else {
            List<DaybooksItem> foundArticles = databaseHelper.getPostsByTitle(searchText.getText().toString());
            adapter.setPostsList(foundArticles);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(DaybooksItem item, View view) {
        Bundle args = new Bundle();
        args.putString("title", item.getTitle());
        args.putString("description", item.getDescription());
        findNavController(view).navigate(action_postsListFragment_to_postsDetailsFragment, args);
    }

    @Override
    public void onRemoveBtnClick(View v) {
        Builder builder = new Builder(this.getContext());
        builder.setMessage("Are you sure you want to delete this article?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteArticleById(Integer.parseInt(v.getContentDescription().toString()));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteArticleById(int id) {
        System.out.println("Delete : " + id);
        databaseHelper.deleteArticleById(id);
        List<DaybooksItem> articles = adapter.getPostsList();
        for (DaybooksItem item : articles) {
            if (item.getId() == id) {
                articles.remove(item);
                break;
            }
        }
        adapter.setPostsList(articles);
        adapter.notifyDataSetChanged();
    }
}