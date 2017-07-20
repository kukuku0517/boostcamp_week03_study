package com.example.android.boostcampweek03miniproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.boostcampweek03miniproject.Data.CardItem;
import com.example.android.boostcampweek03miniproject.Data.CustomAdapter;
import com.example.android.boostcampweek03miniproject.Data.CustomComparator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static android.R.attr.duration;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_tab)
    TabLayout tabLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progressBar2)
    ProgressBar pb;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapter adapter;
    private int layoutType = 0;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<CardItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);

        initView();
        initRecyclerView(items);
        firebaseDataUpdate();
    }

    //navigationView생성
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("맛집 리스트");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    //recyclerview생성(data X)
    private void initRecyclerView(ArrayList<CardItem> items) {
        Collections.sort(items, new CustomComparator(0));
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new CustomAdapter(this);
        adapter.updateItem(items);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        //tablayout. 클릭시 tab에 따라 adapter를 통해 item들을 다시 sorting해준다
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                adapter.sort(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //firebase database에서 data불러오기. 불러오기가 완료되면 adapter를 통해 item을 갱신해준다.
    void firebaseDataUpdate() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cv_items");
        pb.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pb.setVisibility(View.GONE);
                items.clear();
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    CardItem c = s.getValue(CardItem.class);
                    items.add(c);
                }
                adapter.updateItem(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //layout변경 버튼. 누르면 layoutManager를 통해서 layout을 변경해준다
    @OnClick(R.id.layout_button)
    public void click(ImageView v) {
//        Toasty.custom(this, "I'm a custom Toast", R.drawable.checked, Color.BLACK, duration, true,
//                true).show();
        switch (layoutType) {
            case 0:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                v.setImageResource(R.drawable.grid);
                layoutType = 1;
                break;
            case 1:
                layoutManager = new GridLayoutManager(this, 3);
                v.setImageResource(R.drawable.linear);
                layoutType = 2;
                break;
            case 2:
                layoutManager = new LinearLayoutManager(this);
                v.setImageResource(R.drawable.stagger);
                layoutType = 0;
                break;
        }
        rv.setLayoutManager(layoutManager);

    }

    //메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //메뉴 클릭
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //네비게이션 클릭
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        }
        return false;
    }
}
