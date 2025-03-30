package com.example.test.service;

import com.example.test.dbase.Meal;
import com.example.test.dbase.User;
import com.example.test.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReportService reportService;

    @Test
    void testGetDailyCaloriesReport() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setDailyCalories(2500.0);

        Meal meal1 = new Meal();
        meal1.setTotalCalories(350);
        Meal meal2 = new Meal();
        meal2.setTotalCalories(400);
        List<Meal> meals = Arrays.asList(meal1, meal2);

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUserAndDate(user, LocalDate.of(2025, 3, 25))).thenReturn(meals);

        int totalCalories = reportService.getDailyCaloriesReport(userId, LocalDate.of(2025, 3, 25));
        assertEquals(750, totalCalories);

        verify(userService, times(1)).getUserById(userId);
        verify(mealRepository, times(1)).findByUserAndDate(user, LocalDate.of(2025, 3, 25));
    }

    @Test
    void testIsUserUnderCalorieLimit() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setDailyCalories(2500.0);

        Meal meal = new Meal();
        meal.setTotalCalories(2000);
        List<Meal> meals = Collections.singletonList(meal);

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUserAndDate(user, LocalDate.of(2025, 3, 25))).thenReturn(meals);

        boolean underLimit = reportService.isUserUnderCalorieLimit(userId, LocalDate.of(2025, 3, 25));
        assertTrue(underLimit);

        // Если сумма калорий превышает дневную норму
        meal.setTotalCalories(3000);
        when(mealRepository.findByUserAndDate(user, LocalDate.of(2025, 3, 25))).thenReturn(Collections.singletonList(meal));
        boolean notUnderLimit = reportService.isUserUnderCalorieLimit(userId, LocalDate.of(2025, 3, 25));
        assertFalse(notUnderLimit);

        verify(userService, times(2)).getUserById(userId);
        verify(mealRepository, times(2)).findByUserAndDate(user, LocalDate.of(2025, 3, 25));
    }

    @Test
    void testGetMealHistory() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Meal meal = new Meal();
        meal.setId(1L);
        List<Meal> meals = Collections.singletonList(meal);

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUser(user)).thenReturn(meals);

        List<Meal> history = reportService.getMealHistory(userId);
        assertEquals(1, history.size());
        assertEquals(1L, history.get(0).getId());

        verify(userService, times(1)).getUserById(userId);
        verify(mealRepository, times(1)).findByUser(user);
    }
}
