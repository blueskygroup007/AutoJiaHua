package com.bluesky.autojiahua.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private DevicePagingAdapter devicePagingAdapter;


    class DeviceComparator extends DiffUtil.ItemCallback<Device> {

        @Override
        public boolean areItemsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
            return oldItem.tag.equals(newItem.tag);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
            return oldItem.tag.equals(newItem.tag);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //使用options menu
        setHasOptionsMenu(true);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        devicePagingAdapter = new DevicePagingAdapter(new DeviceComparator());

        subscribeUI(devicePagingAdapter);
        return root;
    }


    private void subscribeUI(DevicePagingAdapter adapter) {

        //创建recyclerview
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setAdapter(adapter);
        //监听搜索关键字
        homeViewModel.getLiveDataKeyword().observe(getViewLifecycleOwner(), s -> {
            //查询数量
            homeViewModel.queryDevicesCount();
            //监听查询结果
            homeViewModel.getAllDevicesByPaging().observe(getViewLifecycleOwner(), devicePagingData -> {
                devicePagingAdapter.submitData(getLifecycle(), devicePagingData);
                //显示总条目数量,不成功,改用变通方法:查询数据库返回数量

                binding.tvColumnTipDisplay.setText(String.valueOf(devicePagingAdapter.getItemCount()));
                Log.e("getLiveDataKeyword", "snapshot.getitem.size= " + adapter.snapshot().getItems().size());
                Log.e("getLiveDataKeyword", "adapter.getitemcount= " + devicePagingAdapter.getItemCount());

            });
        });

        //查询数量监听
        homeViewModel.getCountDevices().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvColumnTipDisplay.setText(String.valueOf(integer));
            }
        });

        //恢复下拉列表框选择项
        binding.spinnerQueryDomain.setSelection(homeViewModel.getDomain());
        binding.spinnerQuerySearch.setSelection(homeViewModel.getSearch());
        //区域下拉列表监听
        binding.spinnerQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setDomain(i);
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
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}