package com.bee.store.controllers;

import com.bee.store.dtos.CatalogProductDto;
import com.bee.store.dtos.ResponseProductDto;
import com.bee.store.entities.CatalogProduct;
import com.bee.store.entities.Product;
import com.bee.store.services.CatalogProductService;
import com.bee.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<ResponseProductDto>> getProducts() {
        List<Product> products = service.getAllProducts();

        List<ResponseProductDto> dtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);

    }

    private ResponseProductDto convertToDto(Product product) {
        return modelMapper.map(product, ResponseProductDto.class);
    }
}
