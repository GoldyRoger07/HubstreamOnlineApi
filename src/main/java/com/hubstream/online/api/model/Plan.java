package com.hubstream.online.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_plan")
    private int idPlan;

    private String titre;

    private int duree;

    @Column(name="duree_extension")
    private String dureeExtension;

    private double prix;

    private int points;

    private String type;

    public String getFormatPrix() {
        return (prix % 1 == 0) ? String.valueOf((int) prix) : String.valueOf(prix);
    }

}
