package com.kediri.humas.sambutan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SharedPreferences login;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setTitle(getString(R.string.home_title));
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        if(!session.cek_session()){
            Intent telp=new Intent(getBaseContext(), LoginAplikasi.class);
            startActivity(telp);
            finish();
            //Toast.makeText(getBaseContext(),"Tidak ada session",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(),"Selamat datang "+session.lihat_nama(),Toast.LENGTH_LONG).show();
        }

        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG","TAB1");
                        break;
                    case 1:
                        Log.e("TAG","TAB2");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            Log.d("MyApp", "No SDCARD");
//        } else {
//            File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"MyAppFolder");
//            directory.mkdirs();
//        }

    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterr adapter = new ViewPagerAdapterr(getSupportFragmentManager());
        adapter.addFrag(new Acara(), "Acara");
        adapter.addFrag(new Sambutan(), "Sambutan");
        viewPager.setAdapter(adapter);
    }
    //View Pager fragments setting adapter class
    class ViewPagerAdapterr extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arraylist
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arraylist

        public ViewPagerAdapterr(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.logout:
                logout();
                break;
            default:
                break;
        }

        return true;
    }

    private void logout(){
        session.hapus_session();
        Intent login=new Intent(getBaseContext(), LoginAplikasi.class);
        startActivity(login);
        finish();
    }
    /*
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
     */
}
