package com.leonardosbarbosa.clientscrud.services;

import com.leonardosbarbosa.clientscrud.dto.ClientDTO;
import com.leonardosbarbosa.clientscrud.entities.Client;
import com.leonardosbarbosa.clientscrud.repositories.ClientRepository;
import com.leonardosbarbosa.clientscrud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllClientsPaged(PageRequest pageRequest) {
        Page<Client> clientsList = repository.findAll(pageRequest);
        return clientsList.map(entity -> new ClientDTO(entity));
    }

    public ClientDTO findClientById(Long id) {
        Optional<Client> client = repository.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException(String.format("Client with id %d not found", id)));
        return new ClientDTO(entity);
    }
}
