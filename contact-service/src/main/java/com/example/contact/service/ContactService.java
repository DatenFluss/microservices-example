package com.example.contact.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    
    private final JmsTemplate jmsTemplate;
    
    // Mock database
    private final Map<String, ClientData> mockDb = new ConcurrentHashMap<>();
    
    @JmsListener(destination = QueueConstants.CONTACT_DATA_QUEUE)
    public void processContactData(ClientData clientData) {
        String clientId = clientData.getClientId();
        log.info("Processing contact data for client ID: {}", clientId);
        
        try {
            // Simulate external service call
            Thread.sleep(1000);
            
            // Mock data enrichment
            clientData.setContactList(List.of(
                "+1234567891",
                "+1234567892",
                "+1234567893"
            ));
            
            mockDb.put(clientId, clientData);
            
            // Send to next service
            jmsTemplate.convertAndSend(QueueConstants.FINANCIAL_DATA_QUEUE, clientData);
            log.info("Contact data processed and sent to financial service for client ID: {}", clientId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Processing interrupted for client ID: {}", clientId);
            throw new RuntimeException("Processing interrupted", e);
        } catch (Exception e) {
            log.error("Error processing contact data for client ID: {}", clientId, e);
            throw new RuntimeException("Failed to process contact data", e);
        }
    }
} 