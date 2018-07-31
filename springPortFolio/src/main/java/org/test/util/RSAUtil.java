package org.test.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.domain.RsaVO;

//암호화 기능 유틸
public class RSAUtil {
	
	private KeyPairGenerator generator;
	private KeyFactory keyFactory;
	private KeyPair keyPair;
	private Cipher cipher;
	
	private static final Logger logger=LoggerFactory.getLogger(RSAUtil.class);
	
	public RSAUtil() {
		try {
			generator=KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			keyFactory = keyFactory.getInstance("RSA");
			cipher=Cipher.getInstance("RSA");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("RSA 생성 실패");
		}
	}
	
	public RsaVO createRSA() {
		RsaVO rsa=null;
			
		try {
			 keyPair=generator.generateKeyPair();
			 
			 PublicKey publicKey = keyPair.getPublic();
			 PrivateKey privateKey=keyPair.getPrivate();
			 
			 RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			 String modulus=publicSpec.getModulus().toString(16);
			 String exponent=publicSpec.getPublicExponent().toString(16);
			
			 rsa=new RsaVO(privateKey,modulus,exponent);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsa;
	}
	
	public String getDecryptText(PrivateKey privateKey, String encryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(hexToByteArray(encryptedText));
        return new String(decryptedBytes, "UTF-8");
    }
 
    // 16진수 문자열을 byte 배열로 변환
    private byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[] {};
        }
 
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }


	
}