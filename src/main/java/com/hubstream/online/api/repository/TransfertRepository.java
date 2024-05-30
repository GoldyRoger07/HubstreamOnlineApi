package com.hubstream.online.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstream.online.api.model.Transfert;

public interface TransfertRepository  extends JpaRepository<Transfert,Integer>{
    
}
