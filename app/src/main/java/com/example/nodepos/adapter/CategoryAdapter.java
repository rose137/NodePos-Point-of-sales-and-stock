package com.example.nodepos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodepos.R;
import com.example.nodepos.admin.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> categoryList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CategoryModel category);
    }

    public CategoryAdapter(List<CategoryModel> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_stock, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categoryList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, subtitle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.categoryIcon);
            title = itemView.findViewById(R.id.categoryTitle);
            subtitle = itemView.findViewById(R.id.categorySub);
        }

        public void bind(CategoryModel model, OnItemClickListener listener) {
            icon.setImageResource(model.getIconResId());
            title.setText(model.getTitle());
            subtitle.setText(model.getSubtitle());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(model);
                }
            });
        }
    }
}

