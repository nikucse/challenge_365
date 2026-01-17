package com.learn.sharding.shard;

/**
 * Indicates whether the operation should hit:
 * - PRIMARY (writes)
 * - REPLICA (reads)
 */
public enum ShardType {
    PRIMARY,
    REPLICA
}
