package com.bee.store.services;

import com.bee.store.entities.Client;
import com.bee.store.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> optionalCliente = clientRepository.findByUsername(username);
        if (optionalCliente.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User not found with username: %s", username));
        }
        Client client = optionalCliente.get();

        return new User(client.getUsername(), client.getPassword(), new ArrayList<>());
    }
}
