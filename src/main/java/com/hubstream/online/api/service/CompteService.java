package com.hubstream.online.api.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.repository.CompteRepository;

@Service
public class CompteService {
    @Autowired
    CompteRepository compteRepository;

    public Optional<Compte> getCompte(String idCompte) {
        return compteRepository.findById(idCompte);
    }

    public Optional<Compte> getCompteByUsername(String username) {
        return compteRepository.findByUsername(username);
    }

    public List<Compte> getComptes() { 
        return compteRepository.findAll();
    }

    public Compte save(Compte compte) {
        if (compte.getRole() != null && !compte.getRole().equals("admin"))
            compte.setRole("user");
        if (compte.getIdCompte() != null)
            return compteRepository.save(compte);

        return compteRepository.save(getCompteWithId(compte));
    }

    public Compte update(Compte compte) {
        return compteRepository.save(compte);
    }

    public void delete(String idCompte) {
        compteRepository.deleteById(idCompte);
    }

    public void deleteAll() {
        compteRepository.deleteAll();
    }

    public Compte getCompteWithId(Compte compte) {
        String nom = compte.getNom();
        String prenom = compte.getPrenom();
        compte.setIdCompte(generatedID(nom, prenom));
        return compte;
    }

    public String generatedID(String nom, String prenom) {
        String prefix = nom.substring(0, 1) + prenom.substring(0, 1);
        return prefix + getRandomNumber();
    }

    public int getRandomNumber() {
        Random random = new Random();
        boolean test = true;
        int temp = 0;

        while (test) {
            temp = random.nextInt(1000000);
            if (temp > 99999)
                test = false;
        }
        return temp;
    }

    public List<Compte> getSafeComptes() {
        List<Compte> safeComptes = new ArrayList<>();

        for (Compte c : getComptes()) {
            c.setCodePin(""); 
           
            safeComptes.add(c);
        }

        return safeComptes;
    }

    public Compte getSafeCompte(String id) {
        Compte safeCompte = getCompte(id).isPresent() ? getCompte(id).get() : null;
        if (safeCompte != null) 
            safeCompte.setCodePin("");
           
        
        return safeCompte;
    }

    public boolean testUsername(String username) {
        username = username.replaceAll("\\s+", "").toLowerCase();
        for (Compte c : getComptes()) {
            if (c.getUsername().equals(username))
                return false;
        }

        return true;
    }

    public boolean login(Compte compte) {

        for (Compte c : getComptes()) {
            if (c.getUsername().equals(compte.getUsername()) &&
                    c.getCodePin().equals(compte.getCodePin()) && !c.isConnecter()) {
                c.setConnecter(true);
                update(c);
                return true;
            }

        }

        return false;
    }

    public boolean inscription(Compte compte) {
        compte = getCleanCompte(compte);
        compte.setRole("role");
        compte.setConnecter(false);
        if (testUsername(compte.getUsername())) {
            save(compte);
            return true;
        }

        return false;
    }

    public Compte getCleanCompte(Compte compte) {
        compte.setNom(compte.getNom().replaceAll("\\s+", ""));
        compte.setPrenom(compte.getPrenom().replaceAll("\\s+", ""));
        compte.setUsername(compte.getUsername().toLowerCase().replaceAll("\\s+", ""));

        return compte;
    }

    public boolean deconnection(String idCompte) {
        Compte compte = getCompte(idCompte).isPresent() ? getCompte(idCompte).get() : null;

        if (compte != null) {
            compte.setConnecter(false);
            update(compte);
            return true;
        }

        return false;
    }

   

    public List<Compte> getTop5BestComptes(List<Compte> comptes) {
        Collections.sort(comptes, Comparator.comparingInt(Compte::getPoints).reversed());
        return comptes.subList(0, Math.min(5, comptes.size()));
    }

    public List<Compte> getComptesByRole(String role) {
        return getComptes().stream()
                .filter(compte -> compte.getRole().equals(role))
                .collect(Collectors.toList());
    }

    public void resetPoints() {
        for (Compte compte : getComptesByRole("user")) {
            compte.setPoints(0);
            update(compte);
        }
    }

}

/*
 * 
 * 
 * 'BB647911','BR663816','DJ377395','DL313809','DS159847','GF220766','GW391253',
 * 'JD969474','JS440718','JS477849','KJ741511','LG858889','LW548149','MM134145',
 * 'NK316408','NS741381','RC319459','RJ307723','RN657598','RZ701809','TL764972',
 * 'Tt392943','TW892744'
 * 
 * 
 */
