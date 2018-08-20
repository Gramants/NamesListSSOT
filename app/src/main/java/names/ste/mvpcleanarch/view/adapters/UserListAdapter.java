package names.ste.mvpcleanarch.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.util.ArrayList;
import java.util.List;

import names.ste.mvpcleanarch.R;
import names.ste.mvpcleanarch.model.entities.ItemDbEntity;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private final List<ItemDbEntity> items;
    private final OnItemClickListener listener;

    public void clearAll() {
        this.items.clear();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(ItemDbEntity item);

    }

    public UserListAdapter(OnItemClickListener listener) {
        this.items = new ArrayList();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(items.get(position), listener, holder.itemView.getContext());


    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void updateRepoList(List<ItemDbEntity> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private RelativeLayout mContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainer = (RelativeLayout) itemView.findViewById(R.id.container);
            mTitle = (TextView) itemView.findViewById(R.id.tv_name);

        }

        public void bindItem(ItemDbEntity item, OnItemClickListener listener, Context ctx) {
            mTitle.setText(item.getName());

                mContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });


        }
    }


}