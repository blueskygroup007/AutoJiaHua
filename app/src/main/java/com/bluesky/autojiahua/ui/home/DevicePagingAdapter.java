package com.bluesky.autojiahua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.databinding.RvItemDeviceBinding;

/**
 * @author BlueSky
 * @date 22.8.14
 * Description:
 */
public class DevicePagingAdapter extends PagingDataAdapter<Device, DevicePagingAdapter.ViewHolder> {
    private ViewGroup rv_list;

    public DevicePagingAdapter(@NonNull DiffUtil.ItemCallback<Device> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public DevicePagingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rv_list = parent;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemDeviceBinding binding = RvItemDeviceBinding.inflate(inflater, parent, false);
        return new DevicePagingAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (getItem(position) != null) {
        holder.bind(getItem(position), position, createItemClickListener(getItem(position)));
//        }
    }


    private View.OnClickListener createItemClickListener(Device device) {
        return view -> {
            NavController controller = Navigation.findNavController(rv_list);
            Bundle bundle = new Bundle();
            bundle.putSerializable("device", device);
            controller.navigate(R.id.action_nav_home_to_nav_detail, bundle);
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RvItemDeviceBinding mBinding;

        public ViewHolder(@NonNull RvItemDeviceBinding binding) {
            super(binding.getRoot());
            //todo 获取控件
            mBinding = binding;
        }


        void bind(Device device, int position, View.OnClickListener listener) {
            //填充控件内容
            mBinding.tvNumber.setText(String.valueOf(position + 1));
            mBinding.tvTag.setText(device.getTag());
            mBinding.tvAffect.setText(device.getAffect());
            if (device.getDate() != null && device.getDate().equals("alarm")) {
                mBinding.ivLock.setImageResource(R.drawable.ic_baseline_lock_enable_24);
            } else {
                mBinding.ivLock.setImageResource(R.drawable.ic_baseline_lock_disable_24);

            }
            //把监听器给了root。所以把cardview的clickable去掉。防止拦截
            mBinding.getRoot().setOnClickListener(listener);
        }
    }
}
