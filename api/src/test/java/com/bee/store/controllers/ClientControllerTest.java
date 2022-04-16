package com.bee.store.controllers;

import com.bee.store.services.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

class ClientControllerTest {
    @Mock
    ClientService clientService;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    ClientController clientController;

    @Test
    void givenValidCliente_whenOk() {

    }

}