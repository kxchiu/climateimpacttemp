package edu.uw.jjhama.cimateimpact;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by iguest on 5/24/16.
 */
public class ActivityInfoDialog extends Fragment {

    private static final String TAG = "ReminderDialog";
    String email;
    String uuid;
    String actionString;
    String endTimeString;
    String startTimeString;
    String frequencyString;
    boolean once = false;
    private DatabaseReference mDatabase;

    public ActivityInfoDialog(){
        //required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.activity_info, container, false);
        getActivity().setTitle("Activity Info");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        Log.v(TAG, "Profile page loading");

        //get and set email variable from previous fragment
        Bundle getBundle = getArguments();
        if (getBundle != null) {
            if (getBundle.containsKey("email")) {
                email = getBundle.getString("email");
                frequencyString = getBundle.getString("frequency");
                uuid = getBundle.getString("uuid");
                startTimeString = getBundle.getString("startTime");
                endTimeString = getBundle.getString("endTime");
                actionString = getBundle.getString("action");
            } else {
                email = "test";
            }
        } else {
            email = "test";
        }

        TextView frequencyTV = ((TextView) rootView.findViewById(R.id.frequency));
        TextView actionTV = (TextView) rootView.findViewById(R.id.activity);
        TextView startTimeTV = (TextView) rootView.findViewById(R.id.startTime);
        TextView endTimeTV = (TextView) rootView.findViewById(R.id.endTime);

        Log.v(TAG, "frequency: "+ frequencyString + ", action: " + actionString + ", startTime: " + startTimeString + ", endTime: " + endTimeString);

        frequencyTV.setText(frequencyString);
        actionTV.setText(actionString);
        startTimeTV.setText(startTimeString);
        endTimeTV.setText(endTimeString);

        Button completed = (Button) rootView.findViewById(R.id.completed);
        Button delete = (Button) rootView.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:delete activity from the users tasks
                Log.v(TAG,"attempting to delete " + uuid + "from firebase with the email being " + email);
                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("tasks").child(uuid);
                mDatabase.setValue(null);
                Log.v(TAG, "delete was selected");
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:add X amount of points to user
                mDatabase  = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("carbon");
                final long[] carbon = new long[1];
                once = false;
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        //Post post = dataSnapshot.getValue(Post.class);
                        //carbon = dataSnapshot
                        //mDatabase.setValue();
                        if(!once) {
                            carbon[0] = ((long) dataSnapshot.getValue()) + 10;
                            mDatabase.setValue(carbon[0]);
                            once = true;
                        }
                        //Log.v(TAG, dataSnapshot.getValue() + );
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                });

                //int carbon = mDatabase.g
                Log.v(TAG, "complete was selected");
            }
        });

        return rootView;
    }

}
