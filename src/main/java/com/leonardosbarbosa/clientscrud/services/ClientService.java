package com.leonardosbarbosa.clientscrud.services;

import com.leonardosbarbosa.clientscrud.dto.ClientDTO;
import com.leonardosbarbosa.clientscrud.entities.Client;
import com.leonardosbarbosa.clientscrud.repositories.ClientRepository;
import com.leonardosbarbosa.clientscrud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {
        Optional<Client> client = repository.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException(String.format("Client with id %d not found", id)));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insertNewClient(ClientDTO dto) {
        Client entity = new Client(dto);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Client with id %d not found", id));
        }
    }

    public void deleteClientById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(String.format("Client with id %d not found", id));
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
