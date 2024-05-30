package com.hubstream.online.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstream.online.api.model.Compte;

public interface CompteRepository extends JpaRepository<Compte,String>{
    public Optional<Compte> findByUsername(String username);
}
