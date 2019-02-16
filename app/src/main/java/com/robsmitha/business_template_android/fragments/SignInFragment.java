package com.robsmitha.business_template_android.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.robsmitha.business_template_android.MainActivity;
import com.robsmitha.business_template_android.R;
import com.robsmitha.business_template_android.interfaces.IMain;
import com.robsmitha.business_template_android.models.BaseModel;
import com.robsmitha.business_template_android.models.Employee;
import com.robsmitha.business_template_android.utilities.FirebaseManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    IMain mListener;


    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        Button btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view =  getView();
                assert view != null;
                String email = ((EditText)view.findViewById(R.id.email)).getText().toString();
                String password = ((EditText)view.findViewById(R.id.password)).getText().toString();

                Activity activity = getActivity();
                if(activity instanceof MainActivity){

                    Query query = ((MainActivity)activity).mFirebaseManager.mDatabase.child(FirebaseManager.EMPLOYEE_TABLE).orderByChild("emailAddress").equalTo(email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // dataSnapshot is the "issue" node with all children with id 0
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Employee e = data.getValue(Employee.class);
                                    Log.d(TAG, "Load().onDataChange(): id = "+ e.getId());
                                    //TODO: Add password decryption and matching
                                    mListener.onEmployeeAuthenticated(e);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });
        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreateButtonClicked();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMain) {
            mListener = (IMain) context;
        } else {
            throw new ClassCastException(context.toString());
        }
    }
}
