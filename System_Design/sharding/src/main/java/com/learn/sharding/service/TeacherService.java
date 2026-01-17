package com.learn.sharding.service;

import com.learn.sharding.entity.Teacher;
import com.learn.sharding.shard.ShardContext;
import com.learn.sharding.shard.ShardRouter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Teacher business logic.
 *
 * Same sharding rules as Student.
 */
@Service
public class TeacherService {

    private final ShardRouter shardRouter;

    public TeacherService(ShardRouter shardRouter) {
        this.shardRouter = shardRouter;
    }

    public void createTeacher(String name, String subject) {

        EntityManager em = shardRouter.getEntityManager(true);
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Teacher teacher = new Teacher(
                    ShardContext.getSchoolCode(),
                    name,
                    subject
            );

            em.persist(teacher);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
    }

    public List<Teacher> getAllTeachers() {

        EntityManager em = shardRouter.getEntityManager(false);

        try {
            return em.createQuery(
                            "SELECT t FROM Teacher t WHERE t.schoolCode = :schoolCode",
                            Teacher.class
                    )
                    .setParameter("schoolCode", ShardContext.getSchoolCode())
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
