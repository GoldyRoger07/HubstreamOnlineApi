package com.hubstream.online.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
    @Column(name="id_activer_plan")
    private int idActiverPlan;

    @Column(name="date_debut")
    private LocalDateTime dateDebut;

    @Column(name="date_expiration")
    private LocalDateTime dateExpiration;

    private String etat;

    @Column(name="is_plan_bonus")
    private boolean isPlanBonus;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    @JsonIgnore
    private Compte compte;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

}
