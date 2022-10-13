package com.bluesky.autojiahua.ui.home;

import static com.bluesky.autojiahua.common.App.DETAIL_PAGE_SIMPLIFY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;
import com.bluesky.autojiahua.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding mBinding;
    private Device mDevice;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDevice = (Device) getArguments().getSerializable("device");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mBinding = FragmentDetailBinding.inflate(inflater);
        //或者
         mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        if (mDevice != null) {
            mBinding.setDevice(mDevice);
        }
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_detail, menu);

        MenuItem item = menu.findItem(R.id.menu_item_detail_simple);
        SwitchCompat switchCompat = (SwitchCompat) item.getActionView();
        switchCompat.setTextOff("全");
        switchCompat.setTextOn("简");
        switchCompat.setShowText(true);
        switchCompat.setChecked(DETAIL_PAGE_SIMPLIFY);
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            App.setSimply(b);
            showOrHideDetail(b ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //必须加这一行代码才能有菜单
        setHasOptionsMenu(true);
        //viewmodel没有使用，直接使用了bundle传过来的device
        DetailViewModel viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        showOrHideDetail(DETAIL_PAGE_SIMPLIFY ? View.GONE : View.VISIBLE);


    }

    private void showOrHideDetail(int visible) {
        mBinding.tvDetailContentStandard.setVisibility(visible);
        mBinding.tvDetailContentMode.setVisibility(visible);
        mBinding.tvDetailContentPipe.setVisibility(visible);
        mBinding.tvDetailContentType.setVisibility(visible);
        mBinding.tvDetailContentInstall.setVisibility(visible);
        mBinding.tvDetailContentFactory.setVisibility(visible);
        mBinding.tvDetailContentRemark.setVisibility(visible);
        mBinding.tvDetailContentBrand.setVisibility(visible);
        mBinding.tvDetailContentDate.setVisibility(visible);
    }


}