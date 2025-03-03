package com.techzen.management.repository.impl;

import com.techzen.management.dto.EmployeeSearchRequest;
import com.techzen.management.enums.Gender;
import com.techzen.management.model.Department;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.BaseRepository;
import com.techzen.management.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepository implements IEmployeeRepository {

    @Override
    public List<Employee> findByAttribute(EmployeeSearchRequest employeeSearchRequest) {

        Session session = ConnectionUtil.sessionFactory.openSession();
        List<Employee> employeeList = new ArrayList<>();

        try {
            String hql = "FROM Employee e " + "WHERE (:name IS NULL OR LOWER(e.name) LIKE CONCAT('%', :name, '%')) " + "AND (:gender IS NULL OR e.gender = :gender) " + "AND (:phone IS NULL OR LOWER(e.phone) LIKE CONCAT('%', :phone, '%')) " + "AND (:departmentId IS NULL OR e.departmentId.id = :departmentId) " + "AND (:dobFrom IS NULL OR e.dateOfBirth >= :dobFrom) " + "AND (:dobTo IS NULL OR e.dateOfBirth <= :dobTo) ";

            if (employeeSearchRequest.getSalaryRange() != null) {
                switch (employeeSearchRequest.getSalaryRange()) {
                    case "lt5":
                        hql += "AND e.salary < 5000000 ";
                        break;
                    case "5-10":
                        hql += "AND e.salary BETWEEN 5000000 AND 10000000 ";
                        break;
                    case "10-20":
                        hql += "AND e.salary BETWEEN 10000000 AND 20000000 ";
                        break;
                    case "gt20":
                        hql += "AND e.salary >= 20000000 ";
                        break;
                }
            }

            Query<Employee> query = session.createQuery(hql, Employee.class);

            query.setParameter("name", employeeSearchRequest.getName());
            query.setParameter("gender", employeeSearchRequest.getGender());
            query.setParameter("phone", employeeSearchRequest.getPhone());
            query.setParameter("departmentId", employeeSearchRequest.getDepartmentId());
            query.setParameter("dobFrom", employeeSearchRequest.getDobFrom());
            query.setParameter("dobTo", employeeSearchRequest.getDobTo());

            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return employeeList;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Employee employee = session.createQuery("FROM Employee WHERE id = :id", Employee.class).setParameter("id", id).uniqueResult();
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Employee save(Employee employee) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.saveOrUpdate(employee);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
        return employee;
    }

    @Override
    public void delete(UUID id) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Employee employee = session.get(Employee.class, id);
                if (employee != null) {
                    session.delete(employee);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException(e);
            }
        }
    }
}
