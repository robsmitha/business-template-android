package com.robsmitha.business_template_android.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robsmitha.business_template_android.R;
import com.robsmitha.business_template_android.interfaces.IEmployee;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeEditFragment extends Fragment {

    IEmployee mListener;

    public EmployeeEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_employee, container, false);
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
