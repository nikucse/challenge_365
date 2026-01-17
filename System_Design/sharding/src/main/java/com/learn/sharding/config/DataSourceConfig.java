package com.learn.sharding.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Defines ALL physical databases used by the system.
 *
 * IMPORTANT:
 * - Each DataSource represents ONE database
// * - NO shard routing logic here
// * - NO EntityManager here
 *
 * This class is pure infrastructure wiring.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Shard 1 - PRIMARY
     * Schools with schoolCode starting A-M
     * Used for ALL write operations
     */
    @Bean
    public DataSource shard1PrimaryDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5433/school")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Shard 1 - REPLICA
     * Read-only copy of shard1-primary
     */
    @Bean
    public DataSource shard1ReplicaDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5434/school")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Shard 2 - PRIMARY
     * Schools with schoolCode starting N-Z
     */
    @Bean
    public DataSource shard2PrimaryDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5435/school")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Shard 2 - REPLICA
     * Read-only copy of shard2-primary
     */
    @Bean
    public DataSource shard2ReplicaDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5436/school")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}