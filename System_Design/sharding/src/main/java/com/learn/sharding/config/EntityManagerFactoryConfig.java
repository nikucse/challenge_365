package com.learn.sharding.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates ONE EntityManagerFactory per physical database.
 *
 * IMPORTANT DESIGN RULES:
 * - Each EMF is bound to exactly ONE DataSource
 * - No routing logic here
 * - No tenant logic here
 *
 * This ensures hard database isolation.
 */
@Configuration
public class EntityManagerFactoryConfig {

    /**
     * Central registry of all EntityManagerFactories.
     *
     * Key format:
     *   shardX_primary
     *   shardX_replica
     *
     * This map will be used by ShardRouter.
     */
    @Bean
    public Map<String, EntityManagerFactory> entityManagerFactoryMap(
            DataSource shard1PrimaryDataSource,
            DataSource shard1ReplicaDataSource,
            DataSource shard2PrimaryDataSource,
            DataSource shard2ReplicaDataSource) {

        Map<String, EntityManagerFactory> emfMap = new HashMap<>();

        emfMap.put(
                "shard1_primary",
                buildEntityManagerFactory(shard1PrimaryDataSource)
        );

        emfMap.put(
                "shard1_replica",
                buildEntityManagerFactory(shard1ReplicaDataSource)
        );

        emfMap.put(
                "shard2_primary",
                buildEntityManagerFactory(shard2PrimaryDataSource)
        );

        emfMap.put(
                "shard2_replica",
                buildEntityManagerFactory(shard2ReplicaDataSource)
        );

        return emfMap;
    }

    /**
     * Primary EntityManagerFactory to satisfy Spring Boot's default requirements.
     * We pick one as primary, but ShardRouter will still use the map for routing.
     */
    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(Map<String, EntityManagerFactory> emfMap) {
        return emfMap.get("shard1_primary");
    }

    /**
     * Builds a single EntityManagerFactory for ONE DataSource.
     *
     * This method is intentionally private and reusable
     * to avoid configuration drift between shards.
     */
    private EntityManagerFactory buildEntityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);

        // All shards share the SAME entity model
        emf.setPackagesToScan("com.learn.sharding.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false); // DDL is managed externally or via validate
        vendorAdapter.setShowSql(true);
        emf.setJpaVendorAdapter(vendorAdapter);

        // Explicit Hibernate properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none"); // Avoid validation on startup if DB is down
        properties.put("hibernate.format_sql", "true");
        
        emf.setJpaPropertyMap(properties);

        // Explicit initialization (important)
        emf.afterPropertiesSet();

        return emf.getObject();
    }
}
