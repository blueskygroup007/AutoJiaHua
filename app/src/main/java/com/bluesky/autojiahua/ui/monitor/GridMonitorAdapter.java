package com.bluesky.autojiahua.ui.monitor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.autojiahua.R;

import java.util.List;

/**
 * @author BlueSky
 * @date 22.10.20
 * Description:
 */
public class GridMonitorAdapter extends RecyclerView.Adapter<GridMonitorAdapter.ViewHolder> {

    private List<BeanMonitor> mData;
    private OnThumbClickListener mListener;

    public GridMonitorAdapter(List<BeanMonitor> data) {
        mData = data;
        mListener = new OnThumbClickListener();
    }

    @NonNull
    @Override
    public GridMonitorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_monitor, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridMonitorAdapter.ViewHolder holder, int position) {
        //TODO 这里应该取缩略图,查看内存占用，并优化
        //Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mData.get(position).getPicture());
        //Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);
        //换成统一的默认图标
        holder.ivThumb.setImageResource(R.drawable.ic_workbench_muti);
        holder.tvName.setText(mData.get(position).getName());
        //itemview是holder的成员变量
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<BeanMonitor> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.iv_item_monitor_thumb);
            tvName = itemView.findViewById(R.id.tv_item_monitor_name);
        }
    }

    private class OnThumbClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //TODO 之前采用的方法是调用系统相册,并将要显示的图片插入到相册中,返回uri后,使用相册显示
            //缺点是会在系统相册中插入若干图片.且需要动态申请权限.
            //TODO 新方案是使用第三方库展示图片,优点是可以添加功能:输入工位号可以直接定位.

            int position = (int) v.getTag();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
    }
}
