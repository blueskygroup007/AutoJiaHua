package com.bluesky.autojiahua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.database.DeviceRepository;
import com.bluesky.autojiahua.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private List<Device> deviceList = new ArrayList<>();
    private DeviceAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //435使用options menu
        setHasOptionsMenu(true);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAdapter = new DeviceAdapter(deviceList);
        subscribeUI(mAdapter);
        return root;
    }

    private void subscribeUI(DeviceAdapter adapter) {
        homeViewModel.getLiveDataDevices().observe(getViewLifecycleOwner(), devices -> {
            mAdapter.setData(devices);
            binding.tvColumnTipDisplay.setText(String.valueOf(devices.size()));
        });

        //创建recyclerview
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setAdapter(mAdapter);

        //当前查询结果的监听

        //恢复下拉列表框选择项
        binding.spinnerQueryDomain.setSelection(homeViewModel.getDomain());
        binding.spinnerQuerySearch.setSelection(homeViewModel.getSearch());
        //区域下拉列表监听
        binding.spinnerQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setDomain(i);
                //查询数据库
                homeViewModel.findDevices();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //搜索下拉列表监听
        binding.spinnerQuerySearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setSearch(i);
                //查询数据库
                homeViewModel.findDevices();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_home_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView mSearchView = (SearchView) item.getActionView();
        //设置是否显示搜索框展开时的提交按钮
        mSearchView.setSubmitButtonEnabled(true);
        //键盘上显示放大镜图标(默认)
        mSearchView.setImeOptions(3);
        mSearchView.setMaxWidth(1000);
        //搜索栏内容变化记录
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String keyWord = s.trim();
                if (!keyWord.isEmpty()) {
                    homeViewModel.setKeyWord(keyWord);
                    homeViewModel.findDevices();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        //搜索栏关闭事件的监听处理
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索内容不为空，查询数据库
                if (!homeViewModel.getKeyWord().isEmpty()) {
                    homeViewModel.findDevices();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}