package com.example.nodepos.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodepos.R;
import com.example.nodepos.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {



    public StockFragment() {
        // Diperlukan constructor kosong
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Ganti layout ini dengan layout fragment kamu
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 kolom

        List<CategoryModel> categoryList = getCategoryList();
        CategoryAdapter adapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryModel category) {
                Toast.makeText(getContext(), "Clicked: " + category.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<CategoryModel> getCategoryList() {
        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel(R.drawable.ibubayianak, "Kebutuhan Ibu & Anak", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.produksegar2, "Produk Segar & Beku", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.minuman, "Minuman", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.kebutuhanrumahtangga, "Kebutuhan Rumah Tangga", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.petfood, "Pet Food", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.personalcare, "Personal Care", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.makanan, "Makanan", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.kebutuhandapur, "Kebutuhan Dapur", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.kesehatan, "Kesehatan", "8000+ Stock"));
        list.add(new CategoryModel(R.drawable.lifestyle, "Life Style", "8000+ Stock"));
        return list;
    }


}
