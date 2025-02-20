package com.techzen.exercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @GetMapping("/calculator")
    public ResponseEntity<String> calculator(
            @RequestParam(value = "firstNumber", defaultValue = "") String firstNumberStr,
            @RequestParam(value = "secondNumber", defaultValue = "") String secondNumberStr,
            @RequestParam(value = "operator", defaultValue = "") String operator) {

        if (firstNumberStr.isEmpty()) {
            return ResponseEntity.badRequest().body("First number is empty");
        } else if (secondNumberStr.isEmpty()) {
            return ResponseEntity.badRequest().body("Second number is empty");
        } else if (!isDouble(firstNumberStr)) {
            return ResponseEntity.badRequest().body("First number is not a number");
        } else if (!isDouble(secondNumberStr)) {
            return ResponseEntity.badRequest().body("Second number is not a number");
        }

        double firstNumber = Double.parseDouble(firstNumberStr);
        double secondNumber = Double.parseDouble(secondNumberStr);
        double result;

        switch (operator) {
            case "+" -> result = firstNumber + secondNumber;
            case "-" -> result = firstNumber - secondNumber;
            case "*" -> result = firstNumber * secondNumber;
            case "/" -> {
                if (secondNumber == 0) {
                    return ResponseEntity.badRequest().body("Division by zero");
                }
                result = firstNumber / secondNumber;
            }
            default -> {
                return ResponseEntity.badRequest().body("Invalid operator");
            }
        }
        return ResponseEntity.ok().body("Result: " + result);
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
