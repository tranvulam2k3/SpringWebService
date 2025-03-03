package com.techzen.management.repository;

import com.techzen.management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = """
            SELECT e.* FROM Employee e
            JOIN Department d on e.department_id = d.department_id
            WHERE (:name IS NULL OR e.name LIKE CONCAT('%', :name ,'%'))
            AND (:dobFrom IS NULL OR e.date_of_birth >= :dobFrom)
            AND (:dobTo IS NULL OR e.date_of_birth <= :dobTo)
            AND (:gender IS NULL OR e.gender = :gender)
            AND (:phone IS NULL OR e.phone LIKE CONCAT('%', :phone ,'%'))
            AND (:department_id IS NULL OR e.department_id = :department_id)
            AND (:salaryRange IS NULL
                       OR (:salaryRange = 'lt5' AND e.salary < 5000000)
                       OR (:salaryRange = '5-10' AND e.salary BETWEEN 5000000 AND 10000000)
                       OR (:salaryRange = '10-20' AND e.salary BETWEEN 10000000 AND 20000000)
                       OR (:salaryRange = 'gt20' AND e.salary > 20000000))
            """, nativeQuery = true)
    List<Employee> findByAttribute(@Param("name") String name,
                                   @Param("dobFrom") LocalDate dobFrom,
                                   @Param("dobTo") LocalDate dobTo,
                                   @Param("gender") String gender,
                                   @Param("salaryRange") String salaryRange,
                                   @Param("phone") String phone,
                                   @Param("department_id") Integer department_id);

}
