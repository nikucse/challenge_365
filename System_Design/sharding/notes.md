# School Management System (Sharded, Replicated, Multi-Tenant)

## 1. High-Level Architecture

This project demonstrates a **production-style backend architecture** using:

- Spring Boot
- PostgreSQL
- Plain JPA (`EntityManager`, **no Spring Data repositories**)
- **Tenant-based horizontal sharding**
- **Primary–Replica replication**
- **JWT-based authentication**

Each **School** is a **Tenant**.
Each request belongs to **exactly one school**, which maps to **exactly one shard**.

```
Client
  |
  |  (JWT contains schoolCode)
  v
Auth Filter
  |
  |  (extract schoolCode)
  v
Shard Router
  |
  |-- decide shard (A–M / N–Z)
  |-- decide primary vs replica
  v
EntityManager (correct DB)
  |
  v
Business Logic / Transaction
```

---

## 2. Sharding vs Partitioning (Simple Words)

### Partitioning

- Splitting **tables inside the same database**
- Database still knows everything
- Limited scalability

### Sharding

- Splitting **data across multiple databases**
- Each database has **only a subset of data**
- Application decides where data lives

**This project uses sharding, not partitioning.**

---

## 3. Types of Sharding (Explicit)

### 3.1 Horizontal Sharding

- Same schema
- Different rows in different databases
- Example: School A–M vs N–Z

**Used in this project**

**Pros**

- Infinite horizontal scalability
- Strong tenant isolation

**Cons**

- No cross-shard joins
- More application complexity

---

### 3.2 Vertical Partitioning

- Split by columns or features
- Example: billing DB, auth DB

**Not used here**

---

### 3.3 Logical vs Physical Sharding

**Logical Sharding** (used here)

- Shards decided in application code
- Can live on same machine

**Physical Sharding**

- Shards live on different servers

**Why logical first?**

- No premature infrastructure cost
- Easy future migration

---

### 3.4 Tenant-Based Sharding

- One tenant → one shard
- School = Tenant

**Perfect fit for SaaS systems**

---

### When NOT to use sharding

- Small datasets
- Strong cross-entity analytics needed
- Early-stage products

---

## 4. Sharding Strategy Used

### Shard Key

- `schoolCode` (String)

### Rule

- A–M → shard1
- N–Z → shard2

### Why Parent-Level Sharding?

- `School` is the **root aggregate**
- All child entities belong to exactly one school

**Golden Rule:**

> One request → one school → one shard → one database

---

### Why child entities must NOT decide shard

Bad:

- Student decides shard
- Teacher decides shard

Problems:

- Cross-shard joins
- Distributed transactions
- Data inconsistency

Good:

- Parent decides shard
- Child carries `schoolCode`

---

## 5. Replication Explained

### What is Replication?

Replication = **copying data from Primary to Replica**

### Difference Between Sharding & Replication

| Sharding      | Replication |
| ------------- | ----------- |
| Splits data   | Copies data |
| Scale writes  | Scale reads |
| Different DBs | Same data   |

---

### Per Shard Setup

Each shard has:

- 1 Primary (writes)
- 1 Replica (reads)

### Read / Write Rules

- Writes → Primary
- Reads → Replica (eventual consistency)

### If Replica Fails

- Reads fall back to Primary
- Writes unaffected

---

## 6. Databases Created

| Database       | Purpose    |
| -------------- | ---------- |
| shard1_primary | A–M writes |
| shard1_replica | A–M reads  |
| shard2_primary | N–Z writes |
| shard2_replica | N–Z reads  |

**All can run on same machine initially.**

Later:

- Move shard2 to another server
- Zero code change

---

## 7. Authentication + Sharding Flow

### Why Auth Comes First

- Shard key must be **trusted**
- Request body can be forged
- JWT is signed

### JWT Contains

- `userId`
- `schoolCode`
- `role`

### Flow

1. Login
2. Validate credentials
3. Issue JWT
4. Filter extracts `schoolCode`
5. Shard routing happens

---

## 8. Transactions & Consistency

### Rules

- One transaction = one shard
- No cross-shard ACID

### Why?

- Two-phase commit is slow
- Failure-prone

### Future Scaling

- Use **Saga pattern**
- Event-driven consistency

---

## 9. Docker Compose Overview

