package com.example.expense.service.impl;

import com.example.expense.dto.ExpenseDto;
import com.example.expense.entity.Category;
import com.example.expense.entity.Expense;
import com.example.expense.exceptions.ResourceNotFoundException;
import com.example.expense.mapper.ExpenseMapper;
import com.example.expense.repository.CategoryRepository;
import com.example.expense.repository.ExpenseRepository;
import com.example.expense.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ExpenseServiceImpl implements ExpenseService {

    //inject ExpenseRepository using constructor based DI
    private ExpenseRepository expenseRepository;

    private CategoryRepository categoryRepository;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {

        //Convert ExpenseDto to Expense entity
        Expense expense = ExpenseMapper.mapToExpense(expenseDto);

        //save Expense entity object into database
        Expense savedExpense = expenseRepository.save(expense);

        //convert savedExpense entity into ExpenseDto
        return ExpenseMapper.mapToExpenseDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long expenseId) {
        //get Expense entity from the database using expenseId
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+expenseId));

        //convert Expense entity into ExpenseDto
        return ExpenseMapper.mapToExpenseDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map((expense) -> ExpenseMapper.mapToExpenseDto(expense))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+expenseId));

        //update expense ammount
        expense.setAmount(expenseDto.amount());
        //update expense date
        expense.setExpenseDate(expenseDto.expenseDate());
        //update category
        if(expenseDto.categoryDto() != null){
            //get the category entity by id
            Category category = categoryRepository.findById(expenseDto.categoryDto().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+ expenseDto.categoryDto().id()));
            expense.setCategory(category);
        }
        //update Expense entity
        Expense updatedExpense = expenseRepository.save(expense);
        //convert Expense entity into ExpenseDto
        return ExpenseMapper.mapToExpenseDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        // get the expense from the database by expenseId. if it is not exixts then throw the runtimeException
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+expenseId));

        expenseRepository.delete(expense);
    }


}
