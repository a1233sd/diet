package com.example.test.service;

import com.example.test.dbase.Dish;
import com.example.test.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @Test
    void testCreateDish() {
        Dish dish = new Dish();
        dish.setDishName("Salad");
        dish.setCalories(150);

        when(dishRepository.save(dish)).thenReturn(dish);

        Dish createdDish = dishService.createDish(dish);
        assertNotNull(createdDish);
        assertEquals("Salad", createdDish.getDishName());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testGetDishByIdFound() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setDishName("Salad");
        dish.setCalories(150);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        Dish foundDish = dishService.getDishById(1L);
        assertNotNull(foundDish);
        assertEquals(1L, foundDish.getId());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDishByIdNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> dishService.getDishById(1L));
        assertEquals("Dish not found", exception.getMessage());
        verify(dishRepository, times(1)).findById(1L);
    }
}
