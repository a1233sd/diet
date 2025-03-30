package com.example.test.dbase;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dish")
@Data
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "proteins")
    private double proteins; // Белки в порции (г)

    @Column(name = "fats")
    private double fats; // Жиры в порции (г)

    @Column(name = "carbohydrates")
    private double carbohydrates; // Углеводы в порции (г)

    // Метод, который возвращает количество калорий на порцию
    public int getCaloriesPerPortion() {
        return calories != null ? calories : 0;
    }

}
