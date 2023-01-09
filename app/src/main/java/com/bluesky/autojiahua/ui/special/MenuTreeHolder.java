package com.bluesky.autojiahua.ui.special;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluesky.autojiahua.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * @author BlueSky
 * @date 23.1.8
 * Description:
 */
public class MenuTreeHolder extends TreeNode.BaseNodeViewHolder<IconTreeItem> {

    private ImageView arrowView;
    private TextView tvText;
    private ImageView nodeView;

    public MenuTreeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_menu_tree_node, null, false);
        arrowView = view.findViewById(R.id.iv_item_node_icon);
        nodeView = view.findViewById(R.id.iv_item_node_arrow);
        tvText = view.findViewById(R.id.tv_item_node_text);
        arrowView.setImageResource(R.drawable.ic_baseline_arrow_right_24);
        tvText.setText(value.text);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.INVISIBLE);
/*            nodeView.setVisibility(View.VISIBLE);
        } else {
            nodeView.setVisibility(View.GONE);*/
        }
        return view;
    }

    @Override
    public void toggle(boolean active) {
        super.toggle(active);
        arrowView.setImageResource(active ? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_right_24);
    }
}
