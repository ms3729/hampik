package com.hampik.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Party {

    @Column(name = "default_language")
    @Enumerated(EnumType.STRING)
    private Language defaultLanguage;

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(Language defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * Helper method to get the full name based on the party type
     * This should be implemented by concrete classes
     */
    public abstract String getFullName();
}
