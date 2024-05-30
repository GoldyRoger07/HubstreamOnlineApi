package com.hubstream.online.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.Notification;
import com.hubstream.online.api.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Optional<Notification> getNotification(int id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void delete(int id) {
        notificationRepository.deleteById(id);
    }

    public void deleteNotificationsVue() {
        for (Notification n : getNotifications()) {
            if (n.getEtat().equals("vue"))
                delete(n.getIdNotification());
        }
    }

    public List<Notification> getNotificationByEtat(String etat) {
        List<Notification> listeToSend = new ArrayList<>();

        for (Notification n : getNotifications()) {
            if (n.getEtat().equals(etat))
                listeToSend.add(n);
        }

        return listeToSend;
    }

}
