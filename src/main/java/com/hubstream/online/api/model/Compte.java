package com.hubstream.online.api.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="comptes")
public class Compte {
    @Id
    private String idCompte;

    private String nom;

    private String prenom;

    private String telephone;

    private String username;

    private String codePin;

    private String role;

    private double solde;

    private int points;

    private boolean connecter;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ActiverPlan.class, mappedBy = "compte")
    private List<ActiverPlan> activerPlans;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Notification.class, mappedBy = "compteDestinataire")
    private List<Notification> notifications;

    public boolean isConnecter(){
        return connecter;
    }

    public String getFormatSolde() {
        return (solde % 1 == 0) ? String.valueOf((int) solde) : String.valueOf(solde);
    }

    public boolean testRetrait(double montantARetirer) {
        return montantARetirer <= solde && montantARetirer > 0;
    }

    public void effectuerRetrait(double montantARetirer) {
        solde -= montantARetirer;
    }

    public boolean testDepot(double montantADeposer) {
        return montantADeposer > 0;
    }

    public void effectuerDepot(double montantADeposer) {
        solde += montantADeposer;
    }

    public void initialiserPoints() {
        points = 0;
    }

    public boolean testDepotPoints(double pointsADeposer) {
        return pointsADeposer > 0;
    }

    public void effectuerDepotPoints(double pointsADeposer) {
        points += pointsADeposer;
    }

    public void effectuerRetraitPoints(int pointsARetirer) {
        points -= pointsARetirer;
    }

    public boolean testRetraitPoints(double pointsARetirer) {
        return pointsARetirer <= points && pointsARetirer > 0;
    }


}
