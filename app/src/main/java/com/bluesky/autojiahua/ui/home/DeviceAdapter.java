package com.bluesky.autojiahua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.databinding.RvItemDeviceBinding;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<Device> mData;
    private ViewGroup rv_list;

    public DeviceAdapter(List<Device> data) {
        mData = data;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rv_list = parent;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemDeviceBinding binding = RvItemDeviceBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position), position, createItemClickListener(mData.get(position)));
    }

    private View.OnClickListener createItemClickListener(Device device) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(rv_list);
                Bundle bundle = new Bundle();
                bundle.putSerializable("device", device);
                controller.navigate(R.id.action_nav_home_to_nav_detail, bundle);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Device> devices) {
        mData = devices;
        notifyDataSetChanged();
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
            mBinding.getRoot().setOnClickListener(listener);
        }
    }
}
