package com.kediri.humas.sambutan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kediri.humas.sambutan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vangi on 9/15/2017.
 */

public class AdapterListAcara extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    private class ViewHolder {
        TextView judul;
        TextView keterangan;
        TextView tanggal;
        ImageView imageView;
    }

    public AdapterListAcara(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageList);
        viewHolder.judul = (TextView) convertView.findViewById(R.id.txtListJudul);
        viewHolder.keterangan = (TextView) convertView.findViewById(R.id.txtListKeterangan);
        viewHolder.tanggal = (TextView) convertView.findViewById(R.id.txtListTanggal);

        resultp = data.get(position);
        Picasso.with(context).load(resultp.get("foto")).fit().into(viewHolder.imageView);
        viewHolder.judul.setText(resultp.get("namaOpd"));
        viewHolder.keterangan.setText(resultp.get("namaAcara"));
        viewHolder.tanggal.setText(resultp.get("tanggal"));

        return convertView;
    }

    public void setFilter(ArrayList<HashMap<String, String>> newList){
        //we have used hashmap,  find how to use it here
        data= new ArrayList<HashMap<String, String>>();
        data.addAll(newList);
        notifyDataSetChanged();
    }


}
