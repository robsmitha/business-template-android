package com.robsmitha.business_template_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.robsmitha.business_template_android.fragments.EmployeeCreateFragment;
import com.robsmitha.business_template_android.fragments.EmployeeDetailFragment;
import com.robsmitha.business_template_android.fragments.EmployeeEditFragment;
import com.robsmitha.business_template_android.fragments.SignInFragment;
import com.robsmitha.business_template_android.interfaces.IEmployee;
import com.robsmitha.business_template_android.models.Employee;
import com.robsmitha.business_template_android.utilities.FirebaseManager;

public class EmployeeActivity extends AppCompatActivity implements IEmployee {

    public FirebaseManager mFirebaseManager;
    private static final String TAG = "EmployeeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        mFirebaseManager = new FirebaseManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        if(getIntent().getExtras() != null){
            String value = getIntent().getExtras().getString("Id");
            Bundle bundle = new Bundle();
            EmployeeDetailFragment employeeDetailFragment = new EmployeeDetailFragment();
            bundle.putString("Id", value);
            employeeDetailFragment.setArguments(bundle);
            ReplaceFragment(employeeDetailFragment);
        }else {
            EmployeeCreateFragment employeeCreateFragment = new EmployeeCreateFragment();
            ReplaceFragment(employeeCreateFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof EmployeeCreateFragment) {

        }else if (fragment instanceof EmployeeDetailFragment){

        }else if (fragment instanceof EmployeeEditFragment){

        }
    }

    public void ReplaceFragment(Fragment fragment){
        Log.i(TAG, "ReplaceFragment()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment instanceof EmployeeCreateFragment){
            EmployeeCreateFragment employeeCreateFragment = new EmployeeCreateFragment();
            fragmentTransaction.replace(R.id.frame, employeeCreateFragment);
        }else if (fragment instanceof EmployeeDetailFragment){
            EmployeeDetailFragment employeeDetailFragment = new EmployeeDetailFragment();
            fragmentTransaction.replace(R.id.frame, employeeDetailFragment);
        }else if (fragment instanceof EmployeeEditFragment){
            EmployeeEditFragment employeeEditFragment = new EmployeeEditFragment();
            fragmentTransaction.replace(R.id.frame, employeeEditFragment);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onEmployeeSaved(Employee employee) {
        Log.i(TAG, "onEmployeeSaved()");
        if(employee.getId() != null){
            Bundle bundle = new Bundle();
            EmployeeDetailFragment employeeDetailFragment = new EmployeeDetailFragment();
            bundle.putString("Id", employee.getId());
            employeeDetailFragment.setArguments(bundle);
            ReplaceFragment(employeeDetailFragment);
        }
    }
}
