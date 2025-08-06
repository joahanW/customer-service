package com.johanwork.controller;

import com.johanwork.dto.CustomerDto;
import com.johanwork.dto.GenreUpdateRequest;
import com.johanwork.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id){
        CustomerDto customer = this.customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @PatchMapping("/{id}/genre")
    public ResponseEntity<Void> updateGenre(@PathVariable Long id, @RequestBody GenreUpdateRequest request){
        this.customerService.updateCustomerGenre(id, request);
        return ResponseEntity.noContent().build(); // 204
    }

}
