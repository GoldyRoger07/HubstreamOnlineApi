package com.hubstream.online.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ActiverPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActiverPlan;

    private LocalDateTime dateDebut;

    private LocalDateTime dateExpiration;

    private String etat;

    private boolean isPlanBonus;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    @JsonIgnore
    private Compte compte;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

}
