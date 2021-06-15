package com.eminence.drive13.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eminence.drive13.Leaddetails;
import com.eminence.drive13.R;
import com.eminence.drive13.Transactiondetails;
import com.eminence.drive13.model.LeadsModel;
import com.eminence.drive13.model.TransactionModel;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransactionModel> subcat;

    public TransactionAdapter(ArrayList<TransactionModel> subcat, Context context) {
        this.subcat = subcat;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transactionlayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(subcat.get(position).getName());
        holder.location.setText(subcat.get(position).getLocation());
        holder.insertdate.setText(subcat.get(position).getInsertdate());

        if (subcat.get(position).getBill_photo().equals("")) {
            Glide.with(context)
                    .load(imagebaseurl + subcat.get(position).getSelfi_photo())
                    .error(R.drawable.logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
        } else {
            Glide.with(context)
                    .load(imagebaseurl + subcat.get(position).getBill_photo())
                    .error(R.drawable.logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 if (subcat.get(position).getBill_photo().equals("")) {
                                                     Intent intent = new Intent(context, Transactiondetails.class);
                                                     intent.putExtra("name", subcat.get(position).getName());
                                                     intent.putExtra("location", subcat.get(position).getLocation());
                                                     intent.putExtra("rname", subcat.get(position).getRname());
                                                     intent.putExtra("rmobile", subcat.get(position).getRmobile());
                                                     intent.putExtra("billamount", subcat.get(position).getBillaount());
                                                     intent.putExtra("billphoto", "");
                                                     intent.putExtra("selfiphoto", subcat.get(position).getSelfi_photo());
                                                     context.startActivity(intent);
                                                 } else {
                                                     Intent intent = new Intent(context, Transactiondetails.class);
                                                     intent.putExtra("name", subcat.get(position).getName());
                                                     intent.putExtra("location", subcat.get(position).getLocation());
                                                     intent.putExtra("rname", subcat.get(position).getRname());
                                                     intent.putExtra("rmobile", subcat.get(position).getRmobile());
                                                     intent.putExtra("billamount", subcat.get(position).getBillaount());
                                                     intent.putExtra("billphoto", subcat.get(position).getBill_photo());
                                                     intent.putExtra("selfiphoto", "");

                                                     context.startActivity(intent);
                                                 }


                                             }
                                         }
        );

    }


    @Override
    public int getItemCount() {
        return subcat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location, insertdate;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            location = view.findViewById(R.id.location);
            insertdate = view.findViewById(R.id.insertdate);
            layout = view.findViewById(R.id.layout);
        }
    }
}


