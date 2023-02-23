package com.example.timeregistrychallenger.repository;

import com.example.timeregistrychallenger.models.Alocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlocationRepository extends JpaRepository<Alocation, Long> {
}
