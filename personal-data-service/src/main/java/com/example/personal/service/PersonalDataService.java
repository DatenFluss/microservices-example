package com.example.personal.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonalDataService {
    
    private final JmsTemplate jmsTemplate;
    
    // Mock database
    private final Map<String, ClientData> mockDb = new ConcurrentHashMap<>();
    
    @JmsListener(destination = QueueConstants.PERSONAL_DATA_QUEUE)
    public void processPersonalData(ClientData clientData) {
        String clientId = clientData.getClientId();
        log.info("Processing personal data for client ID: {}", clientId);
        
        try {
            // Simulate external service call
            Thread.sleep(1000);
            
            // Mock data enrichment
            clientData.setFirstName("John");
            clientData.setLastName("Doe");
            clientData.setAddress("123 Main St, City");
            clientData.setEmail("john.doe@example.com");
            clientData.setPhone("+1234567890");
            
            mockDb.put(clientId, clientData);
            
            // Send to next service
            jmsTemplate.convertAndSend(QueueConstants.CONTACT_DATA_QUEUE, clientData);
            log.info("Personal data processed and sent to contact service for client ID: {}", clientId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Processing interrupted for client ID: {}", clientId);
            throw new RuntimeException("Processing interrupted", e);
        } catch (Exception e) {
            log.error("Error processing personal data for client ID: {}", clientId, e);
            throw new RuntimeException("Failed to process personal data", e);
        }
    }
} 