package com.hubstream.online.api.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.hubstream.online.api.service.ActiverPlanService;

public class ActivationPlanBonus implements ActivationPlan {

    ActiverPlanService activerPlanService;

    public ActivationPlanBonus(@Autowired ActiverPlanService activerPlanService) {
        this.activerPlanService = activerPlanService;
    }

    @Override
    public boolean activer(Plan plan, Compte compte) {

        if (!(plan.getPrix() == 0 && plan.getPoints() == 0)) {
            if (compte.testRetraitPoints(plan.getPoints()))
                compte.effectuerRetraitPoints(plan.getPoints());
            else
                return false;
        }

        ActiverPlan activerPlan = new ActiverPlan();
        activerPlan.setPlanBonus(true);

        if (activerPlanService.testPlanActive(compte.getActiverPlans(), plan.getType()))
            activerPlan.setEtat("attente");
        else {
            activerPlan.setEtat("actif");
            activerPlan.setDateDebut(LocalDateTime.now());

            if (plan.getDureeExtension().contains("Heure"))
                activerPlan.setDateExpiration(activerPlan.getDateDebut().plusHours(plan.getDuree()));

            if (plan.getDureeExtension().contains("Minute"))
                activerPlan.setDateExpiration(activerPlan.getDateDebut().plusMinutes(plan.getDuree()));

            if (plan.getDureeExtension().contains("Jour"))
                activerPlan.setDateExpiration(activerPlan.getDateDebut().plusDays(plan.getDuree()));

        }

        activerPlan.setPlan(plan);
        activerPlan.setCompte(compte);

        activerPlanService.save(activerPlan);

        return true;

    }

}
