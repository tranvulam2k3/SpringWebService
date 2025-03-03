package com.techzen.management.repository.impl;

import com.techzen.management.model.Department;
import com.techzen.management.model.Employee;
import com.techzen.management.repository.BaseRepository;
import com.techzen.management.repository.IDepartmentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements IDepartmentRepository {

    @Override
    public List<Department> getAll() {
        Session session = ConnectionUtil.sessionFactory.openSession();
        List<Department> departments = null;
        try {
            departments = session.createQuery("FROM Department")
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(Integer departmentId) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Department department = session.createQuery("FROM Department WHERE departmentId = :departmentId", Department.class)
                    .setParameter("departmentId", departmentId)
                    .uniqueResult();
            return Optional.ofNullable(department);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Department save(Department department) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.saveOrUpdate(department);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    throw new RuntimeException(e);
                }
            }
        }
        return department;
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        try (Session session = ConnectionUtil.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Department department = session.get(Department.class, departmentId);
                if (department != null) {
                    session.delete(department);
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
