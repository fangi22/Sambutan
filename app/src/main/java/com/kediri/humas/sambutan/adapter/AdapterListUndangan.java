package com.kediri.humas.sambutan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kediri.humas.sambutan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by vangi on 9/22/2017.
 */

public class AdapterListUndangan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ArrayList<HashMap<String, String>> allOriginalData;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public AdapterListUndangan(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        allOriginalData = arraylist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtNama, txtStatus, txtTipePhone;
        public ImageView imgView;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            txtNama = (TextView) view.findViewById(R.id.txtListJu);
            txtStatus = (TextView) view.findViewById(R.id.txtListKeterangan);
            txtTipePhone = (TextView) view.findViewById(R.id.txtListTanggal);
            imgView = (ImageView) view.findViewById(R.id.imageList);

        }

        @Override
        public void onClick(View v) {
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                v.setSelected(false);
            }
            else {
                selectedItems.put(getAdapterPosition(), true);
                v.setSelected(true);
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_sambutan, parent, false);
        MyViewHolder rcv=new MyViewHolder(itemView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        resultp = data.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.txtNama.setText(resultp.get("fileAcara"));
        viewHolder.txtStatus.setText(resultp.get("namaAcara"));
        viewHolder.txtTipePhone.setText(resultp.get("tanggal"));
        Picasso.with(context).load("http://192.168.43.194/sambutan/dokumen/undangan/"+resultp.get("fileAcara"))
                .fit()
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(viewHolder.imgView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setFilter(ArrayList<HashMap<String, String>> newList){
        //we have used hashmap,  find how to use it here
        data= new ArrayList<HashMap<String, String>>();
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();

        if (charText.length() == 0) {
            // search empty -> new data = all original data
            data =allOriginalData;
        } else {
            // loop all original data
            // check the condition and make the new data for display in ListView
            for(int i = 0; i < allOriginalData.size(); i++){
                HashMap<String, String> resultA = allOriginalData.get(i);
                if (resultA.get("fileAcara").toLowerCase().contains(charText)) {
                    data.add(resultA);
                }
            }
        }
        notifyDataSetChanged();
    }

}
