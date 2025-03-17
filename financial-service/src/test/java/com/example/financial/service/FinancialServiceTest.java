package com.example.financial.service;

import com.example.common.messaging.QueueConstants;
import com.example.common.model.ClientData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialServiceTest {

    @Mock
    private JmsTemplate jmsTemplate;

    private FinancialService financialService;

    @BeforeEach
    void setUp() {
        financialService = new FinancialService(jmsTemplate);
    }

    @Test
    void processFinancialData_Success() {
        ClientData clientData = ClientData.builder()
                .clientId("test-client-id")
                .firstName("John")
                .lastName("Doe")
                .contactList(List.of("+1234567890"))
                .build();

        financialService.processFinancialData(clientData);

        verify(jmsTemplate).convertAndSend(
            eq(QueueConstants.STORAGE_QUEUE),
            (Object) argThat(arg -> {
                ClientData data = (ClientData) arg;
                return data.getClientId().equals(clientData.getClientId())
                    && data.getCardNumbers() != null
                    && data.getCardNumbers().size() == 3;
            })
        );
    }

    @Test
    void processFinancialData_JmsError() {
        ClientData clientData = ClientData.builder()
                .clientId("test-client-id")
                .firstName("John")
                .lastName("Doe")
                .contactList(List.of("+1234567890"))
                .build();

        doThrow(new RuntimeException("JMS Error"))
            .when(jmsTemplate)
            .convertAndSend(
                eq(QueueConstants.STORAGE_QUEUE),
                (Object) argThat(arg -> arg instanceof ClientData)
            );

        try {
            financialService.processFinancialData(clientData);
        } catch (RuntimeException e) {
            verify(jmsTemplate).convertAndSend(
                eq(QueueConstants.STORAGE_QUEUE),
                (Object) argThat(arg -> arg instanceof ClientData)
            );
        }
    }
} 