package com.bee.store.services;

import com.bee.store.dtos.CreateCatalogDto;
import com.bee.store.entities.Catalog;
import com.bee.store.entities.Client;
import com.bee.store.exceptions.NotFoundException;
import com.bee.store.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CatalogService {
    private final CatalogRepository repository;

    private final ClientService clientService;

    public CatalogService(CatalogRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    public List<Catalog> findAll(String username) {
        Client seller = clientService.findByUsername(username);
        return repository.findBySeller(seller);
    }

    public Catalog findCatalogByUsernameAndId(String username, Long catalogId) {
        Client seller = clientService.findByUsername(username);
        Optional<Catalog> optional = repository.findByIdAndSeller(catalogId, seller);
        if (optional.isEmpty()) {
            throw new NotFoundException("Catalog not found");
        }
        return optional.get();
    }

    public Catalog create(String username, CreateCatalogDto dto) {
        Client seller = clientService.findByUsername(username);
        Catalog catalog = new Catalog();
        catalog.setFee(dto.getFee());
        catalog.setTitle(dto.getTitle());
        catalog.setCreatedAt(new Date());
        catalog.setSeller(seller);
        return repository.save(catalog);
    }

    public void delete(String username, Long catalogId) {
        Catalog catalog = findCatalogByUsernameAndId(username, catalogId);
        repository.delete(catalog);
    }
}
