package com.hubstream.online.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubstream.online.api.model.ActivationPlan;
import com.hubstream.online.api.model.ActivationPlanPayant;
import com.hubstream.online.api.model.ActiverPlan;
import com.hubstream.online.api.model.ActiverPlanIntermediaire;
import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.FormActiverPlan;
import com.hubstream.online.api.model.Plan;
import com.hubstream.online.api.model.PlansContainer;
import com.hubstream.online.api.service.ActiverPlanService;
import com.hubstream.online.api.service.CompteService;
import com.hubstream.online.api.service.PlanService;

@RestController
@RequestMapping("/api.online.hubstream.com")
public class PlanController {
    @Autowired
    PlanService planService;

    @Autowired
    ActiverPlanService activerPlanService;

    @Autowired
    CompteService compteService;

    @GetMapping("/plans")
    public PlansContainer getPlans() {
        PlansContainer plansContainer = new PlansContainer();
        // plansContainer.setPlansFilm(planService.getPlansByType("Film"));
        // plansContainer.setPlansSerie(planService.getPlansByType("Serie"));
        // plansContainer.setPlansAnime(planService.getPlansByType("Anime"));
        plansContainer.setPlansAll(planService.getPlansByType("All"));

        return plansContainer;
    }

    @PostMapping("/plan")
    public String addPlan(@RequestBody Plan plan) {
        planService.save(plan);
        return "{\"status\":\"reussi\"}";
    }

    @PutMapping("/plan")
    public String updatePlan(@RequestBody Plan plan) {
        planService.save(plan);
        return "{\"status\":\"reussi\"}";
    }

    @PutMapping("/activerPlan")
    public String updateActiverPlan(@RequestBody ActiverPlan activerPlan) {
        activerPlanService.save(activerPlan);
        return "{\"status\":\"reussi\"}";
    }

    @GetMapping("/plans/actif/{idCompte}")
    public List<ActiverPlanIntermediaire> getPlansActifOfCompte(@PathVariable("idCompte") String idCompte) {
        Optional<Compte> compteOpt = compteService.getCompte(idCompte);

        Compte compte = compteOpt.isPresent() ? compteOpt.get() : null;

        if (compte != null) {
            activerPlanService.updateActivePlans();
            activerPlanService.updateAttentePlans(compte.getActiverPlans());

            return activerPlanService.getListActiverPlan(compte, "actif");
        }

        return null;
    }

    @GetMapping("/activerPlans")
    public List<ActiverPlan> getActiverPlans(){
        return activerPlanService.getActiverPlans();
    }

    @GetMapping("/plan/actif/{type}/{idCompte}")
    public ActiverPlan getPlansActif(@PathVariable("type") String type, @PathVariable("idCompte") String idCompte) {
        Optional<Compte> compteOpt = compteService.getCompte(idCompte);

        Compte compte = compteOpt.isPresent() ? compteOpt.get() : null;

        if (compte != null) {
            activerPlanService.updateActivePlans();
            activerPlanService.updateAttentePlans(compte.getActiverPlans());

            return activerPlanService.getPlanActif(type, compte.getActiverPlans());
        }
        return null;
    }

    @PostMapping("/plans/activation")
    public String activationPlan(@RequestBody FormActiverPlan formActiverPlan) {

        Compte compte = compteService.getCompte(formActiverPlan.getIdCompte()).get();
        Plan plan = planService.getPlan(formActiverPlan.getIdPlan()).get();
        String paiement = formActiverPlan.getPaiement();

        if (paiement.equals("argent"))
            return activerPlan(new ActivationPlanPayant(activerPlanService), plan, compte);

        return "{\"resultat\":false}";
    }

    public String activerPlan(ActivationPlan activationPlan, Plan plan, Compte compte) {
        if (activationPlan.activer(plan, compte)) {
            compte.setPoints(compte.getPoints() + plan.getPoints());
            compteService.update(compte);
            return "{\"resultat\":true}";
        }

        return "{\"resultat\":false}";
    }

}
