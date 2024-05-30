package com.hubstream.online.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
