package com.techzen.management.repository;

import com.techzen.management.model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}