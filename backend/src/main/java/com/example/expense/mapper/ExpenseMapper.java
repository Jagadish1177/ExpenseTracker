package com.example.expense.mapper;

import com.example.expense.dto.CategoryDto;
import com.example.expense.dto.ExpenseDto;
import com.example.expense.entity.Category;
import com.example.expense.entity.Expense;

public class ExpenseMapper {

    // Map Expense entity to ExpenseDto
    public static ExpenseDto mapToExpenseDto(Expense expense){
        return new ExpenseDto(
                expense.getId(),
                expense.getAmount(),
                expense.getExpenseDate(),
                new CategoryDto(
                        expense.getCategory().getId(),
                        expense.getCategory().getName()
                )
        );
    }

    //Map ExpenseDto to Expense Entity
    public static Expense mapToExpense(ExpenseDto expenseDto){
        Category category = new Category();
        category.setId(expenseDto.categoryDto().id());

        return new Expense(
                expenseDto.id(),
                expenseDto.amount(),
                expenseDto.expenseDate(),
                category
                );
    }
}
