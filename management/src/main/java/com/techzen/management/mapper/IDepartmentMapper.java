package com.techzen.management.mapper;

import com.techzen.management.dto.department.DepartmentResponse;
import com.techzen.management.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepartmentMapper {
    DepartmentResponse toDepartmentResponse(Department department);
}