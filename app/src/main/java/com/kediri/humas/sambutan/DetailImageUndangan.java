package com.kediri.humas.sambutan;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailImageUndangan extends AppCompatActivity {

    String nama_acara,file_acara;
    ImageView imageView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    String url_file="http://192.168.43.194/sambutan/dokumen/undangan/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail_image_undangan);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setTitle("My title");
        }

        nama_acara = getIntent().getExtras().getString("und_acara");
        file_acara = getIntent().getExtras().getString("und_fileacara");

        imageView=(ImageView)findViewById(R.id.imageViewDetailUndangan);
        Picasso.with(DetailImageUndangan.this).load(url_file+""+file_acara)
                .fit()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(imageView);

        Toast.makeText(getApplicationContext(), file_acara, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.download_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            // action with ID action_settings was selected
            case R.id.actionbar_download:
                Toast.makeText(this, "Download selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;


//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
    }
}
