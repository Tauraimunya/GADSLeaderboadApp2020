package com.tauraimunya.gadsleaderboard.UTILS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tauraimunya.gadsleaderboard.R;
import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.ArrayList;
import java.util.List;

public class LearnerHoursRecyclerAdapter extends RecyclerView.Adapter<LearnerHoursRecyclerAdapter.ViewHolder> {

    List<Learner> mItems = new ArrayList<>();
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public LearnerHoursRecyclerAdapter(Context context) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item_hours, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Learner learner = mItems.get(position);

        String description = learner.getHours() + " " + mContext.getString(R.string.learning_hours) + ", " + learner.getCountry();

        holder.mTextName.setText(learner.getName());
        holder.mTextDescription.setText(description);
//        holder.mImageBag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                holder.mImageBag.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//            }
//        });

        ImageLoader.getInstance().displayImage(learner.getBadgeUrl(), holder.mImageBag);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Learner> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextName;
        private final TextView mTextDescription;
        private final ImageView mImageBag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.name_text);
            mTextDescription = itemView.findViewById(R.id.description_text);
            mImageBag = itemView.findViewById(R.id.badgeImage);

        }
    }
}

