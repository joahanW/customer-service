package com.johanwork.mapper;

import com.johanwork.dto.CustomerDto;
import com.johanwork.dto.MovieDto;
import com.johanwork.entity.Customer;

import java.util.List;

public class EntityDtoMapper {

    public static CustomerDto toDto(Customer customer, List<MovieDto> movies){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getFavoriteGenre(),
                movies
        );
    }
}
