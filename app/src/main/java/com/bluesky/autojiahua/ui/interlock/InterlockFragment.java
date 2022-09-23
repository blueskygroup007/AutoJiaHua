package com.bluesky.autojiahua.ui.interlock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.databinding.FragmentInterlockBinding;

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
        mViewModel = new ViewModelProvider(this).get(InterlockViewModel.class);
        // TODO: Use the ViewModel

    }

}