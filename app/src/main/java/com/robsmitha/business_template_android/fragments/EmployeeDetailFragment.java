package com.robsmitha.business_template_android.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robsmitha.business_template_android.R;
import com.robsmitha.business_template_android.interfaces.IEmployee;
import com.robsmitha.business_template_android.models.Employee;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeDetailFragment extends Fragment {

    private static final String TAG = "EmployeeDetailFragment";

    IEmployee mListener;

    public EmployeeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");

        if( getArguments()!= null){
            String Id = getArguments().getString("Id");
            Log.i(TAG, "onCreate(): Id = " + Id);
            Employee employee = new Employee();
            employee.Load();
            Log.i(TAG, "onCreate(): employee.getId() = " + employee.getId());

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_detail_employee, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IEmployee) {
            mListener = (IEmployee) context;
        } else {
            throw new ClassCastException(context.toString());
        }
    }

}
