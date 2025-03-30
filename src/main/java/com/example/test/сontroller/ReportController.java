package com.example.test.сontroller;

import com.example.test.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Получение отчета по дневной норме калорий пользователя за конкретную дату
    @GetMapping("/daily/{userId}/{date}")
    public ResponseEntity<?> getDailyCaloriesReport(@PathVariable Long userId, @PathVariable String date) {
        if (userId == null) {
            return new ResponseEntity<>("User ID is required.", HttpStatus.BAD_REQUEST);
        }
        if (date == null || date.trim().isEmpty()) {
            return new ResponseEntity<>("Date is required.", HttpStatus.BAD_REQUEST);
        }
        try {
            LocalDate localDate = LocalDate.parse(date);
            int dailyCalories = reportService.getDailyCaloriesReport(userId, localDate);
            return new ResponseEntity<>(dailyCalories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Проверка, уложился ли пользователь в свою дневную норму калорий
    @GetMapping("/check/{userId}/{date}")
    public ResponseEntity<?> checkCalorieLimit(@PathVariable Long userId, @PathVariable String date) {
        if (userId == null) {
            return new ResponseEntity<>("User ID is required.", HttpStatus.BAD_REQUEST);
        }
        if (date == null || date.trim().isEmpty()) {
            return new ResponseEntity<>("Date is required.", HttpStatus.BAD_REQUEST);
        }
        try {
            LocalDate localDate = LocalDate.parse(date);
            boolean isUnderLimit = reportService.isUserUnderCalorieLimit(userId, localDate);
            return new ResponseEntity<>(isUnderLimit, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Получение истории питания пользователя
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getMealHistory(@PathVariable Long userId) {
        if (userId == null) {
            return new ResponseEntity<>("User ID is required.", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(reportService.getMealHistory(userId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Invalid request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
