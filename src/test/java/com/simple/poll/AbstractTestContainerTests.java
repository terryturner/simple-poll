package com.simple.poll;

import java.io.IOException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(initializers  = {AbstractTestContainerTests.Initializer.class})
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTestContainerTests {
    
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.4")
            .withUsername("root")
            .withPassword("p@ssword")
            .withDatabaseName("test")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort())
            ;
    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:5.0.0")
            .withExposedPorts(6379)
            ;
    
    @TestConfiguration
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            final String jdbcUrl = String.format("%s&createDatabaseIfNotExist=true&currentSchema=public", postgreSQLContainer.getJdbcUrl());
            TestPropertyValues
                    .of(
                            "spring.datasource.url=" + jdbcUrl,
                            "spring.datasource.jdbc-url=" + jdbcUrl,
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                            "spring.redis.host=" + redisContainer.getHost(),
                            "spring.redis.port=" + redisContainer.getFirstMappedPort()
                            )
                    .applyTo(configurableApplicationContext.getEnvironment());
            
        }
    }
    
    protected byte[] fromFile(String path) throws IOException {
        return new ClassPathResource(path).getInputStream().readAllBytes();
    }
}
