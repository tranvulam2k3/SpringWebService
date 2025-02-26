package com.techzen.marketplace.service;

import com.techzen.marketplace.dto.PremisesSearchRequest;
import com.techzen.marketplace.enums.ErrorCode;
import com.techzen.marketplace.exception.ApiException;
import com.techzen.marketplace.model.Premises;
import com.techzen.marketplace.repository.IPremisesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PremisesService implements IPremisesService {
    IPremisesRepository premisesRepository;

    @Override
    public List<Premises> findByAttribute(PremisesSearchRequest premisesSearchRequest) {
        return premisesRepository.findByAttribute(premisesSearchRequest);
    }

    @Override
    public Optional<Premises> findById(String id) {
        return premisesRepository.findById(id);
    }

    @Override
    public Premises save(Premises premises) {
        if (premisesRepository.findById(premises.getId()).isPresent()) {
            throw new ApiException(ErrorCode.PREMISES_ALREADY_EXIST);
        }
        return premisesRepository.save(premises);
    }

    @Override
    public void delete(String id) {
        premisesRepository.delete(id);
    }
}
