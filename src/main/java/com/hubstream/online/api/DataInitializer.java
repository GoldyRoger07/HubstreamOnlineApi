package com.hubstream.online.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.Plan;
import com.hubstream.online.api.model.PlanFactory;
import com.hubstream.online.api.service.CompteService;
import com.hubstream.online.api.service.PlanService;
import com.hubstream.online.api.service.WinnerService;

@Component
public class DataInitializer implements ApplicationRunner {

    CompteService compteService;

    WinnerService winnerService;

    PlanService planService;



    @Autowired
    public DataInitializer(CompteService compteService, WinnerService winnerService,PlanService planService) {
        this.compteService = compteService;
        this.winnerService = winnerService;
        this.planService = planService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // compteService.updateComptePoints();
        // winnerService.createLastWinner();
        ajouterCompteParDefaut("Gouraige", "Francesse", "raiki",
        "2000","admin");
        ajouterCompteParDefaut("Latouche", "Gerson", "sonix", "0101","admin");

        ajouterCompteParDefaut("nom", "prenom", "shanks", "9292","user");
        ajouterCompteParDefaut("nom", "prenom", "kaido", "9292","user");
        ajouterCompteParDefaut("nom", "prenom", "luffy", "9292","user");
        ajouterCompteParDefaut("nom", "prenom", "zoro", "9292","user");
       
        ajouterPlansParDefaut();

    }

    public void ajouterCompteParDefaut(String nom, String prenom, String username,String codePin,String role) {
        Compte compte = new Compte();

        compte.setNom(nom);
        compte.setPrenom(prenom);
        compte.setUsername(username);
        compte.setCodePin(codePin);
        compte.setRole(role);
        // compte.setDateEnregistrement(LocalDate.now());

        if (!compteService.getCompteByUsername(username).isPresent()) {
            compteService.save(compte);
        }

    }

    public void ajouterPlansParDefaut(){
       List<Plan> plans =  new ArrayList<>();

       plans.add(PlanFactory.getPlanByType("All",1,"jours"));
       plans.add(PlanFactory.getPlanByType("All",3,"jours"));
       plans.add(PlanFactory.getPlanByType("All",7,"jours"));
       plans.add(PlanFactory.getPlanByType("All",30,"jours"));

       plans.get(0).setPrix(50);
       plans.get(0).setPoints(5);

       plans.get(1).setPrix(125);
       plans.get(1).setPoints(15);

       plans.get(2).setPrix(290);
       plans.get(2).setPoints(35);

       plans.get(3).setPrix(1000);
       plans.get(3).setPoints(150);

       if(planService.getPlans().size() == 0){
            plans.forEach(plan->{
                planService.save(plan);
            });
       }
            
       

    }

}
/*
 * 
        Film,Serie ou Animes
        Plan 1 Jour 50 HTG 
        Plan 3 Jours 125 HTG
        Plan 7 Jours 290 HTG
        Plan 30 Jours 1000 HTG

        Film,Serie ou Animes
        Plan 1 Jour 65 HTG 
        Plan 3 Jours 160 HTG
        Plan 7 Jours 380 HTG
        Plan 30 Jours 1495 HTG





 */