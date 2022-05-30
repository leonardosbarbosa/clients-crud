package com.leonardosbarbosa.clientscrud.services;

import com.leonardosbarbosa.clientscrud.dto.ClientDTO;
import com.leonardosbarbosa.clientscrud.entities.Client;
import com.leonardosbarbosa.clientscrud.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllClientsPaged(PageRequest pageRequest) {
        Page<Client> clientsList = repository.findAll(pageRequest);
        return clientsList.map(entity -> new ClientDTO(entity));
    }
}
