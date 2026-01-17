package com.learn.sharding.shard;
/**
 * Holds shard-related information for the current request.
 *
 * IMPLEMENTATION DETAIL:
 * - Uses ThreadLocal because:
 *   - One HTTP request = one thread
 *   - Ensures isolation between requests
 *
 * DO NOT store business data here.
 * Only routing metadata.
 */
public final class ShardContext {

    private static final ThreadLocal<String> SCHOOL_CODE = new ThreadLocal<>();
    private static final ThreadLocal<ShardType> SHARD_TYPE = new ThreadLocal<>();

    private ShardContext() {
        // Utility class
    }

    public static void setSchoolCode(String schoolCode) {
        SCHOOL_CODE.set(schoolCode);
    }

    public static String getSchoolCode() {
        return SCHOOL_CODE.get();
    }

    public static void setShardType(ShardType shardType) {
        SHARD_TYPE.set(shardType);
    }

    public static ShardType getShardType() {
        return SHARD_TYPE.get();
    }

    /**
     * MUST be called at the end of every request.
     * Prevents ThreadLocal memory leaks.
     */
    public static void clear() {
        SCHOOL_CODE.remove();
        SHARD_TYPE.remove();
    }
}
