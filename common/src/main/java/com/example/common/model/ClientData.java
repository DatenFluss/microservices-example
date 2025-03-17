package com.example.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_data")
public class ClientData {
    @Id
    @NotNull
    private String clientId;
    
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private List<String> contactList;
    private List<String> cardNumbers;
} 