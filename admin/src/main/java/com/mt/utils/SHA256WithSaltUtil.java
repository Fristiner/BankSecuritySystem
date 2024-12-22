package com.mt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256WithSaltUtil {

    private static final int SALT_LENGTH = 16; // 盐长度

    /**
     * 使用 SHA-256 和随机生成的盐对输入字符串进行哈希处理。
     *
     * @param input 要哈希的字符串
     * @return 包含哈希值和盐的 Base64 编码字符串
     */
    public static String hashStringWithSalt(String input) {
        try {
            // 生成随机盐
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // 创建 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 更新 digest 以包含盐
            digest.update(salt);

            // 将输入字符串转换为字节数组并更新到 digest
            byte[] encodedhash = digest.digest(input.getBytes());

            // 将盐和哈希值组合在一起
            byte[] combined = new byte[salt.length + encodedhash.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(encodedhash, 0, combined, salt.length, encodedhash.length);

            // 返回 Base64 编码后的字符串表示形式
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing the string with salt", e);
        }
    }

    /**
     * 验证给定的字符串是否与提供的带盐哈希匹配。
     *
     * @param input       要验证的字符串
     * @param hashedValue 已经哈希过的字符串（包括盐）
     * @return 如果匹配则返回 true；否则返回 false
     */
    public static boolean verifyHashedString(String input, String hashedValue) {
        try {
            // 解码 Base64 编码的字符串
            byte[] decoded = Base64.getDecoder().decode(hashedValue);

            // 分离盐和哈希值
            byte[] salt = new byte[SALT_LENGTH];
            byte[] storedHash = new byte[decoded.length - SALT_LENGTH];
            System.arraycopy(decoded, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(decoded, SALT_LENGTH, storedHash, 0, storedHash.length);

            // 再次哈希输入字符串并比较结果
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            byte[] newHash = digest.digest(input.getBytes());

            // 比较两次哈希的结果
            return slowEquals(storedHash, newHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while verifying the hashed string", e);
        }
    }

    /**
     * 比较两个字节数组是否相等，避免时间侧信道攻击。
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    public static void main(String[] args) {
        // 示例：对密码进行 SHA-256 加盐哈希处理
        String password = "your_password_here";
        String hashedPasswordWithSalt = hashStringWithSalt(password);
        System.out.println("Original Password: " + password);
        System.out.println("Hashed Password With Salt: " + hashedPasswordWithSalt);

        // 验证密码是否匹配
        boolean isVerified = verifyHashedString(password, hashedPasswordWithSalt);
        System.out.println("Verification Result: " + isVerified);
    }
}