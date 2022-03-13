package com.example.spring_data_intro_exercise.models;

import com.example.spring_data_intro_exercise.enums.AgeRestriction;
import com.example.spring_data_intro_exercise.enums.EditionType;

import java.math.BigDecimal;

public interface ReducedBook {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
