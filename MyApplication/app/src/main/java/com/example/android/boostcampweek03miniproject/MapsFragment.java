package com.example.android.boostcampweek03miniproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_onmap)
    TextView tvOnMap;

    @OnClick(R.id.location_select)
    void click() {
        try {
            mListener.onMapsFragmentInteraction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MapsFragment newInstance() {
        Bundle args = new Bundle();

        MapsFragment fragment = new MapsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //camera이동시 createActivity에서 호출하는 메소드
    public void setLocation(String s) {
        tvOnMap.setText(s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) mListener);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapsFragmentInteraction() throws IOException;
    }
}
