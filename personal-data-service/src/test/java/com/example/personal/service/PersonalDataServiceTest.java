package com.example.personal.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalDataServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    private PersonalDataService personalDataService;

    @BeforeEach
    void setUp() {
        personalDataService = new PersonalDataService(jmsTemplate);
    }

    @Test
    void processPersonalData_Success() {
        ClientData clientData = ClientData.builder()
            .clientId("test-client-id")
            .build();

        personalDataService.processPersonalData(clientData);

        ClientData expectedData = ClientData.builder()
            .clientId("test-client-id")
            .firstName("John")
            .lastName("Doe")
            .address("123 Main St, City")
            .email("john.doe@example.com")
            .phone("+1234567890")
            .build();

        verify(jmsTemplate).convertAndSend(
            QueueConstants.CONTACT_DATA_QUEUE,
            expectedData
        );
    }

    @Test
    void processPersonalData_JmsError() {
        ClientData clientData = ClientData.builder()
            .clientId("test-client-id")
            .build();

        doThrow(new RuntimeException("JMS Error"))
            .when(jmsTemplate)
            .convertAndSend(eq(QueueConstants.CONTACT_DATA_QUEUE), any(ClientData.class));

        try {
            personalDataService.processPersonalData(clientData);
        } catch (RuntimeException e) {
            verify(jmsTemplate).convertAndSend(
                eq(QueueConstants.CONTACT_DATA_QUEUE),
                any(ClientData.class)
            );
        }
    }
} 