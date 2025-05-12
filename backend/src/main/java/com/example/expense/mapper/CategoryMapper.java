package com.example.expense.mapper;

import com.example.expense.dto.CategoryDto;
import com.example.expense.entity.Category;

public class CategoryMapper {
    // map CategoryDto to Category entity
    public static Category mapToCategory(CategoryDto categoryDto){
        return new Category(
                categoryDto.id(),
                categoryDto.name()
        );
    }

    //map Category entity to CategoryDto
    public static CategoryDto mapToCategoryDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

}
