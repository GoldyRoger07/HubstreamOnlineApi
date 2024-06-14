package com.hubstream.online.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_notification")
    private int idNotification;

    @ManyToOne
    @JoinColumn(name = "id_compte_source")
    @JsonIgnore
    private Compte compteSource;

    @ManyToOne
    @JoinColumn(name = "id_compte_destinataire")
    @JsonIgnore
    private Compte compteDestinataire;

    private String texte;

    private String etat;

}
