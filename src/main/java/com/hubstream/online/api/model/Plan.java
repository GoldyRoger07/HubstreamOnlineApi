package com.hubstream.online.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlan;

    private String titre;

    private int duree;

    private String dureeExtension;

    private double prix;

    private int points;

    private String type;

    public String getFormatPrix() {
        return (prix % 1 == 0) ? String.valueOf((int) prix) : String.valueOf(prix);
    }

}
