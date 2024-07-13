package com.hubstream.online.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.Notification;
import com.hubstream.online.api.model.Transfert;
import com.hubstream.online.api.service.AdminService;
import com.hubstream.online.api.service.CompteService;
import com.hubstream.online.api.service.NotificationService;
import com.hubstream.online.api.service.TransfertService;

@RestController
@RequestMapping("/api.online.hubstream.com")
public class TransfertController {

    @Autowired
    private TransfertService transfertService;

    @Autowired
    private CompteService compteService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/transfert")
    public String effectuertransfert(@RequestBody Transfert transfert) {
        Compte compteSource = compteService.getCompte(transfert.getCompteSource().getIdCompte()).get();
        Compte compteDestinataire = compteService.getCompte(transfert.getCompteDestinataire().getIdCompte()).get();

        System.out.println(compteSource.getUsername());
        System.out.println(compteDestinataire.getUsername());

        double montantTransfert = transfert.getMontant();
        String role = compteSource.getRole();
        double pourcentage = 1;
        boolean status = false;

        if (role.equals("admin")) {

           if(!compteDestinataire.getRole().equals("user") )
                    pourcentage = 1.2;
               
           

            compteDestinataire.effectuerDepot(montantTransfert * pourcentage);
            status = true;

        } else {

            if ( compteSource.testRetrait(montantTransfert)) {
                compteSource.effectuerRetrait(montantTransfert);
                compteDestinataire.effectuerDepot(montantTransfert);

                status = true;
            }

        }

        if (status) {
            transfert.setCompteSource(compteSource);
            transfert.setCompteDestinataire(compteDestinataire);

            effectuerTransfert(transfert);
            Notification notification = new Notification();

            notification.setCompteSource(compteSource);
            notification.setCompteDestinataire(compteDestinataire);
            notification.setEtat("nonVue");
            notification.setTexte(
                    compteSource.getUsername() + " vous a transféré " + formaterMontant(montantTransfert)
                            + " HTG");

            notificationService.save(notification);

            if (notificationService.getNotificationByEtat("vue").size() > 10)
                notificationService.deleteNotificationsVue();

            return "{\"resultat\":true}";
        }

        return "{\"resultat\":false}";

    }

   

    public void effectuerTransfert(Transfert transfert) {  
        transfert.setDateTransfert(LocalDateTime.now());
        transfert.setCodeTransfert(AdminService.generateRandomString(8));
        transfertService.save(transfert);
    }

    public String formaterMontant(double montant) {
        return (montant % 1 == 0) ? String.valueOf((int) montant) : String.valueOf(montant);
    }

}
