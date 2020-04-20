package com.example.jpasecuritymodii.repo;

import com.example.jpasecuritymodii.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifictionTokeRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByValue(String value);
}
