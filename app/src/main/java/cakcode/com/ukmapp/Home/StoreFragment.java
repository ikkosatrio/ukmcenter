package cakcode.com.ukmapp.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.synnapps.carouselview.CarouselView;

import cakcode.com.ukmapp.Member.LoginActivity;
import cakcode.com.ukmapp.Member.RegistrationActivity;
import cakcode.com.ukmapp.R;
import cakcode.com.ukmapp.Store.RequestStoreActivity;
import spencerstudios.com.bungeelib.Bungee;

public class StoreFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    View view;
    Button btnBukaUKM;

    public StoreFragment() {
        // Required empty public constructor
    }


    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);

        btnBukaUKM = (Button) view.findViewById(R.id.btnBukaUKM);


        btnBukaUKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RequestStoreActivity.class));
                Bungee.slideUp(getActivity());
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
