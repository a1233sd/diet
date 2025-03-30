package com.example.test.сontroller;

import com.example.test.dbase.Meal;
import com.example.test.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    // Добавление нового приема пищи
    @PostMapping("/users/{userId}")
    public ResponseEntity<Meal> addMeal(@PathVariable Long userId, @RequestBody Meal meal) {
        Meal createdMeal = mealService.addMeal(userId, meal);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    // Получение всех приемов пищи пользователя по ID
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Meal>> getMealsByUserId(@PathVariable Long userId) {
        List<Meal> meals = mealService.getMealsByUserId(userId);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    // Получение приема пищи по ID
    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long mealId) {
        Meal meal = mealService.getMealById(mealId);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }
}
