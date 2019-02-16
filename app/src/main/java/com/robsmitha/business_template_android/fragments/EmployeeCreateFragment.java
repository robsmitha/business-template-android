package com.robsmitha.business_template_android.fragments;

import android.app.Activity;
import android.content.Context;
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
import com.google.firebase.database.ValueEventListener;
import com.robsmitha.business_template_android.EmployeeActivity;
import com.robsmitha.business_template_android.MainActivity;
import com.robsmitha.business_template_android.R;
import com.robsmitha.business_template_android.interfaces.IEmployee;
import com.robsmitha.business_template_android.interfaces.IMain;
import com.robsmitha.business_template_android.models.Employee;
import com.robsmitha.business_template_android.utilities.FirebaseManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeCreateFragment extends Fragment {

    private static final String TAG = "EmployeeCreateFragment";
    IEmployee mListener;

    public EmployeeCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_create_employee, container, false);
        Button btnCreateEmployee = view.findViewById(R.id.btnCreateEmployee);
        btnCreateEmployee.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreateView().onClick()");
                View view = getView();

                String firstName = ((EditText)view.findViewById(R.id.firstName)).getText().toString();
                String middleName = ((EditText)view.findViewById(R.id.middleName)).getText().toString();
                String lastName = ((EditText)view.findViewById(R.id.lastName)).getText().toString();
                String email = ((EditText)view.findViewById(R.id.email)).getText().toString();
                String password = ((EditText)view.findViewById(R.id.password)).getText().toString();
                //TODO: Add password hashing and store that value
                Date createDate = Calendar.getInstance().getTime();
                Activity activity = getActivity();
                if(activity instanceof EmployeeActivity){
                    Log.d(TAG, "onCreateView().onClick()");
                    String Id = ((EmployeeActivity)activity).mFirebaseManager.mDatabase.child(FirebaseManager.EMPLOYEE_TABLE).push().getKey();
                    Employee employee = new Employee(Id,firstName,middleName,lastName,email,password,createDate, true);
                    Map<String, Object> postValues = employee.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/" + FirebaseManager.EMPLOYEE_TABLE + "/" + employee.getId(), postValues);
                    ((EmployeeActivity)activity).mFirebaseManager.mDatabase.updateChildren(childUpdates);

                    Log.d(TAG, "onCreateView().onClick(): id = "+ employee.getId());
                    mListener.onEmployeeSaved(employee);
                }
            }
        });

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
