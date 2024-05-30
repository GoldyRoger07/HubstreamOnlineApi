package com.hubstream.online.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.service.CompteService;
import com.hubstream.online.api.service.WinnerService;

@Component
public class DataInitializer implements ApplicationRunner {

    CompteService compteService;

    WinnerService winnerService;

    @Autowired
    public DataInitializer(CompteService compteService, WinnerService winnerService) {
        this.compteService = compteService;
        this.winnerService = winnerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // compteService.updateComptePoints();
        // winnerService.createLastWinner();
        // ajouterCompteParDefaut("Gouraige", "Francesse", "raiki", "raiki2000",
        // "2000");
        // ajouterCompteParDefaut("Latouche", "Gerson", "sonix", "latou0504", "0101");

    }

    public void ajouterCompteParDefaut(String nom, String prenom, String username,String codePin) {
        Compte compte = new Compte();

        compte.setNom(nom);
        compte.setPrenom(prenom);
        compte.setUsername(username);
        compte.setCodePin(codePin);
        compte.setRole("admin");
        // compte.setDateEnregistrement(LocalDate.now());

        if (!compteService.getCompteByUsername(username).isPresent()) {
            compteService.update(compte);
        }

    }

}
