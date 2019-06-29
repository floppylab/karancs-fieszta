package com.floppylab.karancsfieszta.configuration;

import com.floppylab.karancsfieszta.service.LoggedInUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    private final LoggedInUserService loggedInUserService;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(loggedInUserService.getLoggedInUserName());
    }
}
