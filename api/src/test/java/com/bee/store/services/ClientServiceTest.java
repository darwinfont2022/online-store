package com.bee.store.services;

import com.bee.store.dtos.CreateClientDto;
import com.bee.store.entities.Client;
import com.bee.store.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class ClientServiceTest {
    @Mock
    private ClientRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ClientService service;

    private static Client mockClient() {
        Client client = new Client();
        client.setName("Juan");
        client.setEmail("test@gm.com");
        client.setAddress("Custom");
        client.setCity("Cusco");
        client.setPassword("secret");
        return client;
    }

    @BeforeEach
    void setUp() {
        openMocks(this);
        service = new ClientService(repository, passwordEncoder);
    }

    @Test
    void givenClientDto_thenCreate() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(mockClient());
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("secret_encoded");

        CreateClientDto dto = new CreateClientDto();
        dto.setName("Juan");
        dto.setEmail("test@gmail.com");
        dto.setAddress("Custom");
        dto.setCity("Cusco");
        dto.setUsername("test");
        dto.setPassword("secret");

        Client client = service.create(dto);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        assertEquals(client.getName(), "Juan");
    }

}