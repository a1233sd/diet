package com.example.test.service;

import com.example.test.dbase.Meal;
import com.example.test.dbase.User;
import com.example.test.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserService userService;

    public int getDailyCaloriesReport(User user, LocalDate date) {
        List<Meal> meals = mealRepository.findByUserAndDate(user, date);
        return meals.stream().mapToInt(Meal::getTotalCalories).sum();
    }

    public int getDailyCaloriesReport(Long userId, LocalDate date) {
        User user = userService.getUserById(userId);
        return getDailyCaloriesReport(user, date);
    }

    public boolean isUserUnderCalorieLimit(Long userId, LocalDate date) {
        User user = userService.getUserById(userId);
        int dailyCaloriesConsumed = getDailyCaloriesReport(user, date);
        return dailyCaloriesConsumed <= user.getDailyCalories();
    }
    public List<Meal> getMealHistory(Long userId) {
        User user = userService.getUserById(userId);
        return mealRepository.findByUser(user);
    }


}
