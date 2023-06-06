package com.bilan.postsApp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bilan.postsApp.databinding.PostsItemBinding;
import com.bilan.postsApp.db.DatabaseHelper;
import com.bilan.postsApp.list.OnRecyclerViewItemClickListener;
import com.bilan.postsApp.list.OnRemoveButtonClickListener;

import java.util.List;

import static android.view.LayoutInflater.from;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.*;
import static com.bilan.postsApp.databinding.PostsItemBinding.inflate;
import static java.lang.String.valueOf;
import static java.util.Collections.*;

public class DayBookListAdapter extends RecyclerView.Adapter<DayBookListAdapter.PostsViewHolder> {
    private List<DaybooksItem> mPostsList = emptyList();
    private DatabaseHelper databaseHelper;
    private OnRecyclerViewItemClickListener itemClickListener;
    private OnRemoveButtonClickListener removeButtonClickListener;

    class PostsViewHolder extends ViewHolder {
       private PostsItemBinding postsItemBinding;
       private DaybooksItem daybooksItem;

        public PostsViewHolder(PostsItemBinding binding) {
            super(binding.getRoot());
            postsItemBinding = binding;
        }

        public void bind(DaybooksItem daybooksItem) {
            this.daybooksItem = daybooksItem;
            postsItemBinding.postsTitle.setText(daybooksItem.getTitle());
            postsItemBinding.category.setText(daybooksItem.getCategory());
            postsItemBinding.source.setText(daybooksItem.getSource());
            postsItemBinding.removeButton.setContentDescription(valueOf(daybooksItem.getId()));

            Glide.with(postsItemBinding.postsImage.getContext())
                    .load(daybooksItem.getUrl())
                    //.placeholder(R.mipmap.poster_tmp)
                    //.error(R.drawable.ic_baseline_error_outline_24)
                    .diskCacheStrategy(RESOURCE)
                    .into(postsItemBinding.image);
        }

        public DaybooksItem getPostsItem() {
            return daybooksItem;
        }
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = from(parent.getContext());
        PostsItemBinding postsItemBinding = inflate(inflater, parent, false);
        return new PostsViewHolder(postsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder holder, final int position) {
        holder.bind(mPostsList.get(position));

        holder.postsItemBinding.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (removeButtonClickListener != null) {
                    removeButtonClickListener.onRemoveBtnClick(v);
                }
            }
        });

        holder.postsItemBinding.postsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getPostsItem(), view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostsList.size();
    }

    public void setPostsList(List<DaybooksItem> posts) {
        this.mPostsList = posts;
        Log.e(DayBookListAdapter.class.getCanonicalName(), this.mPostsList.toString());
    }

    public List<DaybooksItem> getPostsList() {
        return mPostsList;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mClickListener) {
        this.itemClickListener = mClickListener;
    }

    public void setRemoveButtonClickListener(OnRemoveButtonClickListener removeButtonClickListener) {
        this.removeButtonClickListener = removeButtonClickListener;
    }
}
