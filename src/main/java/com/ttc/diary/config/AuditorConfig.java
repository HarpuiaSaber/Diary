package com.ttc.diary.config;

import com.ttc.diary.service.impl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class AuditorConfig {

    @Bean
    public AuditorAwareImpl auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}