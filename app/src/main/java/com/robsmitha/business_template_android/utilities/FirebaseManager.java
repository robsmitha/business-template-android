package com.robsmitha.business_template_android.utilities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robsmitha.business_template_android.models.Employee;

import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {

    private static final String TAG = "FirebaseManager";

    public static String EMPLOYEE_TABLE = "employees";

    public DatabaseReference mDatabase;

    public FirebaseAuth mAuth;

    public FirebaseManager() {

        this.mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    /*
    save Employee
     */
    public void createUser(final Employee employee){
        try{
            mAuth.createUserWithEmailAndPassword(employee.getEmail(),employee.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success
                                Log.d(TAG, "createUserWithEmail:success - uid: " + mAuth.getCurrentUser().getUid());

                            } else {
                                // Sign in failure
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            }
                        }
                    });
        }
        catch (Exception ex){

        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                        }

                        // ...
                    }
                });
    }
}
