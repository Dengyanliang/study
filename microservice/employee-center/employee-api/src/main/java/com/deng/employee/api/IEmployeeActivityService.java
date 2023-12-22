package com.deng.employee.api;


import com.deng.employee.pojo.EmployeeActivity;

public interface IEmployeeActivityService {

    EmployeeActivity useToilet(Long employeeId);

    EmployeeActivity restoreToilet(Long employeeId);

}
