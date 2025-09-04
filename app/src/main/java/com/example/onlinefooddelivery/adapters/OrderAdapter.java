package com.example.onlinefooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtItems.setText(order.getItems());
        holder.txtTotal.setText("Total: ETB " + order.getTotal());
        holder.txtDate.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtItems, txtTotal, txtDate;

        public OrderViewHolder(View itemView) {
            super(itemView);
            txtItems = itemView.findViewById(R.id.txtItems);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
