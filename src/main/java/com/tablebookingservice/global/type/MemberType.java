package com.tablebookingservice.global.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER");

    private final String description;
}
