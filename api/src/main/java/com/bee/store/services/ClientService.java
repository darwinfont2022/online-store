package com.bee.store.services;

import com.bee.store.dtos.CreateClientDto;
import com.bee.store.entities.Client;
import com.bee.store.exceptions.NotFoundException;
import com.bee.store.repositories.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client create(CreateClientDto dto) {
        Client client = Client.create(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getAddress(), dto.getCity(), passwordEncoder.encode(dto.getPassword()));
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new NotFoundException("Client not found");
        }
        return client.get();
    }

    public Client findByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isEmpty()) {
            throw new NotFoundException("Client not found");
        }
        return client.get();
    }
}
