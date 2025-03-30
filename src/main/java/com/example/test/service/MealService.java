package com.example.test.service;

import com.example.test.dbase.Dish;
import com.example.test.dbase.Meal;
import com.example.test.dbase.User;
import com.example.test.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DishService dishService;

    public Meal addMeal(Long userId, Meal meal) {
        User user = userService.getUserById(userId);
        meal.setUser(user);
        List<Dish> fullDishes = meal.getDishes().stream()
                .map(dish -> dishService.getDishById(dish.getId()))
                .collect(Collectors.toList());
        meal.setDishes(fullDishes);
        return mealRepository.save(meal);
    }


    public List<Meal> getMealsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return mealRepository.findByUser(user);
    }

    public Meal getMealById(Long mealId) {
        return mealRepository.findById(mealId).orElseThrow(() -> new RuntimeException("Meal not found"));
    }
}
