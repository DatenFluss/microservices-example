package com.example.common.messaging;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueueConstants {
    public static final String PERSONAL_DATA_QUEUE = "personal.data.queue";
    public static final String CONTACT_DATA_QUEUE = "contact.data.queue";
    public static final String FINANCIAL_DATA_QUEUE = "financial.data.queue";
    public static final String STORAGE_QUEUE = "storage.queue";
    
    // Dead Letter Queues
    public static final String DLQ_PREFIX = "DLQ.";
    public static final String PERSONAL_DATA_DLQ = DLQ_PREFIX + PERSONAL_DATA_QUEUE;
    public static final String CONTACT_DATA_DLQ = DLQ_PREFIX + CONTACT_DATA_QUEUE;
    public static final String FINANCIAL_DATA_DLQ = DLQ_PREFIX + FINANCIAL_DATA_QUEUE;
    public static final String STORAGE_DLQ = DLQ_PREFIX + STORAGE_QUEUE;
} 