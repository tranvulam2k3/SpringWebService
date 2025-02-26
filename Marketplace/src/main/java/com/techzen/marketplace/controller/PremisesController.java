package com.techzen.marketplace.controller;

import com.techzen.marketplace.dto.PremisesSearchRequest;
import com.techzen.marketplace.enums.ErrorCode;
import com.techzen.marketplace.exception.ApiException;
import com.techzen.marketplace.model.Premises;
import com.techzen.marketplace.service.IPremisesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/premises")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PremisesController {
    IPremisesService premisesService;

    @GetMapping
    public ResponseEntity<?> getAllPremises(PremisesSearchRequest request) {
        return ResponseEntity.ok(premisesService.findByAttribute(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPremises(@PathVariable String id) {
        return premisesService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.PREMISES_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> createPremises(@RequestBody Premises premises) {
        return ResponseEntity.status(HttpStatus.CREATED).body(premisesService.save(premises));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePremises(@PathVariable String id) {
        premisesService.findById(id).orElseThrow(() -> new ApiException(ErrorCode.PREMISES_NOT_EXIST));
        premisesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
