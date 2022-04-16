package com.bee.store.controllers;

import com.bee.store.dtos.CatalogProductDto;
import com.bee.store.entities.CatalogProduct;
import com.bee.store.services.CatalogProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {

    @Autowired
    private CatalogProductService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<CatalogProductDto>> getProducts() {
        List<CatalogProduct> products = service.findAll();

        List<CatalogProductDto> dtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);

    }

    private CatalogProductDto convertToDto(CatalogProduct product) {
        CatalogProductDto dto = modelMapper.map(product, CatalogProductDto.class);
        dto.setTitle(product.getProduct().getTitle());
        dto.setPrice(service.calculateProductPrice(product));
        dto.setImage(product.getProduct().getImage());
        return dto;
    }
}
