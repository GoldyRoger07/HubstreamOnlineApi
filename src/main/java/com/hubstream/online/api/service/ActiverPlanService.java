package com.hubstream.online.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.ActiverPlan;
import com.hubstream.online.api.model.ActiverPlanIntermediaire;
import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.Plan;
import com.hubstream.online.api.repository.ActiverPlanRepository;

@Service
public class ActiverPlanService {

    @Autowired
    ActiverPlanRepository activerPlanRepository;

   
    public Optional<ActiverPlan> getActiverPlan(final int idActiverPlan) {
        return activerPlanRepository.findById(idActiverPlan);
    }

    public boolean testPlanBonus(String type, Compte compte) {
        for (ActiverPlan ac : compte.getActiverPlans()) {
            if ((ac.getPlan().getType().equals(type) || ac.getPlan().getType().equals("All"))
                    && ac.getEtat().equals("actif") && ac.isPlanBonus())
                return true;
        }
        return false;
    }

    public List<ActiverPlan> getActiverPlans(){
        return activerPlanRepository.findAll();
    }

    public List<ActiverPlan> getActiverPlansToUpdate() {
        List<ActiverPlan> resultat = new ArrayList<>();
        for(ActiverPlan ac: activerPlanRepository.findAll()){
            ac.getCompte().setActiverPlans(null);
            ac.getCompte().setNotifications(null);
            resultat.add(ac);
        }

        return resultat;
       
        
    }

    public void deleteActiverPlan(final int idActiverPlan) {
        activerPlanRepository.deleteById(idActiverPlan);
    }

    public ActiverPlan save(ActiverPlan activerPlan) {
        return activerPlanRepository.save(activerPlan);
    }

    public boolean testPlanActive(List<ActiverPlan> activePlans, String type) {
        for (ActiverPlan a : activePlans) {
            if (a.getEtat().equals("actif")
                    && (a.getPlan().getType().equals(type) || a.getPlan().getType().equals("All"))) {
                return true;
            }
        }
        return false;
    }

    public ActiverPlan getPlanActif(String type, List<ActiverPlan> activePlans) {

        for (ActiverPlan a : activePlans) {
            if (a.getEtat().equals("actif") &&
                    (a.getPlan().getType().equals(type)
                            || a.getPlan().getType().equals("All"))) {
                return a;
            }
        }
        return null;
    }

    public void updateActivePlans() {
        List<ActiverPlan> activerPlans = getActiverPlans();
        LocalDateTime today = LocalDateTime.now();

        for (ActiverPlan a : activerPlans) {
            if (a.getEtat().equals("actif") && today.isAfter(a.getDateExpiration())) {
                a.setEtat("expirer");
                save(a);
            }
        }
    }

    public void updateAttentePlans(List<ActiverPlan> activerPlans) {
        Plan plan;
        for (ActiverPlan a : activerPlans) {
            if (a.getEtat().equals("attente")) {
                plan = a.getPlan();
                if (!testPlanActive(activerPlans, plan.getType())) {
                    a.setDateDebut(LocalDateTime.now());
                    a.setEtat("actif");

                    if (plan.getDureeExtension().contains("Heure"))
                        a.setDateExpiration(a.getDateDebut().plusHours(plan.getDuree()));

                    if (plan.getDureeExtension().contains("Minute"))
                        a.setDateExpiration(a.getDateDebut().plusMinutes(plan.getDuree()));

                    if (plan.getDureeExtension().contains("Jour"))
                        a.setDateExpiration(a.getDateDebut().plusDays(plan.getDuree()));

                    save(a);
                }
            }
        }
    }

    public List<ActiverPlanIntermediaire> getListActiverPlan(Compte compte, String etat) {
        List<ActiverPlanIntermediaire> listAEnvoyer = new ArrayList<>();

        for (ActiverPlan a : compte.getActiverPlans()) {
            if (a.getEtat().equals(etat)) {
                ActiverPlanIntermediaire activerPlanIntermediaire = new ActiverPlanIntermediaire();
                activerPlanIntermediaire.setActiverPlan(a);
                activerPlanIntermediaire.setPrixFormatter(a.getPlan().getFormatPrix());
                activerPlanIntermediaire
                        .setHeureDebut(a.getDateDebut().toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));
                activerPlanIntermediaire.setHeureExpiration(
                        a.getDateExpiration().toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));

                if (a.getDateDebut().toLocalDate().equals(LocalDate.now())) {
                    activerPlanIntermediaire.setDateDebut("aujourd'hui");
                } else if (a.getDateDebut().toLocalDate().plusDays(1).equals(LocalDate.now())) {
                    activerPlanIntermediaire.setDateDebut("hier");
                } else {
                    activerPlanIntermediaire
                            .setDateDebut("le " + a.getDateDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                if (a.getDateExpiration().toLocalDate().equals(LocalDate.now())) {
                    activerPlanIntermediaire.setDateExpiration("aujourd'hui");
                } else if (a.getDateExpiration().toLocalDate().minusDays(1).isEqual(LocalDate.now())) {
                    activerPlanIntermediaire.setDateExpiration("demain");
                } else {
                    activerPlanIntermediaire.setDateExpiration(
                            "le " + a.getDateExpiration().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                listAEnvoyer.add(activerPlanIntermediaire);

            }

        }

        return listAEnvoyer;
    }

    // public void SauvegardeActiverPlan() {
    //     List<ActiverPlan> activerPlans = getActiverPlans();

    //     List<String> sqlQueries = new ArrayList<>();

    //     for (ActiverPlan ap : activerPlans) {
    //         String sqlQuerie = "INSERT INTO activer_plan VALUES (0,'" + ap.getDateDebut() + "','"
    //                 + ap.getDateExpiration() + "','" + ap.getEtat() + "','" + ap.getCompte().getIdCompte() + "',"
    //                 + ap.getPlan().getIdPlan() + "," + ap.isPlanBonus() + ");";

    //         sqlQueries.add(sqlQuerie);
    //     }

       
    // }


    // public void SauvegardeActiverPlan() {
    //     List<ActiverPlan> activerPlans = getActiverPlans();

    //     List<String> sqlQueries = new ArrayList<>();

    //     for (ActiverPlan ap : activerPlans) {
    //         String sqlQuerie = "INSERT INTO activer_plan VALUES (0,'" + ap.getDateDebut() + "','"
    //                 + ap.getDateExpiration() + "','" + ap.getEtat() + "','" + ap.getCompte().getIdCompte() + "',"
    //                 + ap.getPlan().getIdPlan() + "," + ap.isPlanBonus() + ");";

    //         sqlQueries.add(sqlQuerie);
    //     }

    //     MainService.sauvegardeSql("activerPlans.sql", sqlQueries);
    // }

}
