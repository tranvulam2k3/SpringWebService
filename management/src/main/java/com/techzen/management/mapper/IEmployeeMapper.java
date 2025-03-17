package com.techzen.management.mapper;

import com.techzen.management.dto.employee.EmployeeRequest;
import com.techzen.management.dto.employee.EmployeeResponse;
import com.techzen.management.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {
    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse employeeToEmployeeResponse(Employee employee);
}
