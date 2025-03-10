package com.grochu.library;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("library.presence")
@Data
public class PresenceProps {
    private int elementsOnPage = 15;
}
