package com.bee.store.controllers;

import com.bee.store.dtos.ClientDto;
import com.bee.store.dtos.CreateClientDto;
import com.bee.store.entities.Client;
import com.bee.store.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<List<ClientDto>> list() {
        List<Client> clients = service.findAll();

        List<ClientDto> dtos = clients.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDto> create(
            @RequestBody CreateClientDto clientDto
    ) {
        Client client = service.create(clientDto);
        return ResponseEntity.ok(convertToDto(client));
    }

    @GetMapping("/me")
    public ClientDto getById(Authentication authentication) {
        Client client = service.findByUsername(authentication.getName());
        return convertToDto(client);
    }

    private ClientDto convertToDto(Client client) {
        return modelMapper.map(client, ClientDto.class);
    }
}
