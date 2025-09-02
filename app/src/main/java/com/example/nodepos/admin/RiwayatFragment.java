package com.example.nodepos.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nodepos.R;
import com.example.nodepos.model.riwayatModel;

import java.util.ArrayList;
import java.util.List;


public class RiwayatFragment extends Fragment {


    private RecyclerView rvHistory;
    private RiwayatAdapter adapter;
    private List<riwayatModel> transactionList;
    public RiwayatFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // hubungkan fragment dengan layout fragment_history.xml
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);

        rvHistory = view.findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy Data
        transactionList = new ArrayList<>();
        transactionList.add(new riwayatModel("INV-001", "Selesai", "01-09-2025", "Rp 250.000"));
        transactionList.add(new riwayatModel("INV-002", "Pending", "01-09-2025", "Rp 125.000"));
        transactionList.add(new riwayatModel("INV-003", "Batal", "02-09-2025", "Rp 300.000"));
        transactionList.add(new riwayatModel("INV-004", "Selesai", "02-09-2025", "Rp 99.000"));

        adapter = new RiwayatAdapter(getContext(), transactionList);
        rvHistory.setAdapter(adapter);

        return view;
    }
}