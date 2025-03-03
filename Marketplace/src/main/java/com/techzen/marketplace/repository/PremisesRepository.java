package com.techzen.marketplace.repository;

import com.techzen.marketplace.dto.PremisesSearchRequest;
import com.techzen.marketplace.model.Premises;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PremisesRepository implements IPremisesRepository {
    @Override
    public List<Premises> findByAttribute(PremisesSearchRequest premisesSearchRequest) {
        List<Premises> premisesList = new ArrayList<>();
        try {
            String query = "select * from premises join PremisesType PT on premises.PremisesTypeId = PT.PremisesTypeId where 1=1 ";

            List<Object> parameters = new ArrayList<>();
            if (premisesSearchRequest.getId() != null) {
                query += " and id = ?";
                parameters.add(premisesSearchRequest.getId());
            }
            if (premisesSearchRequest.getName() != null) {
                query += " and lower(name) like ?";
                parameters.add("%" + premisesSearchRequest.getName().toLowerCase() + "%");
            }
            if (premisesSearchRequest.getAddress() != null) {
                query += " and lower(address) like ?";
                parameters.add("%" + premisesSearchRequest.getAddress().toLowerCase() + "%");
            }
            if (premisesSearchRequest.getPremisesTypeId() != null) {
                query += " and PremisesTypeId = ?";
                parameters.add(premisesSearchRequest.getPremisesTypeId());
            }
            if (premisesSearchRequest.getDorFrom() != null) {
                query += " and rentalStartDate >= ?";
                parameters.add(premisesSearchRequest.getDorFrom());
            }
            if (premisesSearchRequest.getDorTo() != null) {
                query += " and rentalStartDate <= ?";
                parameters.add(premisesSearchRequest.getDorTo());
            }
            if (premisesSearchRequest.getAcreageFrom() != null) {
                query += " and acreage >= ?";
                parameters.add(premisesSearchRequest.getAcreageFrom());
            }
            if (premisesSearchRequest.getAcreageTo() != null) {
                query += " and acreage <= ?";
                parameters.add(premisesSearchRequest.getAcreageTo());
            }
            if (premisesSearchRequest.getRent() != null) {
                switch (premisesSearchRequest.getRent()) {
                    case "2tr<":
                        query += " and price < 2000000";
                        break;
                    case "2tr-5tr":
                        query += " and price >= 2000000 and price <= 5000000";
                        break;
                    case "5tr-10tr":
                        query += " and price >= 5000000 and price <= 10000000";
                        break;
                    case "10tr>":
                        query += " and price >= 10000000";
                        break;
                }
            }

            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(query);

            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof LocalDate) {
                    preparedStatement.setDate(i + 1, Date.valueOf((LocalDate) parameters.get(i)));
                } else if (parameters.get(i) instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) parameters.get(i));
                } else {
                    preparedStatement.setObject(i + 1, parameters.get(i));
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Premises employee = new Premises(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getInt("acreage"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getDate("rentalStartDate").toLocalDate(),
                        resultSet.getInt("PremisesTypeId"));
                premisesList.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return premisesList;
    }

    @Override
    public Optional<Premises> findById(String id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement("SELECT * FROM premises WHERE id = ?");
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Premises premises = Premises.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .address(resultSet.getString("address"))
                        .acreage(resultSet.getInt("acreage"))
                        .price(resultSet.getBigDecimal("price"))
                        .rentalStartDate(resultSet.getDate("rentalStartDate").toLocalDate())
                        .PremisesTypeId(resultSet.getInt("PremisesTypeId"))
                        .build();
                return Optional.of(premises);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Premises save(Premises premises) {

        String sql = "INSERT INTO premises (id,name, address, acreage, price, rentalStartDate, PremisesTypeId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = BaseRepository.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, premises.getId());
            preparedStatement.setString(2, premises.getName());
            preparedStatement.setString(3, premises.getAddress());
            preparedStatement.setInt(4, premises.getAcreage());
            preparedStatement.setBigDecimal(5, premises.getPrice());
            preparedStatement.setDate(6, Date.valueOf(premises.getRentalStartDate()));
            preparedStatement.setInt(7, premises.getPremisesTypeId());

            preparedStatement.executeUpdate();

            try (ResultSet res = preparedStatement.getGeneratedKeys()) {
                if (res.next()) {
                    premises.setId(res.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return premises;
    }

    @Override
    public void delete(String id) {
        try {
            PreparedStatement preparedStatement = BaseRepository.getConnection()
                    .prepareStatement("delete from premises where id = ?");
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
