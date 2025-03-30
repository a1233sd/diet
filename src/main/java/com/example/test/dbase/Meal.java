package com.example.test.dbase;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meal")
@Data
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; // Дата приема пищи

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Пользователь, к которому относится прием пищи

//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(
            name = "meal_dish",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes; // Список блюд, входящих в прием пищи

    private int totalCalories; // Общая калорийность приема пищи

    @PrePersist
    @PreUpdate
    public void calculateTotalCalories() {
        // Суммируем калории всех блюд в приеме пищи
        totalCalories = dishes.stream().mapToInt(Dish::getCaloriesPerPortion).sum();
    }
}

