package com.hubstream.online.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.FormModifierPassword;
import com.hubstream.online.api.model.Winner;
import com.hubstream.online.api.service.ActiverPlanService;
import com.hubstream.online.api.service.CompteService;
import com.hubstream.online.api.service.MainService;
import com.hubstream.online.api.service.WinnerService;

@RestController
@RequestMapping("/api.online.hubstream.com")
public class CompteController {

    @Autowired
    CompteService compteService;

    @Autowired
    WinnerService winnerService;

    @Autowired
    ActiverPlanService activerPlanService;

    @GetMapping("/comptes")
    public List<Compte> getAllComptes() {
        return compteService.getSafeComptes();
    }

    @GetMapping("/compte/{id}")
    public Compte getCompte(@PathVariable String id) {

        return compteService.getSafeCompte(id);
    }

    @PostMapping("/compte")
    public String addCompte(@RequestBody Compte compte) {
        compteService.save(compte);
        return "{\"status\":\"reussi\"}";
    }

    @PutMapping("/compte")
    public String updateCompte(@RequestBody Compte compte) {
        compteService.update(compte);
        return "{\"status\":\"reussi\"}";
    }

    @DeleteMapping("/compte/{id}")
    public String deleteCompte(@PathVariable String id) {
        if (compteService.getCompte(id).isPresent()) {
            compteService.delete(id);
            return "{\"status\":\"reussi\"}";
        }

        return "{\"status\":\"echec\"}";

    }

    @GetMapping("/compte/test-username/{username}")
    public String testUsername(@PathVariable String username) {
        boolean testValue = compteService.testUsername(username);
        String message = testValue ? "Test Reussi" : "Ce username existe deja";

        String status = testValue ? "success" : "error";

        return "{\"status\":\"" + status + "\",\"message\":\"" + message + "\"}";
    }

    @GetMapping("/compte/deconnexion/{idCompte}")
    public String deconnexion(@PathVariable String idCompte) {
        boolean testValue = compteService.deconnection(idCompte);
        String message = testValue ? "Deconnexion effectuer avec succes"
                : "Impossible d'effectuer cette operation,essayer plus tard";

        String status = testValue ? "success" : "error";

        return "{\"status\":\"" + status + "\",\"message\":\"" + message + "\"}";

    }

    @PostMapping("/compte/login")
    public String login(@RequestBody Compte compte) {

        compte.setUsername(compte.getUsername().replaceAll("\\s+", ""));
        boolean testValue = compteService.login(compte);
        String message = testValue ? "login Reussi" : "Username,Password incorrecte ou ce compte est deja connecter";

        String status = testValue ? "success" : "error";

        String idCompte = testValue ? compteService.getCompteByUsername(compte.getUsername()).get().getIdCompte()
                : "none";

        return "{\"status\":\"" + status + "\",\"message\":\"" + message + "\",\"idCompte\":\"" + idCompte + "\"}";
    }

    @PostMapping("/compte/inscription")
    public String inscription(@RequestBody Compte compte) {

        boolean testValue = compteService.inscription(compte);
        String message = testValue ? "inscription Reussi" : "Ce username existe deja";

        String status = testValue ? "success" : "error";

        return "{\"inscription\":\"" + status + "\",\"message\":\"" + message + "\"}";
    }

    @GetMapping("/compte/{idCompte}/solde")
    public String getSolde(@PathVariable("idCompte") String idCompte) {
        Compte compteResult = compteService.getCompte(idCompte).get();

        return "{ \"username\":\"" + compteResult.getUsername() + "\",\"solde\":" + compteResult.getFormatSolde() + "}";
    }

    @PostMapping("/compte/password")
    public String updatePasswordCompte(@RequestBody FormModifierPassword formModifierPassword) {

        Compte compte = compteService.getCompte(formModifierPassword.getIdCompte()).get();

        if (compte.getCodePin().equals(formModifierPassword.getAncienPassword())) {
            compte.setCodePin(formModifierPassword.getNouveauPassword());
            compteService.update(compte);
            // compteService.sauvegardeCompteSql();
            return "{\"resultat\":true}";
        }

        return "{\"resultat\":false}";
    }

    @PostMapping("/compte/update")
    public String updateCompte2(@RequestBody Compte compteUpdate) {
        Compte compte = compteService.getCompte(compteUpdate.getIdCompte()).get();
        boolean testUsername = compteService.testUsername(compteUpdate.getUsername());

        if (compte.getUsername().equals(compteUpdate.getUsername())) {
            effectuerModification(compte, compteUpdate);
            return "{\"resultat\":true}";
        } else if (testUsername) {

            effectuerModification(compte, compteUpdate);
            return "{\"resultat\":true}";
        }

        return "{\"resultat\":false}";
    }

    public void effectuerModification(Compte compte, Compte compteUpdate) {
        compte.setNom(compteUpdate.getNom());
        compte.setPrenom(compteUpdate.getPrenom());
        compte.setUsername(compteUpdate.getUsername());
        compte.setTelephone(compteUpdate.getTelephone());

        compteService.update(compte);

    }

    @GetMapping("/comptes/top5")
    public List<Compte> getTop5Comptes() {
        List<Compte> comptes = compteService.getComptesByRole("user");
        return compteService.getTop5BestComptes(comptes);
    }

    @GetMapping("/comptes/top5/title")
    public String getTop5ComptesTitle() {
        String title = "Top 5 utilisateurs potentiel pour les cadeaux du mois "
                + MainService.getMois(LocalDate.now().getMonthValue());
        return "{\"title\":\"" + title + "\"}";
    }

    @GetMapping("/lastWinner/title")
    @ResponseBody
    public String getLastWinnerTitle() {
        Winner winner = winnerService.getLastWinner();
      
        String title = "Le Meilleur utilisateur du mois de mars";
        if (winner != null)
            title = "Le Meilleur utilisateur du mois " + MainService.getMois(winner.getDateWin().getMonthValue() - 1);
        return "{\"title\":\"" + title + "\"}";
    }

    @GetMapping("/lastWinner")
    public Winner getLastWinner() {
        return winnerService.getLastWinner();
    }

}
