package com.hamzaamin.i180550_i170298;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.MyViewHolder> implements Filterable {

    List<Profile> list;
    List<Profile> filteredList;
    Context c;
    public ContactRVAdapter(List<Profile> list, Context c) {
        this.c=c;
        this.list=list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public ContactRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.contact_item,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRVAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.name.setText(filteredList.get(position).getName());
        holder.contactNo.setText(filteredList.get(position).getPhoneNo());
        Picasso.get().load(Id.getIp()+filteredList.get(position).getDp()).fit().centerCrop().into(holder.profile);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(c, ChatActivity.class);
                intent.putExtra("profileName",filteredList.get(position).getName());
                intent.putExtra("id",filteredList.get(position).getId());
                intent.putExtra("image",filteredList.get(position).getDp());

                c.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    filteredList = list;
                }
                else{
                    List<Profile> listFiltered = new ArrayList<>();
                    for (Profile row: list){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            listFiltered.add(row);

                        }
                    }
                    filteredList = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList =  (List<Profile>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, contactNo;
        CircleImageView profile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            contactNo= itemView.findViewById(R.id.ContactNo);
            profile=itemView.findViewById(R.id.profile);
        }
    }
}

