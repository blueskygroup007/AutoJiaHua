package com.bluesky.autojiahua.ui.interlock;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.database.DeviceRepository;
import com.bluesky.autojiahua.databinding.FragmentInterlockBinding;

import java.util.List;

public class InterlockFragment extends Fragment {

    private InterlockViewModel mViewModel;
    private FragmentInterlockBinding mBinding;
    private InterLockRecyclerViewAdapter mAdapter;

    public static InterlockFragment newInstance() {
        return new InterlockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InterlockViewModel.class);

        mBinding = FragmentInterlockBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();
        //return inflater.inflate(R.layout.fragment_interlock, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new InterLockRecyclerViewAdapter(mBinding.rvListInterlock);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        mBinding.rvListInterlock.setLayoutManager(linearLayoutManager);
        //setLayoutManager必须在setAdapter方法之前调用
        mBinding.rvListInterlock.setAdapter(mAdapter);
        mBinding.rvListInterlock.setHasFixedSize(true);
        //监听查询数据
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<InterLock>>() {
            @Override
            public void onChanged(List<InterLock> interLocks) {
                if (interLocks != null) {
                    mAdapter.setData(interLocks);
                    mBinding.tvInterlockColumnCount.setText(String.valueOf(interLocks.size()));
                }
            }
        });
        //恢复界面元素
        mBinding.spinnerInterlockDomain.setSelection(mViewModel.getDomainPosition());
        //下拉列表点击监听
        mBinding.spinnerInterlockDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setDomainPosition(position);
                String strDomain = getResources().getStringArray(R.array.spinner_query_domain_interlock)[position];
                DeviceRepository.getInstance().getInterLockByDomain("%" + strDomain + "%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}