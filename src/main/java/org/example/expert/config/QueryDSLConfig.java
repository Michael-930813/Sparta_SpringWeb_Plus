package org.example.expert.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLConfig {
// - Properties
    @PersistenceContext
    private EntityManager em;

// - Methods
    @Bean
    public JPAQueryFactory jpaQueryFactor() { return new JPAQueryFactory(em); }
}
