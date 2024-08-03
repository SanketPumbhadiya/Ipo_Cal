package com.example.ipocalculation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipocalculation.IpoDetailsSetData;
import com.example.ipocalculation.R;

import java.util.ArrayList;

public class RecyclerItemAdapter extends RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder> {
    ArrayList<IpoDetailsSetData> originalList;
    Context context;
    LayoutInflater inflater;
    private OnClickListener onClickListener;

    public RecyclerItemAdapter(ArrayList<IpoDetailsSetData> list, Context context, LayoutInflater inflater) {
        this.originalList = list;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView;
        convertView = inflater.inflate(R.layout.customlistview, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.tvCompanyName = convertView.findViewById(R.id.tv_companyName);
        viewHolder.tvOpenDate = convertView.findViewById(R.id.tv_openDate);
        viewHolder.tvCloseDate = convertView.findViewById(R.id.tv_closeDate);
        viewHolder.tvIssuePrice = convertView.findViewById(R.id.tv_issuePrice);
        viewHolder.tvLotSize = convertView.findViewById(R.id.tv_lotSize);
        viewHolder.tvListingDate = convertView.findViewById(R.id.tv_listingDate);
        viewHolder.tvExchange = convertView.findViewById(R.id.tv_exchange);
        convertView.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        IpoDetailsSetData model = originalList.get(position);
        Log.e("Sanket", "ListSize" + originalList.size());
        Log.e("Sanket", "ListSize" + originalList);

        Log.e("Sanket", "Binding data for position " + position + ": " + model.getCompanyName());

        holder.tvCompanyName.setText(model.getCompanyName());
        holder.tvOpenDate.setText(model.getOpenDate());
        holder.tvCloseDate.setText(model.getCloseDate());
        holder.tvIssuePrice.setText(" " + model.getIssuePrice());
        holder.tvLotSize.setText(" " + model.getLotSize());
        holder.tvListingDate.setText(model.getListingDate());
        holder.tvExchange.setText(model.getExchange());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return originalList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, IpoDetailsSetData model);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName, tvOpenDate, tvCloseDate, tvListingDate, tvIssuePrice, tvLotSize, tvExchange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}