package com.example.test.service;

import com.example.test.dbase.Dish;
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
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserService userService;

    @Mock
    private DishService dishService;

    @InjectMocks
    private MealService mealService;

    @Test
    void testAddMeal() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUserName("John Doe");

        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setCalories(150);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setCalories(200);

        Meal meal = new Meal();
        meal.setDate(LocalDate.of(2025, 3, 25));
        // Передаём только id – сервис должен подгрузить полные объекты
        meal.setDishes(Arrays.asList(new Dish() {{ setId(1L); }}, new Dish() {{ setId(2L); }}));

        when(userService.getUserById(userId)).thenReturn(user);
        when(dishService.getDishById(1L)).thenReturn(dish1);
        when(dishService.getDishById(2L)).thenReturn(dish2);
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Meal savedMeal = mealService.addMeal(userId, meal);
        assertNotNull(savedMeal);
        assertEquals(user, savedMeal.getUser());
        assertEquals(2, savedMeal.getDishes().size());
        // Если нужно проверить пересчёт калорий – это можно сделать дополнительно
        verify(userService, times(1)).getUserById(userId);
        verify(dishService, times(1)).getDishById(1L);
        verify(dishService, times(1)).getDishById(2L);
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void testGetMealsByUserId() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUserName("John Doe");

        Meal meal1 = new Meal();
        meal1.setId(1L);
        meal1.setUser(user);
        Meal meal2 = new Meal();
        meal2.setId(2L);
        meal2.setUser(user);
        List<Meal> mealList = Arrays.asList(meal1, meal2);

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUser(user)).thenReturn(mealList);

        List<Meal> result = mealService.getMealsByUserId(userId);
        assertEquals(2, result.size());
        verify(userService, times(1)).getUserById(userId);
        verify(mealRepository, times(1)).findByUser(user);
    }

    @Test
    void testGetMealByIdFound() {
        Meal meal = new Meal();
        meal.setId(1L);
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        Meal foundMeal = mealService.getMealById(1L);
        assertNotNull(foundMeal);
        assertEquals(1L, foundMeal.getId());
        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMealByIdNotFound() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> mealService.getMealById(1L));
        assertEquals("Meal not found", exception.getMessage());
        verify(mealRepository, times(1)).findById(1L);
    }
}
