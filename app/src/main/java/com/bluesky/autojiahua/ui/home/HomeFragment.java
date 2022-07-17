package com.bluesky.autojiahua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private List<Device> deviceList=new ArrayList<>();
    private DeviceAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAdapter = new DeviceAdapter(deviceList);
        subscribeUI(mAdapter);
        return root;
    }

    private void subscribeUI(DeviceAdapter adapter) {
        homeViewModel.getDevices().observe(getViewLifecycleOwner(), devices -> adapter.setData(devices));

        //todo 创建recyclerview
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}