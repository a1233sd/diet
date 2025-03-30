package com.example.test.service;

import com.example.test.dbase.Dish;
import com.example.test.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId).orElseThrow(() -> new RuntimeException("Dish not found"));
    }
}
