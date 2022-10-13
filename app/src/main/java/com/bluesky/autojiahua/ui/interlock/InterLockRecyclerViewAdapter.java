package com.bluesky.autojiahua.ui.interlock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.autojiahua.R;
import com.bluesky.autojiahua.bean.InterLock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 22.9.23
 * Description:
 */
public class InterLockRecyclerViewAdapter extends RecyclerView.Adapter<InterLockRecyclerViewAdapter.InterLockViewHolder> {

    private final RecyclerView mRecyclerView;
    private List<InterLock> mData = new ArrayList<>();

    public InterLockRecyclerViewAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public InterLockRecyclerViewAdapter.InterLockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new InterLockViewHolder(inflater.inflate(R.layout.rv_item_interlock, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InterLockRecyclerViewAdapter.InterLockViewHolder holder, int position) {
        InterLock interLock = mData.get(position);
        holder.tvNumber.setText(String.valueOf(interLock.getNumber()));
        holder.tvTag.setText(interLock.getTag());
        holder.tvName.setText(interLock.getDevice_name());
        holder.tvDomain.setText(interLock.getDomain());
        holder.root.setTag(position);
        holder.root.setOnClickListener(v -> {
            InterLock interlock = mData.get((Integer) v.getTag());
            NavController controller = Navigation.findNavController(mRecyclerView);
            Bundle bundle = new Bundle();
            bundle.putSerializable("interlock", interLock);
            controller.navigate(R.id.action_nav_interlock_to_interlockDetailFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<InterLock> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class InterLockViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNumber;
        final TextView tvTag;
        final TextView tvDomain;
        final TextView tvName;
        final CardView root;

        public InterLockViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.card_interlock_item);
            tvNumber = itemView.findViewById(R.id.tv_interlock_number);
            tvDomain = itemView.findViewById(R.id.tv_interlock_domain);
            tvName = itemView.findViewById(R.id.tv_interlock_device_name);
            tvTag = itemView.findViewById(R.id.tv_interlock_tag);
        }
    }
}
