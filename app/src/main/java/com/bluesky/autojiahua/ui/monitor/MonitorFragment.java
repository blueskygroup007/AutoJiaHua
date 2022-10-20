package com.bluesky.autojiahua.ui.monitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.databinding.FragmentMonitorBinding;

public class MonitorFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private FragmentMonitorBinding mBinding;
    private MonitorViewModel mMonitorViewModel;
    private GridMonitorAdapter mAdapter;

    public static MonitorFragment newInstance() {
        return new MonitorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_monitor, container, false);
        mBinding = FragmentMonitorBinding.inflate(inflater, container, false);
        mMonitorViewModel = new ViewModelProvider(this).get(MonitorViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new GridMonitorAdapter(mMonitorViewModel.getListPicHuaChanMain());
        mBinding.rvMonitor.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        mBinding.rvMonitor.setAdapter(mAdapter);
        //RadioGroup的默认选中,设置监听
        mBinding.rgGroupMonitor.check(mBinding.rbHuachanMonitor.getId());
        mBinding.rgGroupMonitor.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //返回对应的数组(id,name,drawableId),设置给adapter
        if (checkedId == mBinding.rbHuachanMonitor.getId()) {
            mAdapter.setData(mMonitorViewModel.getListPicHuaChanMain());
        } else if (checkedId == mBinding.rbZhisuanMonitor.getId()) {
            mAdapter.setData(mMonitorViewModel.getListPicHuaChanZhiSuan());
        } else if (checkedId == mBinding.rbGanxijiaoMonitor.getId()) {
            mAdapter.setData(mMonitorViewModel.getListPicGanXiJiao());
        } else if (checkedId == mBinding.rbJiaoluMonitor.getId()) {
            mAdapter.setData(mMonitorViewModel.getListPicJiaoLu());
        }
    }
}