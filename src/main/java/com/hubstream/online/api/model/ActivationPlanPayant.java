package com.hubstream.online.api.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.hubstream.online.api.service.ActiverPlanService;

public class ActivationPlanPayant implements ActivationPlan {

    ActiverPlanService activerPlanService;

    public ActivationPlanPayant(@Autowired ActiverPlanService activerPlanService) {
        this.activerPlanService = activerPlanService;
    }

    @Override
    public boolean activer(Plan plan, Compte compte) {

        ActiverPlan activerPlan = new ActiverPlan();
        activerPlan.setPlanBonus(false);

        if (activerPlanService.testPlanActive(compte.getActiverPlans(), plan.getType()))
            activerPlan.setEtat("attente");
        else {
            activerPlan.setEtat("actif");
            activerPlan.setDateDebut(LocalDateTime.now());

            if (plan.getDureeExtension().contains("Heure"))
                activerPlan.setDateExpiration(activerPlan.getDateDebut().plusHours(plan.getDuree()));

            if (plan.getDureeExtension().contains("Jour"))
                activerPlan.setDateExpiration(activerPlan.getDateDebut().plusDays(plan.getDuree()));
        }

       

        if (compte.testRetrait(plan.getPrix()) || compte.getRole().equals("admin")) {
            if(!compte.getRole().equals("admin"))
                 compte.effectuerRetrait(plan.getPrix());
           
            activerPlan.setPlan(plan);
            activerPlan.setCompte(compte);

            activerPlanService.save(activerPlan);
            // System.out.println("check 2 : " +
            // activerPlanService.getCountNotValidatePlan(plan, compte));
            return true;
        }

        return false;
    }

}
