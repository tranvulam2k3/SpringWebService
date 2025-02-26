package com.techzen.marketplace.service;

import com.techzen.marketplace.dto.PremisesSearchRequest;
import com.techzen.marketplace.model.Premises;

import java.util.List;
import java.util.Optional;

public interface IPremisesService {
    List<Premises> findByAttribute(PremisesSearchRequest premisesSearchRequest);
    Optional<Premises> findById(String id);
    Premises save(Premises premises);
    void delete(String id);
}
