package com.eminence.drive13.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.eminence.drive13.Newsdetail;
import com.eminence.drive13.R;
import com.eminence.drive13.model.LeadsModel;
import com.eminence.drive13.model.NewsModel;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.MyViewHolder> {
    Context context;
    ArrayList<LeadsModel> subcat;

    public LeadsAdapter(ArrayList<LeadsModel> subcat, Context context) {
        this.subcat = subcat;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leadslayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tripnumber.setText("Trip Number #"+subcat.get(position).getLead_id() );
        holder.fromtodes.setText(subcat.get(position).getTrip_from()+" to "+subcat.get(position).getTrip_destination());
        holder.datetime.setText(subcat.get(position).getTrip_time()+" on "+subcat.get(position).getTrip_date());
        holder.date.setText(subcat.get(position).getCreated_at());


        holder.layout.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent=new Intent(context, Leaddetails.class);
                                                 intent.putExtra("tripnumber",subcat.get(position).getLead_id());
                                                 intent.putExtra("tripfrom",subcat.get(position).getTrip_from());
                                                 intent.putExtra("tripdetintion",subcat.get(position).getTrip_destination());
                                                 intent.putExtra("triptime",subcat.get(position).getTrip_time());
                                                 intent.putExtra("tripdate",subcat.get(position).getTrip_date());
                                                 intent.putExtra("createdat",subcat.get(position).getCreated_at());
                                                 intent.putExtra("clientname",subcat.get(position).getClientname());
                                                 intent.putExtra("clientnumber",subcat.get(position).getClientnumber());
                                                 intent.putExtra("purpose",subcat.get(position).getPurpose());


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
        public TextView tripnumber, fromtodes,datetime,date,time;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            tripnumber = view.findViewById(R.id.tripnumber);
            fromtodes = view.findViewById(R.id.fromtodes);
            datetime = view.findViewById(R.id.datetime);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);
            layout = view.findViewById(R.id.layout);


        }
    }


}


