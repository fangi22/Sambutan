package com.kediri.humas.sambutan;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kediri.humas.sambutan.adapter.AdapterListUndangan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SambutanUndangan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListUndangan mAdapter;
    SwipeRefreshLayout swipe;
    ArrayList<HashMap<String, String>> undanganList = new ArrayList<HashMap<String, String>>();
    private static final String TAG = SambutanUndangan.class.getSimpleName();
    ProgressBar progressBar;
    EditText search;
    SessionManagement session;
    String url_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sambutan_undangan);
        session=new SessionManagement(getApplicationContext());
        url_read="http://192.168.43.194/sambutan/api/sambutan/undangan/"+session.lihat_group()+"/"+session.lihat_opd();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search= (EditText) findViewById(R.id.inputSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.listSambutanUndangan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SambutanUndangan.this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter=new AdapterListUndangan(this,undanganList);
        recyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.VISIBLE);

        callUndangan();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            int GetItemPosition ;
            View gg;
            GestureDetector gestureDetector = new GestureDetector(SambutanUndangan.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                gg = rv.findChildViewUnder(e.getX(), e.getY());
                if(gg != null && gestureDetector.onTouchEvent(e)) {

                    GetItemPosition = rv.getChildAdapterPosition(gg);
                    HashMap<String, String> und=(HashMap<String, String>)undanganList.get(GetItemPosition);
                    //Toast.makeText(SambutanUndangan.this, und.get("fileAcara").toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),DetailImageUndangan.class);
                        intent.putExtra("und_id", und.get("idAcara"));
                        intent.putExtra("und_acara", und.get("namaAcara"));
                        intent.putExtra("und_fileacara", und.get("fileAcara"));
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }


    private void callUndangan(){
        //swipe.setRefreshing(true);

        // membuat req array dengan JSON
        JsonArrayRequest jArr=new JsonArrayRequest(url_read, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        HashMap<String,String> map = new HashMap<String,String>();

                        map.put("idAcara", obj.getString("ID_ACARA"));
                        map.put("fileAcara", obj.getString("FILE_UNDANGAN"));
                        map.put("namaAcara", obj.getString("NAMA_ACARA"));
                        map.put("tanggal", obj.getString("TGL_ACARA"));
                        map.put("opd", obj.getString("NAMA_OPD"));
                        // menambah item ke array
                        undanganList.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifikasi perubahan data adapter
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // menambah request ke antrian request
        //AppController.getInstance().addToRequestQueue(jArr);
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(jArr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.actionbar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

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

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String newText = s.toString().toLowerCase();
                ArrayList<HashMap<String, String>> newList= new ArrayList<>();
                for(  HashMap<String, String> entry : undanganList){
                    //String name= entry.get("fileAcara",newText);
                    if(entry.get("fileAcara").toLowerCase().contains(newText) || entry.get("namaAcara").toLowerCase().contains(newText)){
                        newList.add(entry);
                    }}
                mAdapter.setFilter(newList);
                return true;
            }
        });
    }


}
