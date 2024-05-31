package com.hubstream.online.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.Compte;
import com.hubstream.online.api.model.Winner;
import com.hubstream.online.api.repository.WinnerRepository;

@Service
public class WinnerService {

    @Autowired
    WinnerRepository winnerRepository;

    @Autowired
    CompteService compteService;

    public Optional<Winner> getWinner(int id) {
        return winnerRepository.findById(id);
    }

    public List<Winner> getWinners() {
        return winnerRepository.findAll();
    }

    public Winner save(Winner winner) {
        return winnerRepository.save(winner);
    }

    public void delete(int id) {
        winnerRepository.deleteById(id);
    }

    public void deleteAll() {
        winnerRepository.deleteAll();
    }

    public void setLastWinnerToFalse() {
        for (Winner w : getWinners()) {
            w.setLastWinner(false);
            save(w);
        }
    }

    public Winner getLastWinner() {
        for (Winner w : getWinners()) {
            if (w.isLastWinner())
                return w;
        }

        return null;
    }

    public Winner getWinnerByIdCompte(String id) {
        for (Winner w : getWinners()) {
            if (w.getCompte().getIdCompte().equals(id))
                return w;
        }
        return null;
    }

    public void createLastWinner() {
        List<Compte> userComptes = compteService.getComptesByRole("user");

        List<Compte> top5BestComptes = compteService.getTop5BestComptes(userComptes);

        Compte winnerCompte = top5BestComptes.get(0);

        Winner testWinner = getWinnerByIdCompte(winnerCompte.getIdCompte());

        setLastWinnerToFalse();

        if (testWinner != null) {
            testWinner.setDateWin(LocalDate.now());
            testWinner.setTime(testWinner.getTime() + 1);
            testWinner.setLastWinner(true);
            save(testWinner);
        } else {
            Winner winner = new Winner();

            winner.setCompte(winnerCompte);

            winner.setTime(1);

            winner.setDateWin(LocalDate.now());

            winner.setLastWinner(true);

            save(winner);
        }

    }
}
