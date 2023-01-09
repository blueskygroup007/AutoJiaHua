package com.bluesky.autojiahua.ui.special;

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
import com.bluesky.autojiahua.databinding.FragmentSpecialBinding;

public class SpecialFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private SpecialViewModel mViewModel;
    private FragmentSpecialBinding mBinding;
    private GridSpecialAdapter mAdapter;

    public static SpecialFragment newInstance() {
        return new SpecialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentSpecialBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialViewModel.class);
        mAdapter = new GridSpecialAdapter(mBinding.rvSpecialDevices, mViewModel.getHuachan().getValue());
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        mBinding.rvSpecialDevices.setLayoutManager(layoutManager);
        mBinding.rvSpecialDevices.setAdapter(mAdapter);

        //RadioGroup的默认选中,设置监听
        mBinding.rgGroupSpecial.check(mBinding.rbSpecialHuachan.getId());
        mBinding.rgGroupSpecial.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mBinding.rbSpecialHuachan.getId()) {
            mAdapter.setData(mViewModel.getHuachan().getValue());
        } else if (checkedId == mBinding.rbSpecialGanxijiao.getId()) {
            mAdapter.setData(mViewModel.getGanxijiao().getValue());

        }
    }
}