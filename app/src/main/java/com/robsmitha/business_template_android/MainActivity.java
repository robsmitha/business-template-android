package com.robsmitha.business_template_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.robsmitha.business_template_android.fragments.EmployeeDetailFragment;
import com.robsmitha.business_template_android.fragments.SignInFragment;
import com.robsmitha.business_template_android.interfaces.IMain;
import com.robsmitha.business_template_android.models.Employee;
import com.robsmitha.business_template_android.utilities.FirebaseManager;

public class MainActivity extends AppCompatActivity implements IMain {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    public FirebaseManager mFirebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate()");
        
        //Initialize
        mAuth = FirebaseAuth.getInstance();
        mFirebaseManager = new FirebaseManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");

        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(mAuth.getCurrentUser());

        SignInFragment signInFragment = new SignInFragment();
        ReplaceFragment(signInFragment);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof SignInFragment) {

        }
    }

    public void ReplaceFragment(Fragment fragment){
        Log.i(TAG, "ReplaceFragment()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment instanceof SignInFragment) {
            SignInFragment signInFragment = new SignInFragment();
            fragmentTransaction.replace(R.id.frame, signInFragment);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void updateUI(FirebaseUser user) {
        Log.i(TAG, "updateUI()");
        if (user != null) {
            Log.i(TAG, "updateUI(): User Authenticated");
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            Log.i(TAG, "updateUI(): name = " + name);
            Log.i(TAG, "updateUI(): email = " + email);
            Log.i(TAG, "updateUI(): photoUrl = " + photoUrl);
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult result) {
                    String idToken = result.getToken();
                    //authenticate with backend server
                    Log.d(TAG, "GetTokenResult result = " + idToken);
                }
            });

            Log.i(TAG, "updateUI(): uid = " + uid);

            Bundle bundle = new Bundle();
            bundle.putString("Id", uid);
            EmployeeDetailFragment employeeDetailFragment = new EmployeeDetailFragment();
            employeeDetailFragment.setArguments(bundle);
            ReplaceFragment(employeeDetailFragment);

        }else{
            Log.i(TAG, "updateUI(): User NOT Authenticated");
            //Show Sign in Fragment
            SignInFragment signInFragment = new SignInFragment();
            ReplaceFragment(signInFragment);
        }
    }

    @Override
    public void onEmployeeAuthenticated(Employee employee) {
        Log.i(TAG, "onEmployeeAuthenticated()");
        if(employee.getId() != null){
            //mFirebaseManager.signIn(employee.getEmail(),employee.getPassword());
            String id = employee.getId();
            Intent mIntent = new Intent(this, EmployeeActivity.class);

            mIntent.putExtra("Id", id);
            Log.i(TAG, "onEmployeeAuthenticated(): id = "+id);

            startActivity(mIntent);

            Toast.makeText(this, "Welcome!",
                    Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Denied!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateButtonClicked() {
        Log.i(TAG, "onCreateButtonClicked()");
        //Start Employee Activity
        Intent mIntent = new Intent(this, EmployeeActivity.class);
        startActivity(mIntent);
    }

}
