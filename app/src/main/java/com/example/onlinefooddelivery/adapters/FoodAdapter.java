package com.example.onlinefooddelivery.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefooddelivery.CartActivity;
import com.example.onlinefooddelivery.MainActivity;
import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.database.DbHelper;
import com.example.onlinefooddelivery.models.FoodItem;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList;
    private Context context;
    private DbHelper dbHelper;

    public FoodAdapter(Context context, List<FoodItem> foodList, DbHelper dbHelper) {
        this.context = context;
        this.foodList = foodList;
        this.dbHelper = dbHelper;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.foodName.setText(item.getName());
        holder.foodPrice.setText("ETB " + item.getPrice());
        int imageResId = context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName());
        holder.foodImage.setImageResource(imageResId);

        holder.btnAddToCart.setOnClickListener(v -> {
            boolean added = dbHelper.addToCart(item.getId(), 1);
            if (added) {
                Toast.makeText(context, item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice;
        Button btnAddToCart;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}