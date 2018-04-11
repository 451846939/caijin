package com.kdkj.caijin.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

public class ShiroEncryptionUtils {
    /**
     * @param @param  str
     * @param @return
     * @return String
     * @throws
     * @Title: getBase64Encryption
     * @Description: base64加密
     */
    public static String getBase64Encryption(String str) {
        return Base64.encodeToString(str.getBytes());
    }

    /**
     * @param @param  str
     * @param @return
     * @return String
     * @throws
     * @Title: getBase64Decrypt
     * @Description: base64解密
     */
    public static String getBase64Decrypt(String str) {
        return Base64.decodeToString(str);
    }

    /**
     * @param @param  str
     * @param @return
     * @return String
     * @throws
     * @Title: getHexEncryption
     * @Description: 16进制加密
     */
    public static String getHexEncryption(String str) {
        return Hex.encodeToString(str.getBytes());
    }

    /**
     * @param @param  str
     * @param @return
     * @return String
     * @throws
     * @Title: getHexDecrypt
     * @Description: 16进制解密
     */
    public static String getHexDecrypt(String str) {
        return new String(Hex.decode(str.getBytes()));
    }

    /**
     * 生成盐
     *
     * @param salt
     * @return
     */
    public static String getSalt(String salt) {
        SecureRandomNumberGenerator randomNumberGenerator =
                new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed(salt.getBytes());
        return randomNumberGenerator.nextBytes().toHex();
    }

    public static String getRandomSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
}
