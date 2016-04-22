package edu.uw.jjhama.cimateimpact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by iguest on 4/21/16.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "Profile";

    public ProfileFragment(){
        //required empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.profile, container, false);

        //get the different textviews I want to manipulate
        TextView fName = (TextView) rootView.findViewById(R.id.fName);
        TextView lName = (TextView) rootView.findViewById(R.id.lName);
        TextView carbon = (TextView) rootView.findViewById(R.id.carbon);
        TextView water = (TextView) rootView.findViewById(R.id.water);

        //set the values to users account details
        //Todo: get and set Profile details (From a bundle...?)
//        fName.setText(accountDetails.getfName());
//        lName.setText(accountDetails.getlName());
//        carbon.setText(accountDetails.getCarbon()+ "");
//        water.setText(accountDetails.getWater() + "");

        //go get to activities
        Button button = (Button) rootView.findViewById(R.id.activity);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}