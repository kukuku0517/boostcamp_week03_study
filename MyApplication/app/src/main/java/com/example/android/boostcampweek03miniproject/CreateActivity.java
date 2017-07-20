package com.example.android.boostcampweek03miniproject;

import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity
        implements CreateFragment.OnFragmentInteractionListener, MapsFragment.OnFragmentInteractionListener,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String fullAddress;
    private double lat;
    private double lng;

    //backbutton
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        initView();
        initGooleApiClient();
    }

    //lat,lng 초기화
    //toolbar setting
    //createFragment 생성
    void initView() {
        lat = 37;
        lng = 126;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("맛집 등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateFragment fragment = new CreateFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    //google API 설정
    void initGooleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    //현재 position(latlng)으로 부터 주소 받아오기
    private void getAddress(LatLng latlng) {

        lat = latlng.latitude;
        lng = latlng.longitude;
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<android.location.Address> addresses = null;

        try {
            addresses = gcd.getFromLocation(latlng.latitude, latlng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0);
            fullAddress = address;
        } else {

        }

    }

    //주소 검색후 좌표값 저장(createFragment>autocomplete intent)
    public void setLatlng(LatLng latlng) {
        lat = latlng.latitude;
        lng = latlng.longitude;
    }

    //create fragment 지도보기 클릭시
    @Override
    public void onCreateFragmentInteraction() {
        Fragment fragment = MapsFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    //map fragment 완료 클릭시
    @Override
    public void onMapsFragmentInteraction() {
        Fragment fragment = CreateFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        Bundle arguments = new Bundle();
        arguments.putString("address", fullAddress);
        fragment.setArguments(arguments);

        fragmentTransaction.commit();
    }

    //지도 로딩후. 화면 이동시 주소 업데이트
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f));
        final MapsFragment map = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng here = marker.getPosition();
                getAddress(here);
                map.setLocation(fullAddress);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
            }
        });

//맵전체 이동할시 위치 가져오는 부분(제외)
//        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng here = mMap.getCameraPosition().target;
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(here));
//                getAddress();
//                map.setLocation(fullAddress);
//            }
//        });

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
