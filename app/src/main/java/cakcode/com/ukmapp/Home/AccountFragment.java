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
import android.widget.TextView;

import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Member.LoginActivity;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.R;
import cakcode.com.ukmapp.Store.RequestStoreActivity;
import spencerstudios.com.bungeelib.Bungee;

public class AccountFragment extends Fragment {

    Button btnLogout;
    private SessionManager session;
    TextView tvName,tvStatus;
    Member mCurrenMember;

    public AccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        session = new SessionManager(getActivity());
        btnLogout = (Button)view.findViewById(R.id.btnLogout);
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvStatus = (TextView)view.findViewById(R.id.tvStatus);


        if(session.isLoggedIn()){
            mCurrenMember = session.getCurrentMember();
            tvName.setText(mCurrenMember.getName());
            tvStatus.setText(mCurrenMember.getStatus());
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setCurrentMember("");
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Bungee.slideDown(getActivity());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
