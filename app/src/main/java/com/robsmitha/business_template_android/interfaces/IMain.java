package com.robsmitha.business_template_android.interfaces;

import com.robsmitha.business_template_android.models.Employee;

public interface IMain {
    void onEmployeeAuthenticated(Employee employee);
    void onCreateButtonClicked();
}
