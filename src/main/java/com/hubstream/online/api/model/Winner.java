package com.hubstream.online.api.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "winners")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_winner")
    private int idWinner;

    @Column(name="date_win")
    private LocalDate dateWin;

    @Column(name="last_winner")
    private boolean lastWinner;

    private int time;

    @OneToOne
    @JoinColumn(name = "id_compte")
    private Compte compte;
}
