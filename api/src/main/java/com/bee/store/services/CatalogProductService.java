package com.bee.store.services;

import com.bee.store.dtos.ProductsDto;
import com.bee.store.entities.Catalog;
import com.bee.store.entities.CatalogProduct;
import com.bee.store.entities.Product;
import com.bee.store.exceptions.NotFoundException;
import com.bee.store.repositories.CatalogProductRepository;
import com.bee.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogProductService {
    private final CatalogProductRepository repository;

    private final CatalogService catalogService;

    private final ProductRepository productRepository;

    @Value("${app.fee-percentage}")
    private float feePercentage;

    public CatalogProductService(CatalogProductRepository catalogProductRepository, CatalogService catalogService, ProductRepository productRepository) {
        this.repository = catalogProductRepository;
        this.catalogService = catalogService;
        this.productRepository = productRepository;
    }

    public CatalogProduct findById(Long id) {
        Optional<CatalogProduct> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        return optional.get();
    }

    public List<CatalogProduct> findAll() {
        return repository.findAll();
    }

    public List<CatalogProduct> findProductsByCatalog(String username, Long catalogId) {
        Catalog catalog = catalogService.findCatalogByUsernameAndId(username, catalogId);
        return repository.findByCatalog(catalog);
    }

    @Transactional
    public void productsAdd(String username, long id, List<ProductsDto> productsDto) {
        Catalog catalog = catalogService.findCatalogByUsernameAndId(username, id);

        productsDto.forEach(productDto -> {
            CatalogProduct catalogProduct = new CatalogProduct();
            Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
            if (optionalProduct.isEmpty()) {
                throw new NotFoundException("Product not found");
            }
            catalogProduct.setProduct(optionalProduct.get());
            if (productDto.getFee() > 0) {
                catalogProduct.setPercentage(productDto.getFee());
            }
            catalogProduct.setCatalog(catalog);
            repository.save(catalogProduct);
        });
    }

    @Transactional
    public void removeProduct(String username, long catalogId, long productId) {
        catalogService.findCatalogByUsernameAndId(username, catalogId);
        repository.deleteByIdAndCatalog(productId, catalogId);
    }


    public float calculateProductPrice(CatalogProduct catalogProduct) {
        float basePrice = catalogProduct.getProduct().getPrice();
        float feeSeller = catalogProduct.getFee() / 100 * basePrice;
        float feeBee = basePrice * feePercentage / 100;
        return basePrice + feeBee + feeSeller;
    }
}
