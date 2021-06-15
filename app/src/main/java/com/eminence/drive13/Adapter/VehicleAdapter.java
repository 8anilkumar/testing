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
import com.eminence.drive13.R;
import com.eminence.drive13.Transactiondetails;
import com.eminence.drive13.model.TransactionModel;
import com.eminence.drive13.model.VehicleModel;
import com.eminence.drive13.profile.Regitervehicle;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {
    Context context;
    ArrayList<VehicleModel> subcat;

    public VehicleAdapter(ArrayList<VehicleModel> subcat, Context context) {
        this.subcat = subcat;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehiclelayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(subcat.get(position).getBrand() );
        holder.number.setText(subcat.get(position).getV_number());



        holder.layout.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent=new Intent(context, Regitervehicle.class);
                                                 intent.putExtra("operatiton","update");
                                                 intent.putExtra("brand",subcat.get(position).getBrand());
                                                 intent.putExtra("model",subcat.get(position).getModel());
                                                 intent.putExtra("manufacturt",subcat.get(position).getManufracture());
                                                 intent.putExtra("color",subcat.get(position).getColor());
                                                 intent.putExtra("id",subcat.get(position).getId());
                                                 intent.putExtra("insuranceimage",subcat.get(position).getInsurancepp());
                                                 intent.putExtra("v_number",subcat.get(position).getV_number());
                                                 intent.putExtra("rcupload",subcat.get(position).getRcupload());
                                                  context.startActivity(intent);

                                             }
                                         }
        );

    }


    @Override
    public int getItemCount() {
        return subcat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number,insertdate;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            number = view.findViewById(R.id.number);


            layout = view.findViewById(R.id.layout);


        }
    }


}


