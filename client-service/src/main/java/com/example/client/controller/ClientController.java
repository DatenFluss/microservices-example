package com.example.client.controller;

import com.example.client.service.ClientService;
import com.example.common.model.ClientData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    
    private final ClientService clientService;
    
    @PostMapping("/{clientId}/process")
    public ResponseEntity<String> processClientData(@PathVariable String clientId) {
        clientService.processClientData(clientId);
        return ResponseEntity.accepted()
                .body("Client data processing initiated for client ID: " + clientId);
    }
} 