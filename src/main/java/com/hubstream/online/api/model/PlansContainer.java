package com.hubstream.online.api.model;

import java.util.List;

import lombok.Data;

@Data
public class PlansContainer {
    List<Plan> plansFilm;
    List<Plan> plansSerie;
    List<Plan> plansAnime;
    List<Plan> plansAll;
}
