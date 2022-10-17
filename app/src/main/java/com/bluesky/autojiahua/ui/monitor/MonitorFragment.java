package com.bluesky.autojiahua.ui.monitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.databinding.FragmentMonitorBinding;

public class MonitorFragment extends Fragment {

    private FragmentMonitorBinding mBinding;
    private MonitorViewModel mMonitorViewModel;

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
        
    }
}