package com.bee.store.controllers;

import com.bee.store.dtos.*;
import com.bee.store.entities.Catalog;
import com.bee.store.entities.CatalogProduct;
import com.bee.store.services.CatalogProductService;
import com.bee.store.services.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {
    @Autowired
    private CatalogService service;

    @Autowired
    private CatalogProductService catalogProductService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<CatalogDto>> getCatalogs(Authentication authentication) {
        List<Catalog> catalogs = service.findAll(authentication.getName());

        List<CatalogDto> dtos = catalogs.stream().map(this::convertToCatalogDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("")
    public ResponseEntity<CatalogResponseDto> create(Authentication authentication, @RequestBody CreateCatalogDto dto) {
        Catalog catalog = service.create(authentication.getName(), dto);
        CatalogResponseDto responseDto = new CatalogResponseDto();
        responseDto.setId(catalog.getId());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{catalogId}")
    public ResponseEntity<Void> deleteCatalog(Authentication authentication, @PathVariable("catalogId") long catalogId) {
        service.delete(authentication.getName(), catalogId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<CatalogProductDto>> getProducts(Authentication authentication, @PathVariable("id") long id) {
        List<CatalogProduct> products = catalogProductService.findProductsByCatalog(authentication.getName(), id);

        List<CatalogProductDto> dtos = products.stream().map(this::convertToCatalogProduct).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);

    }

    @PostMapping("{catalogId}/products")
    public void productsAdd(Authentication authentication, @PathVariable("catalogId") long catalogId, @RequestBody List<ProductsDto> productsDtos) {
        catalogProductService.productsAdd(authentication.getName(), catalogId, productsDtos);
    }

    @DeleteMapping("{catalogId}/products/{productId}")
    public void deleteProduct(Authentication authentication, @PathVariable("catalogId") long catalogId, @PathVariable("productId") long productId) {
        catalogProductService.removeProduct(authentication.getName(), catalogId, productId);
    }

    private CatalogDto convertToCatalogDto(Catalog catalog) {
        return modelMapper.map(catalog, CatalogDto.class);
    }

    private CatalogProductDto convertToCatalogProduct(CatalogProduct product) {
        CatalogProductDto dto = modelMapper.map(product, CatalogProductDto.class);
        dto.setTitle(product.getProduct().getTitle());
        dto.setPrice(product.getProduct().getPrice());
        dto.setImage(product.getProduct().getImage());
        return dto;
    }
}
