package com.example.test.repository;

import com.example.test.dbase.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository  extends JpaRepository<Dish, Long> {
}
