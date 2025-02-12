package com.system.person_api.dto;

import java.util.UUID;

public class MessageDTO {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public MessageDTO(UUID id) {
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
