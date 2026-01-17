package com.learn.sharding.shard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Central component responsible for:
 * - Shard selection
 * - Primary vs Replica routing
 *
 * This class contains ALL sharding rules.
 */
@Component
public class ShardRouter {

    private final Map<String, EntityManagerFactory> emfMap;

    public ShardRouter(Map<String, EntityManagerFactory> emfMap) {
        this.emfMap = emfMap;
    }

    /**
     * Returns an EntityManager bound to the correct shard.
     *
     * @param writeOperation
     *        true  -> PRIMARY
     *        false -> REPLICA
     */
    public EntityManager getEntityManager(boolean writeOperation) {

        String schoolCode = ShardContext.getSchoolCode();
        if (schoolCode == null) {
            throw new IllegalStateException("schoolCode not set in ShardContext");
        }

        // Decide shard based on schoolCode
        String shard = resolveShard(schoolCode);

        // Decide primary vs replica
        ShardType shardType = writeOperation
                ? ShardType.PRIMARY
                : ShardType.REPLICA;

        // Persist decision in context (useful for debugging)
        ShardContext.setShardType(shardType);

        String emfKey = shard + "_" + shardType.name().toLowerCase();

        EntityManagerFactory emf = emfMap.get(emfKey);
        if (emf == null) {
            throw new IllegalStateException(
                    "No EntityManagerFactory found for key: " + emfKey
            );
        }

        return emf.createEntityManager();
    }

    /**
     * SHARDING RULE:
     * - A-M -> shard1
     * - N-Z -> shard2
     */
    private String resolveShard(String schoolCode) {
        char firstChar = Character.toUpperCase(schoolCode.charAt(0));
        return (firstChar >= 'A' && firstChar <= 'M')
                ? "shard1"
                : "shard2";
    }
}
