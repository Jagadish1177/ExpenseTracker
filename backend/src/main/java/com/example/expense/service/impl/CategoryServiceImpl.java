package com.example.expense.service.impl;

import com.example.expense.dto.CategoryDto;
import com.example.expense.entity.Category;
import com.example.expense.exceptions.ResourceNotFoundException;
import com.example.expense.mapper.CategoryMapper;
import com.example.expense.repository.CategoryRepository;
import com.example.expense.repository.ExpenseRepository;
import com.example.expense.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ExpenseRepository expenseRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        //convert CategoryDto to Category entity
        Category category = CategoryMapper.mapToCategory(categoryDto);

        //save Category object into database - categories
        Category savedCategory = categoryRepository.save(category);

        //convert savedCategory to CategoryDto
        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+ categoryId));
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map((category) -> CategoryMapper.mapToCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {

    //Get Category entity from database by categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+ categoryId));

        //update the Category entity and save to database table
        category.setName(categoryDto.name());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        //check if a Category with given id exists in the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+ categoryId));

        expenseRepository.deleteByCategoryId(categoryId);
        categoryRepository.delete(category);
    }
}
