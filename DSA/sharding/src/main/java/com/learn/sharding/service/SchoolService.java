package com.learn.sharding;

import com.learn.sharding.entity.School;
import com.learn.sharding.shard.ShardContext;
import com.learn.sharding.shard.ShardRouter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Service;

/**
 * Manages School (Tenant) creation.
 *
 * IMPORTANT:
 * - School creation is always a WRITE
 * - Goes to PRIMARY of the correct shard
 */
@Service
public class SchoolService {

    private final ShardRouter shardRouter;

    public SchoolService(ShardRouter shardRouter) {
        this.shardRouter = shardRouter;
    }

    public void createSchool(String schoolCode, String name) {

        // Manually set tenant context (bootstrap case)
        ShardContext.setSchoolCode(schoolCode);

        EntityManager em = shardRouter.getEntityManager(true);
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            School school = new School(schoolCode, name);
            em.persist(school);

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
            ShardContext.clear();
        }
    }
}
