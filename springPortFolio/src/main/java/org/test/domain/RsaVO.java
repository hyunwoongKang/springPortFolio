package org.test.domain;

import java.security.PrivateKey;

public class RsaVO {

	private PrivateKey privateKey;
	private String modulus;
	private String exponent;
	
	  public RsaVO(PrivateKey privateKey, String modulus, String exponent) {
	        this.privateKey = privateKey;
	        this.modulus = modulus;
	        this.exponent = exponent;
	    }

	
	public PrivateKey getPriavateKey() {
		return privateKey;
	}
	public void setPriavateKey(PrivateKey priavateKey) {
		this.privateKey = priavateKey;
	}
	public String getModulus() {
		return modulus;
	}
	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	public String getExponent() {
		return exponent;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	@Override
	public String toString() {
		return "RsaVO [privateKey=" + privateKey + ", modulus=" + modulus + ", exponent=" + exponent + "]";
	}
	
	
}
