package com.johanwork.service;

import com.johanwork.client.MovieClient;
import com.johanwork.dto.CustomerDto;
import com.johanwork.dto.GenreUpdateRequest;
import com.johanwork.dto.MovieDto;
import com.johanwork.entity.Customer;
import com.johanwork.exception.CustomerNotFoundException;
import com.johanwork.mapper.EntityDtoMapper;
import com.johanwork.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MovieClient movieClient;

    public CustomerDto getCustomer(Long id){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        List<MovieDto> movies = this.movieClient.getMovies(customer.getFavoriteGenre());
        return EntityDtoMapper.toDto(customer,movies);
    }

    public void updateCustomerGenre(Long id, GenreUpdateRequest request){
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setFavoriteGenre(request.favoriteGenre());
        this.customerRepository.save(customer);
    }

}
