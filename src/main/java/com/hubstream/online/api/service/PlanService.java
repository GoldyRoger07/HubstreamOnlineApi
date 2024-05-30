package com.hubstream.online.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.Plan;
import com.hubstream.online.api.model.PlanFactory;
import com.hubstream.online.api.repository.PlanRepository;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public Optional<Plan> getPlan(final int idPlan) {
        return planRepository.findById(idPlan);
    }

    public List<Plan> getPlans() {
        return planRepository.findAll();
    }

    public void deleteFilm(final int idPlan) {
        planRepository.deleteById(idPlan);
    }

    public Plan save(Plan plan) {
        Plan planSaved = planRepository.save(plan);

        return planSaved;
    }

    public List<Plan> getPlansByType(String type) {
        List<Plan> plans = getPlanPayant();
        List<Plan> finalList = new ArrayList<>();

        for (Plan p : plans) {
            if (p.getType().equals(type)) {
                finalList.add(p);
            }
        }

        return finalList;
    }

    public List<Plan> getPlanPayant() {
        List<Plan> finalList = new ArrayList<>();

        for (Plan p : getPlans()) {
            if (!(p.getPrix() == 0 || p.getPoints() == 0))
                finalList.add(p);
        }

        return finalList;
    }

    public List<Plan> getPlanTirages() {
        List<Plan> finalList = new ArrayList<>();

        for (Plan p : getPlans()) {
            if (p.getPrix() == 0 || p.getPoints() == 0)
                finalList.add(p);
        }

        return finalList;

    }

    public void saveDefaultPlansTirage() {
        List<Plan> plans = new ArrayList<>();

        plans.add(PlanFactory.getPlanTirage("Film", 20, "minutes"));
        plans.add(PlanFactory.getPlanTirage("Serie", 20, "minutes"));
        plans.add(PlanFactory.getPlanTirage("Anime", 20, "minutes"));

        plans.add(PlanFactory.getPlanTirage("Film", 1, "heures"));
        plans.add(PlanFactory.getPlanTirage("Serie", 1, "heures"));
        plans.add(PlanFactory.getPlanTirage("Anime", 1, "heures"));

        plans.add(PlanFactory.getPlanTirage("Film", 1, "jours"));
        plans.add(PlanFactory.getPlanTirage("Serie", 1, "jours"));
        plans.add(PlanFactory.getPlanTirage("Anime", 1, "jours"));

        plans.add(PlanFactory.getPlanTirage("Film", 7, "jours"));
        plans.add(PlanFactory.getPlanTirage("Serie", 7, "jours"));
        plans.add(PlanFactory.getPlanTirage("Anime", 7, "jours"));

        for (Plan p : plans) {
            save(p);
        }
    }

    public boolean hasPlansTirage() {
        for (Plan p : getPlans()) {
            if (p.getPrix() == 0 || p.getPoints() == 0)
                return true;
        }
        return false;
    }
}
