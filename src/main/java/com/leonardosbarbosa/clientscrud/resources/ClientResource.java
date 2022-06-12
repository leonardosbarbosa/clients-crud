package com.leonardosbarbosa.clientscrud.resources;

import com.leonardosbarbosa.clientscrud.dto.ClientDTO;
import com.leonardosbarbosa.clientscrud.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientResource {

    @Autowired
    ClientService service;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllClientsPaged(Pageable pageable) {
        Page<ClientDTO> clientsPage = service.findAllClientsPaged(pageable);
        return ResponseEntity.ok().body(clientsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        ClientDTO client = service.findClientById(id);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insertNewClient(@RequestBody ClientDTO dto) {
        dto = service.insertNewClient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO dto) {
        dto = service.updateClient(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        service.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
