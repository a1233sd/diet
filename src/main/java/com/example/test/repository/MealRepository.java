package com.example.test.repository;

import com.example.test.dbase.Meal;
import com.example.test.dbase.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository  extends JpaRepository<Meal, Long> {
    List<Meal> findByUser(User user);
    List<Meal> findByUserAndDate(User user, LocalDate date);
}
