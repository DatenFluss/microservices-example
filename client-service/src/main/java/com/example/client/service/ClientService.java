package com.example.client.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    
    private final JmsTemplate jmsTemplate;
    
    public void processClientData(String clientId) {
        log.info("Starting client data processing for client ID: {}", clientId);
        
        try {
            ClientData clientData = ClientData.builder()
                    .clientId(clientId)
                    .build();
            
            jmsTemplate.convertAndSend(QueueConstants.PERSONAL_DATA_QUEUE, clientData);
            log.info("Client data sent to personal data queue for processing");
        } catch (Exception e) {
            log.error("Error processing client data for client ID: {}", clientId, e);
            throw new RuntimeException("Failed to process client data", e);
        }
    }
} 