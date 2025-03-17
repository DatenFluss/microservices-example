package com.example.common.security;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class SecurityUtils {
    private static final String SID_HEADER = "X-Session-ID";
    private static final int MIN_SID_LENGTH = 32;
    
    public String extractSid(String sidHeader) {
        return StringUtils.hasText(sidHeader) ? sidHeader : null;
    }
    
    public boolean isValidSid(String sid) {
        return StringUtils.hasText(sid) && sid.length() >= MIN_SID_LENGTH;
    }
    
    public String getSidHeaderName() {
        return SID_HEADER;
    }
} 