package com.example.test.сontroller;

import com.example.test.dbase.Dish;
import com.example.test.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    // Создание нового блюда
    @PostMapping
    public ResponseEntity<?> createDish(@RequestBody Dish dish) {
        try {
            Dish createdDish = dishService.createDish(dish);
            return new ResponseEntity<>(createdDish, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ошибка при создании блюда: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<?> getDishById(@PathVariable Long dishId) {
        try {
            Dish dish = dishService.getDishById(dishId);
            return new ResponseEntity<>(dish, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Блюдо не найдено: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

