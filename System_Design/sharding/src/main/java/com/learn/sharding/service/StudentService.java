package com.learn.sharding.service;
import com.learn.sharding.entity.Student;
import com.learn.sharding.shard.ShardContext;
import com.learn.sharding.shard.ShardRouter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Student business logic.
 *
 * Demonstrates:
 * - Writes -> PRIMARY
 * - Reads  -> REPLICA
 */
@Service
public class StudentService {

    private final ShardRouter shardRouter;

    public StudentService(ShardRouter shardRouter) {
        this.shardRouter = shardRouter;
    }

    /**
     * WRITE operation -> PRIMARY
     */
    public void createStudent(String name, int grade) {

        EntityManager em = shardRouter.getEntityManager(true);
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Student student = new Student(
                    ShardContext.getSchoolCode(),
                    name,
                    grade
            );

            em.persist(student);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
    }

    /**
     * READ operation -> REPLICA
     */
    public List<Student> getAllStudents() {

        EntityManager em = shardRouter.getEntityManager(false);

        try {
            return em.createQuery(
                            "SELECT s FROM Student s WHERE s.schoolCode = :schoolCode",
                            Student.class
                    )
                    .setParameter("schoolCode", ShardContext.getSchoolCode())
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
