package com.hubstream.online.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstream.online.api.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
