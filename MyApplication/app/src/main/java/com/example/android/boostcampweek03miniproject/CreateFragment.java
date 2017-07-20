package com.example.android.boostcampweek03miniproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.boostcampweek03miniproject.Data.CardItem;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class CreateFragment extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private OnFragmentInteractionListener mListener;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @BindView(R.id.et_location)
    TextView location;
    @BindView(R.id.et_title)
    EditText title;
    @BindView(R.id.et_phone)
    EditText phone;
    @BindView(R.id.et_content)
    EditText content;
    @BindView(R.id.et_count)
    TextView tv_count;

    //완료 클릭시 database 저장
    @OnClick(R.id.create_complete)
    void onClickSave() {
        Date d = new Date();
        d.getTime();
        CardItem c = new CardItem(getString(R.string.dummyLocation), title.getText().toString(), content.getText().toString(),
                location.getText().toString(), phone.getText().toString(), 0, 0, false, d);
        myRef.push().setValue(c);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    //지도보기 클릭시
    @OnClick(R.id.create_cancel)
    void onClickToMap() {
        mListener.onCreateFragmentInteraction();
    }

    public static CreateFragment newInstance() {
        Bundle args = new Bundle();
        CreateFragment fragment = new CreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this, view);

        //저장된 주소값 있을경우에만 주소 추가
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("address")) {
            String address = arguments.getString("address");
            location.setText(address);
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cv_items");

        //주소클릭시 자동완성 API intent실행
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        //내용 입력시 글자수 count 변화
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_count.setText("글자수 : " + String.valueOf(s.length())+"/500");
            }
        });
        return view;
    }

    //자동완성 API 선택후, 주소는 textview에, latlng값은 activity에 저장
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                location.setText(String.valueOf(place.getAddress()));
                CreateActivity c = (CreateActivity) mListener;
                c.setLatlng(place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled operation.
            }
        }
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
        void onCreateFragmentInteraction();

    }
}
