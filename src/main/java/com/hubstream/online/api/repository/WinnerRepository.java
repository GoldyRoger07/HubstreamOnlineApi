package com.hubstream.online.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstream.online.api.model.Winner;

public interface WinnerRepository extends JpaRepository<Winner, Integer> {

}
