package dscountr.app.co.ke.dscountr_android_app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dscountr.app.co.ke.dscountr_android_app.R;
import dscountr.app.co.ke.dscountr_android_app.model.FeaturedRetailers;

public class FeaturedRetailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<FeaturedRetailers> featuredRetailers;
    private OnItemClickListener mItemClickListener;
    private static final int EMPTY_VIEW = 100;
    private static final int DATA_VIEW = 200;

    public FeaturedRetailersAdapter(Context context, ArrayList<FeaturedRetailers> featuredRetailers) {
        this.context = context;
        this.featuredRetailers = featuredRetailers;
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView tItemName, tSub;

        EmptyViewHolder(View itemView) {
            super(itemView);
            tItemName = itemView.findViewById(R.id.itemName);
            tSub = itemView.findViewById(R.id.subItemName);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvFeaturedRetailer;
        CardView cvFeaturedRetailer;

        MyViewHolder(View view) {
            super(view);

            cvFeaturedRetailer = view.findViewById(R.id.cvFeaturedRetailer);
            tvFeaturedRetailer = view.findViewById(R.id.tvFeaturedRetailer);

            cvFeaturedRetailer.setOnClickListener(this);
            tvFeaturedRetailer.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition(), featuredRetailers.get(getAdapterPosition()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return featuredRetailers.size() == 0 ? EMPTY_VIEW : DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATA_VIEW:
                return new MyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.item_featured_retailers, parent, false));
            case EMPTY_VIEW:
                return new EmptyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.empty_item, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder emptyHolder = (EmptyViewHolder) holder;
            emptyHolder.tItemName.setText("Featured Retailers...");
            emptyHolder.tSub.setText("No featured retailers at the moment...");
        }

        if (holder instanceof MyViewHolder) {
            FeaturedRetailers featured_Retailers = featuredRetailers.get(position);
//            ((MyViewHolder) holder).tvFeaturedRetailer.setText(featured_Retailers.getFeatured_Retailer_Name());
        }
    }
    @Override
    public int getItemCount() {
        return featuredRetailers.size() == 0 ? 1 : featuredRetailers.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, FeaturedRetailers featuredRetailers);
    }

    void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    void clearData() {
        featuredRetailers.clear();
        notifyDataSetChanged();
    }

    void updateFeaturedRetailers(ArrayList<FeaturedRetailers> featuredRetailers) {
        this.featuredRetailers = featuredRetailers;
        notifyDataSetChanged();
    }
}