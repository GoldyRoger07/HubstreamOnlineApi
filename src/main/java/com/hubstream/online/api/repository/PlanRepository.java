package com.hubstream.online.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstream.online.api.model.Plan;

public interface PlanRepository extends JpaRepository<Plan,Integer>{
    
}
