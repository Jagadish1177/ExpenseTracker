package com.example.expense.controller;

import com.example.expense.dto.ExpenseDto;
import com.example.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Expense Resource",
        description = "CRUD REST APIs for Expense Resource - Create Expense, Update Expense, Get Expense and Delete Expense"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    //inject the ExpenseService using constructor based DI
    private ExpenseService expenseService;

    @Operation(
            summary = "Create Expense REST API",
            description = "Create Expense REST API to save expense into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP STATUS 201 CREATED"
    )
    //Build createExpense REST API
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto savedExpense = expenseService.createExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Expense REST API",
            description = "Get Expense REST API to get expense from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 201 OK"
    )
    //Build getExpenseById REST API
    @GetMapping("{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long expenseId){
        ExpenseDto expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @Operation(
            summary = "Get All Expenses REST API",
            description = "Get All Expenses REST API to get expenses from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 201 OK"
    )
    //Build getAllExpenses REST API
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(){
        List<ExpenseDto> expenses = expenseService.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }

    @Operation(
            summary = "Update Expense REST API",
            description = "Update Expense REST API to update expense in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 201 OK"
    )
    //Build updateExpense REST API
    @PutMapping("{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long expenseId,@RequestBody ExpenseDto expenseDto){
        ExpenseDto updatedExpense = expenseService.updateExpense(expenseId, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    @Operation(
            summary = "Delete Expense REST API",
            description = "Delete Expense REST API to delete expense from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 201 OK"
    )
    //Build deleteExpense REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully");
    }
}
