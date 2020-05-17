package com.dmf.bootstarter.logger;

import java.util.Base64;
import java.util.UUID;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
public abstract class AbstractUUIDShort {

    /**
     * 生成唯一ID
     */
    public static String generate() {
        UUID uuid = UUID.randomUUID();
        return compressedUUID(uuid);
    }

    protected static String compressedUUID(UUID uuid) {
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, 8);
        return Base64.getEncoder().encodeToString(byUuid);
    }

    protected static void long2bytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }
}
