package com.kediri.humas.sambutan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kediri.humas.sambutan.adapter.AdapterListAcara;
import com.kediri.humas.sambutan.volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Acara extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    SwipeRefreshLayout swipe;
//    List<String> undanganList = new ArrayList<>();
    ArrayList<HashMap<String, String>> undanganList = new ArrayList<HashMap<String, String>>();
    AdapterListAcara adapter;
    EditText search;
    SessionManagement session;
    String url_read;
    private static final String TAG = Acara.class.getSimpleName();

    public Acara() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_acara, container, false);
        session=new SessionManagement(getActivity().getApplicationContext());
        url_read="http://192.168.43.194/sambutan/api/undangan/undangan/"+session.lihat_group()+"/"+session.lihat_opd();
        swipe = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout_undangan);
        listView = (ListView)rootView.findViewById(R.id.listviewUndangan);
        search= (EditText) rootView.findViewById(R.id.inputSearch);

        undanganList.clear();

        adapter=new AdapterListAcara(getActivity(),undanganList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> und=(HashMap<String, String>)undanganList.get(position);
                //Toast.makeText(getContext(), und.get("judul").toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),DetailAcara.class);
                    intent.putExtra("und_id", und.get("idAcara"));
                    intent.putExtra("und_opd", und.get("namaOpd"));
                    intent.putExtra("und_acara", und.get("namaAcara"));
                    intent.putExtra("und_tanggal", und.get("tanggal"));
                    intent.putExtra("und_tempat", und.get("tempat"));
                    intent.putExtra("und_keterangan", und.get("keterangan"));
                    intent.putExtra("und_fileacara", und.get("fileAcara"));
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().toLowerCase();
                ArrayList<HashMap<String, String>> newList= new ArrayList<>();
                for(  HashMap<String, String> entry : undanganList){
                    //String name= entry.get("fileAcara",newText);
                    if(entry.get("keterangan").toLowerCase().contains(newText) || entry.get("namaAcara").toLowerCase().contains(newText)){
                        newList.add(entry);
                    }}
                adapter.setFilter(newList);
//                Toast.makeText(getBaseContext(), s.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
//                           undanganList.clear();
                           adapter.notifyDataSetChanged();
                           callUndangan();
                       }
                   }
        );

        return rootView;
    }

    @Override
    public void onRefresh() {
        undanganList.clear();
        adapter.notifyDataSetChanged();
        callUndangan();
    }

    private void callUndangan(){
        swipe.setRefreshing(true);

        // membuat req array dengan JSON
        JsonArrayRequest jArr=new JsonArrayRequest(url_read, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        HashMap<String,String> map = new HashMap<String,String>();

                        map.put("idAcara", obj.getString("ID_ACARA"));
                        map.put("namaOpd", obj.getString("NAMA_OPD"));
                        map.put("namaAcara", obj.getString("NAMA_ACARA"));
                        map.put("tanggal", obj.getString("TGL_ACARA"));
                        map.put("tempat", obj.getString("NAMA_LOKASI"));
                        map.put("keterangan", obj.getString("KET_ACARA"));
                        map.put("fileAcara", obj.getString("FILE_UNDANGAN"));
                        if (obj.getString("FOTO_OPD") != "") {
                            map.put("foto", "http://192.168.43.194/sambutan/gambar/opd/"+obj.getString("FOTO_OPD"));
                        }

                        // menambah item ke array
                        undanganList.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifikasi perubahan data adapter
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
    //            Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });
        // menambah request ke antrian request
        AppController.getInstance().addToRequestQueue(jArr);

    }





}