### Services

- `app`
- `shard1-primary`
- `shard1-replica`
- `shard2-primary`
- `shard2-replica`

### Why Service Names?

- Docker DNS
- No hardcoded IPs
- Cloud-ready

---

## 10. How to Run

```bash
# 1. Start containers
docker-compose up -d

# 2. App runs on
http://localhost:8080
```

---

## 11. Common Mistakes & Warnings

❌ Passing schoolCode in request body
❌ Cross-shard joins
❌ Multi-shard transactions
❌ Using Spring Data repositories

✅ Always route before EntityManager

---

## 12. Who This Is For

- Junior developers (learning architecture)
- Interviewers (real system design)
- Future maintainers (clear rules)

---

## 13. Deep Dive: Real-World Design Questions

### 13.1 How do you handle schema migrations across multiple shards?

In a sharded environment, schema migrations become more complex as they must be executed on all physical databases.

- **Sequential vs Parallel**: For a few shards, sequential execution is fine. For hundreds, use tools that can parallelize migrations (e.g., specialized Flyway configurations or custom automation).
- **Version Control**: Migration scripts must be versioned and idempotent.
- **Zero-Downtime**: Use "expand and contract" patterns (add nullable column, migrate data, add constraints, remove old column) to allow different shards to be at slightly different versions during rollout.

### 13.2 What happens when a shard becomes too hot (Shard Rebalancing)?

If `shard1` (A-M) grows significantly larger than `shard2` (N-Z), you need to rebalance:

- **Directory-Based Routing**: Move from a simple algorithmic rule (A-M) to a lookup table (SchoolID -> ShardID).
- **Consistent Hashing**: Use consistent hashing to minimize data movement when adding a new shard.
- **Migration Process**:
  1. Identify schools to move.
  2. Set them to read-only.
  3. Copy data to the new shard.
  4. Update the lookup table.
  5. Delete data from the old shard.

### 13.3 How do you handle cross-shard reporting and analytics?

Since we cannot perform SQL joins across physical databases:

- **Data Lake/Warehouse**: Stream data from all shards into a central repository (like Snowflake, BigQuery, or a large Postgres instance) using CDC (Change Data Capture) tools like Debezium.
- **Application-Level Aggregation**: Perform multiple queries in the application layer and merge the results (only suitable for small datasets).
- **Read-Only "Global" Shard**: Maintain a special shard that contains aggregated or frequently used global data.

### 13.4 How do you handle unique constraints (like Email) across shards?

Standard database unique constraints only work within a single database.

- **Global Unique Index**: Use a separate, fast storage (like Redis or a dedicated "Global Unique" table in a central DB) to check for existence before inserting into a shard.
- **ID Generation**: Use Snowflake IDs, UUIDs, or a centralized ID allocator to ensure Primary Keys are unique across the entire system, preventing collisions during future merges.

---

## 14. Implementation Lessons Learned

### 14.1 Bypassing Spring Boot Auto-Configuration

In a multi-shard system, you often need to **disable** `DataSourceAutoConfiguration` and `HibernateJpaAutoConfiguration`. Spring's defaults are designed for a single-database world. We manually manage:

- **Multiple DataSources**: Wired specifically for each physical instance.
- **EntityManagerFactory Map**: Storing a pre-configured EMF for every shard/role combination.

### 14.2 The Role of ThreadLocal in Sharding

The `ShardContext` uses `ThreadLocal` to safely propagate the `schoolCode` from the `TenantFilter` all the way to the `ShardRouter`.

- **Safety**: Always use a `try-finally` block in the filter to `clear()` the ThreadLocal, preventing memory leaks and data "bleeding" between requests in pooled threads.

---

## 15. Real-World Failure Scenarios

### 15.1 "Eventual Consistency" Lag

When writing to the Primary and immediately reading from the Replica, the data might not have replicated yet.

- **Solution**: For critical flows (e.g., "Create user and redirect to profile"), the application should read from the **Primary** for a short window after a write, or use "Read-Your-Writes" consistency logic.

### 15.2 Shard Outage

If `shard2-primary` goes down:

- Schools N-Z cannot perform writes.
- Reads can still function if `shard2-replica` is healthy.
- System remains **partially available**, which is a key advantage of sharding over a single massive database.

---
