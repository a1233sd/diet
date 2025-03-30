package com.example.test.dbase;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @Column(name = "goal")
    @Enumerated (EnumType.STRING)
    private Goal goal;

    @Column(name = "daily_calories")
    private double dailyCalories;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;

    @PrePersist
    public void calculateDailyCalories() {
        // Применение формулы Харриса-Бенедикта для расчета дневной нормы калорий
        double bmr = 10 * weight + 6.25 * height - 5 * age + 5; // для мужчин
        if (goal == Goal.LOSE_WEIGHT) {
            dailyCalories = bmr - 500; // дефицит калорий для похудения
        } else if (goal == Goal.GAIN_WEIGHT) {
            dailyCalories = bmr + 500; // избыток калорий для набора массы
        } else {
            dailyCalories = bmr; // для поддержания массы
        }
    }


}
