package com.kediri.humas.sambutan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailAcara extends AppCompatActivity {

    String nama_opd,nama_acara,tanggal,tempat,keterangan,file_acara;
    TextView txtOpd,txtNamaAcara,txtTanggal,txtTempat,txtKeterangan;
    ImageView imageView;
    String url_file="http://192.168.43.194/sambutan/dokumen/undangan/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acara);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_opd = getIntent().getExtras().getString("und_opd");
        nama_acara = getIntent().getExtras().getString("und_acara");
        tanggal = getIntent().getExtras().getString("und_tanggal");
        tempat = getIntent().getExtras().getString("und_tempat");
        keterangan = getIntent().getExtras().getString("und_keterangan");
        file_acara = getIntent().getExtras().getString("und_fileacara");

        txtOpd=(TextView)findViewById(R.id.txtNamaOpd);
        txtNamaAcara=(TextView)findViewById(R.id.txtNamaAcara);
        txtTanggal=(TextView)findViewById(R.id.txtTanggal);
        txtTempat=(TextView)findViewById(R.id.txtTempat);
        txtKeterangan=(TextView)findViewById(R.id.txtKeterangan);

        imageView=(ImageView)findViewById(R.id.imageViewDetailUndangan);

        txtOpd.setText(nama_opd);
        txtNamaAcara.setText(nama_acara);
        txtTanggal.setText(tanggal);
        txtTempat.setText(tempat);
        txtKeterangan.setText(keterangan);

        Picasso.with(DetailAcara.this).load(url_file+""+file_acara)
                .fit()
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(imageView);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
