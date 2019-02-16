package com.robsmitha.business_template_android.models;

import com.robsmitha.business_template_android.utilities.FirebaseManager;

public class BaseModel {


    public FirebaseManager mFirebaseManager;


    public interface IBase {
        void onEmployeeSaved(Employee employee);
    }
}
