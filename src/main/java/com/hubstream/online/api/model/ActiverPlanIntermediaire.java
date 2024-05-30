package com.hubstream.online.api.model;

import lombok.Data;

@Data
public class ActiverPlanIntermediaire {

    private ActiverPlan activerPlan;

    private String heureDebut;
    private String dateDebut;

    private String heureExpiration;
    private String dateExpiration;

    private String prixFormatter;
 
}
