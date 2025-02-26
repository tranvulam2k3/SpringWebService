package com.techzen.marketplace.repository;

import com.techzen.marketplace.dto.PremisesSearchRequest;
import com.techzen.marketplace.model.Premises;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPremisesRepository {
    List<Premises> findByAttribute(PremisesSearchRequest premisesSearchRequest);
    Optional<Premises> findById(String id);
    Premises save(Premises premises);
    void delete(String id);
}
