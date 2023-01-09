package com.bluesky.autojiahua.ui.special;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.databinding.FragmentSpecialMenuBinding;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

public class SpecialMenuFragment extends Fragment {

    private SpecialMenuViewModel mViewModel;
    private BeanSpecial mBeanSpecial;
    private FragmentSpecialMenuBinding mBinding;

    public static SpecialMenuFragment newInstance() {
        return new SpecialMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSpecialMenuBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            mBeanSpecial = (BeanSpecial) getArguments().getSerializable("BeanSpecial");
        }

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialMenuViewModel.class);
        // TODO: Use the ViewModel
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        if (mBeanSpecial != null) {
            AndroidTreeView treeView = new AndroidTreeView(requireContext(), mViewModel.getMenuTree(mBeanSpecial.getId()));
            treeView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
                @Override
                public void onClick(TreeNode node, Object value) {
                    mBinding.tvSpecialMenuDesc.setText(((IconTreeItem) value).desc);
                }
            });
            treeView.setDefaultAnimation(true);
            treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, false);
            mBinding.llContainerMenu.addView(treeView.getView());
        }

    }

    private void initData() {

    }

    private void initEvent() {

    }

}