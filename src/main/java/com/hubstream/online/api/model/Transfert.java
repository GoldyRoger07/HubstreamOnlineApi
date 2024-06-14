package com.hubstream.online.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transferts")
public class Transfert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private int idTransaction;

    @Column(unique = true,name="code_transfert")
    private String codeTransfert;

    @ManyToOne
    // @JoinColumn(name = "id_source", unique = false)
    private Compte compteSource;

    @ManyToOne
    // @JoinColumn(name = "id_destinataire", unique = false)
    private Compte compteDestinataire;

    private double montant;

    @Column(name = "date_transfert")
    private LocalDateTime dateTransfert;

}
