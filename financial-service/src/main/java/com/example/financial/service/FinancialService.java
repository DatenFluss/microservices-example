package com.example.financial.service;

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
public class FinancialService {
    
    private final JmsTemplate jmsTemplate;
    
    // Mock database
    private final Map<String, ClientData> mockDb = new ConcurrentHashMap<>();
    
    @JmsListener(destination = QueueConstants.FINANCIAL_DATA_QUEUE)
    public void processFinancialData(ClientData clientData) {
        String clientId = clientData.getClientId();
        log.info("Processing financial data for client ID: {}", clientId);
        
        try {
            // Simulate external service call
            Thread.sleep(1000);
            
            // Mock data enrichment
            clientData.setCardNumbers(List.of(
                "4111111111111111",
                "5555555555554444",
                "3782822463100005"
            ));
            
            mockDb.put(clientId, clientData);
            
            // Send to next service
            jmsTemplate.convertAndSend(QueueConstants.STORAGE_QUEUE, clientData);
            log.info("Financial data processed and sent to storage service for client ID: {}", clientId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Processing interrupted for client ID: {}", clientId);
            throw new RuntimeException("Processing interrupted", e);
        } catch (Exception e) {
            log.error("Error processing financial data for client ID: {}", clientId, e);
            throw new RuntimeException("Failed to process financial data", e);
        }
    }
} 