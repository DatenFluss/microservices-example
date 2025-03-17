package com.example.client.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(jmsTemplate);
    }

    @Test
    void processClientData_Success() {
        String clientId = "test-client-id";
        ClientData expectedData = ClientData.builder()
            .clientId(clientId)
            .build();
        
        clientService.processClientData(clientId);
        
        verify(jmsTemplate).convertAndSend(
            QueueConstants.PERSONAL_DATA_QUEUE,
            expectedData
        );
    }

    @Test
    void processClientData_JmsError() {
        String clientId = "test-client-id";
        ClientData expectedData = ClientData.builder()
            .clientId(clientId)
            .build();

        doThrow(new RuntimeException("JMS Error"))
            .when(jmsTemplate)
            .convertAndSend(
                QueueConstants.PERSONAL_DATA_QUEUE,
                expectedData
            );
        
        assertThrows(RuntimeException.class, () -> 
            clientService.processClientData(clientId)
        );
    }
} 