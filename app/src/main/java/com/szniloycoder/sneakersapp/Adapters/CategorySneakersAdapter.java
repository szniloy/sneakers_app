package com.szniloycoder.sneakersapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.szniloycoder.sneakersapp.DetailActivity;
import com.szniloycoder.sneakersapp.Models.SneakersModel;
import com.szniloycoder.sneakersapp.R;
import com.szniloycoder.sneakersapp.databinding.CatSneakersViewBinding;

import java.util.ArrayList;

public class CategorySneakersAdapter  extends RecyclerView.Adapter<CategorySneakersAdapter.viewHolder> {

    Context context;
    ArrayList<SneakersModel> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String averageRatings;

    // Constructor
    public CategorySneakersAdapter(Context context, ArrayList<SneakersModel> list) {
        this.context = context;
        this.list = list;
        this.database = FirebaseDatabase.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_sneakers_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        SneakersModel model = list.get(position);

        // Set food details to the view
        holder.binding.titleTxt.setText(model.getName());
        holder.binding.priceTxt.setText("$" + model.getPrice());

        // Load food image with Glide and apply transformations
        Glide.with(context)
                .load(model.getImageUrls().get(0))
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.img);

        // Set click listener to open DetailActivity with food details
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("foodName", model.getName());
            intent.putExtra("foodRatings", model.getRatings());
            intent.putExtra("foodSize", model.getSizes());
            intent.putExtra("foodDeliveryTme", model.getDeliveryTme());
            intent.putExtra("foodDescription", model.getDescription());
            intent.putExtra("foodPrice", model.getPrice());
            intent.putExtra("foodImage", model.getImageUrls());
            intent.putExtra("foodIngredient", model.getIngredients());
            intent.putExtra("ItemId", model.getItemId());
            intent.putExtra("catId", model.getCatId());
            intent.putExtra("foodCatName", model.getCategory());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        CatSneakersViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = CatSneakersViewBinding.bind(itemView);
        }
    }
}
