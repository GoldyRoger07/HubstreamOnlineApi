package com.hubstream.online.api.model;

public class PlanFactory {

    public static Plan getPlanTirage(String type, int nombreDeTemps, String temps) {
        Plan plan = getPlanByType(type, nombreDeTemps, temps);
        plan.setPoints(0);
        plan.setPrix(0);
        return plan;
    }

    public static Plan getPlanByType(String type, int nombreDeTemps, String temps) {
        Plan plan = getBasicPlan(nombreDeTemps, temps);
        plan.setType(type);
        plan.setTitre(getTitrePlan(plan));
        return plan;
    }

    public static String getTitrePlan(Plan plan) {
        return plan.getType() + "-" + plan.getDureeExtension().substring(0, 1) + plan.getDuree();
    }

    public static Plan getBasicPlan(int nombreDeTemps, String temps) {
        Plan plan = new Plan();
        plan.setDuree(nombreDeTemps);
        String dureeExtension = "";
        switch (temps) {
            case "minutes":
                if (nombreDeTemps > 1)
                    dureeExtension = "Minutes";
                else
                    dureeExtension = "Minute";

                break;

            case "heures":
                if (nombreDeTemps > 1)
                    dureeExtension = "Heures";
                else
                    dureeExtension = "Heure";

                break;

            case "jours":
                if (nombreDeTemps > 1)
                    dureeExtension = "Jours";
                else
                    dureeExtension = "Jour";

                break;

        }

        plan.setDureeExtension(dureeExtension);

        return plan;
    }
}
