package com.example.contact.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(jmsTemplate);
    }

    @Test
    void processContactData_Success() {
        ClientData clientData = ClientData.builder()
            .clientId("test-client-id")
            .firstName("John")
            .lastName("Doe")
            .build();

        contactService.processContactData(clientData);

        ClientData expectedData = ClientData.builder()
            .clientId("test-client-id")
            .firstName("John")
            .lastName("Doe")
            .contactList(Arrays.asList(
                "+1234567891",
                "+1234567892",
                "+1234567893"
            ))
            .build();

        verify(jmsTemplate).convertAndSend(
            QueueConstants.FINANCIAL_DATA_QUEUE,
            expectedData
        );
    }

    @Test
    void processContactData_JmsError() {
        ClientData clientData = ClientData.builder()
            .clientId("test-client-id")
            .firstName("John")
            .lastName("Doe")
            .build();

        doThrow(new RuntimeException("JMS Error"))
            .when(jmsTemplate)
            .convertAndSend(
                QueueConstants.FINANCIAL_DATA_QUEUE,
                clientData
            );

        try {
            contactService.processContactData(clientData);
        } catch (RuntimeException e) {
            verify(jmsTemplate).convertAndSend(
                QueueConstants.FINANCIAL_DATA_QUEUE,
                clientData
            );
        }
    }
} 