package com.bluesky.autojiahua.ui.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.autojiahua.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 23.1.8
 * Description:
 */
class GridSpecialAdapter extends RecyclerView.Adapter<GridSpecialAdapter.ViewHolder> {

    private RecyclerView mRecyclerView;
    private List<BeanSpecial> mData;

    public GridSpecialAdapter(RecyclerView recyclerView, List<BeanSpecial> data) {
        mRecyclerView = recyclerView;
        mData = data;
    }

    @NonNull
    @Override
    public GridSpecialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_grid, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridSpecialAdapter.ViewHolder holder, int position) {
        BeanSpecial bean = mData.get(position);
        holder.ivPicture.setImageResource(bean.getPicture());
        holder.tvName.setText(bean.getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(mRecyclerView);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BeanSpecial", bean);
                controller.navigate(R.id.action_nav_special_to_specialMenuFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<BeanSpecial> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicture;
        TextView tvName;
        View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            ivPicture = itemView.findViewById(R.id.iv_item_special_picture);
            tvName = itemView.findViewById(R.id.tv_item_special_name);
        }
    }
}
