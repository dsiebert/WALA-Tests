package auth;

import java.awt.TextField;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Ciphers {

	public void doEncryptionDES(){
		try {
			Cipher cipher = Cipher.getInstance("DES");
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	public void doEncryptionDESCBCPKCS5Padding(){
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	public void doEncryptionRSA(){
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	public void doEncryptionAES(){
		try {
			Cipher cipher = Cipher.getInstance("AES");
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	public void doEncryptionPBEWithHmacSHA1AndDESede(){
		try {
			Cipher cipher = Cipher.getInstance("PBEWithHmacSHA1AndDESede");
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	public void doEncryptionParameter(String algo){
		try {
			Cipher cipher = Cipher.getInstance(algo);
			
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		}
		
	}
	
	
}
