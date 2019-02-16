package com.robsmitha.business_template_android.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.robsmitha.business_template_android.utilities.FirebaseManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Employee extends BaseModel {

    private static final String TAG = "Employee";

    private static final String ID = "id";
    private static final String FIRSTNAME = "firstName";
    private static final String MIDDLENAME = "middleName";
    private static final String LASTNAME = "lastName";
    private static final String EMAIL = "emailAddress";
    private static final String PASSWORD = "password";
    private static final String CREATEDATE = "createDate";
    private static final String ACTIVE = "active";

    private String _id;
    private String _firstName;
    private String _middleName;
    private String _lastName;
    private String _email;
    private String _password;
    private Date _createDate;
    private Boolean _active;

    public Employee(){
        _id = null;
        _firstName = "";
        _middleName = "";
        _lastName = "";
        _email = "";
        _password = "";
        _createDate = null;
        _active = true;
    }

    public Employee(String email, String password){
        _id = null;
        _firstName = "";
        _middleName = "";
        _lastName = "";
        _email = email;
        _password = password;
        _createDate = null;
        _active = true;
    }

    public Employee(String id, String firstName, String middleName, String lastName, String email, String password, Date createDate, Boolean active){
        _id = id;
        _firstName = firstName;
        _middleName = middleName;
        _lastName = lastName;
        _email = email;
        _password = password;
        _createDate = createDate;
        _active = active;
    }

    public String getId(){
        return this._id;
    }

    public void setId(String id){
        this._id = id;
    }

    public String getFirstname(){
        return this._firstName;
    }

    public void setFirstname(String firstname){
        this._firstName = firstname;
    }

    public String getMiddlename(){
        return this._middleName;
    }

    public void setMiddlename(String middlename){
        this._middleName = middlename;
    }
    public String getLastname(){
        return this._lastName;
    }

    public void setLastname(String lastname){
        this._lastName = lastname;
    }
    public String getEmail(){
        return this._email;
    }

    public void setEmail(String email){
        _email = email;
    }

    public String getPassword(){
        return this._password;
    }

    public void setPassword(String password){
        this._password = password;
    }

    /*
    toMap()
    */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(ID, _id);
        result.put(FIRSTNAME, _firstName);
        result.put(MIDDLENAME, _middleName);
        result.put(LASTNAME, _lastName);
        result.put(EMAIL, _email);
        result.put(PASSWORD, _password);
        result.put(CREATEDATE, _createDate);
        result.put(ACTIVE, _active);

        return result;
    }

    public void Create(){
        Log.i(TAG, "Create()");
        mFirebaseManager = new FirebaseManager();
        //mFirebaseManager.createUser(this);


        Log.i(TAG, "Create(): employees table = "+ FirebaseManager.EMPLOYEE_TABLE );
        if(this._id == null){
            this._id = mFirebaseManager.mDatabase.child(FirebaseManager.EMPLOYEE_TABLE).push().getKey();
        }

        Log.i(TAG, "Create(): _id = "+ this._id );

        Map<String, Object> postValues = this.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + FirebaseManager.EMPLOYEE_TABLE + "/" + this._id, postValues);
        mFirebaseManager.mDatabase.updateChildren(childUpdates);

        mFirebaseManager.mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Employee e = data.getValue(Employee.class);
                        Log.d(TAG, "Load().onDataChange(): id = "+ e.getId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public String LookUpEmployee(String _email, String _password){
        Log.i(TAG, "LookUpEmployee()");

        mFirebaseManager = new FirebaseManager();


        Query query = mFirebaseManager.mDatabase.child(FirebaseManager.EMPLOYEE_TABLE).orderByChild("emailAddress").equalTo(_email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Employee e = data.getValue(Employee.class);
                        Log.d(TAG, "Load().onDataChange(): id = "+ e.getId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return "";
    }
    public void Load(){
        Log.i(TAG, "Load()");

        mFirebaseManager = new FirebaseManager();

        Query query = mFirebaseManager.mDatabase.child(FirebaseManager.EMPLOYEE_TABLE).orderByChild("emailAddress").equalTo(this._email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot employee : dataSnapshot.getChildren()) {
                        Employee e = employee.getValue(Employee.class);
                        _id = e.getId();
                        Log.d(TAG, "Load().onDataChange(): id = "+ e.getId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}