package hr.fer.oprpp1.hw05.crypto;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.file.Files;
import java.io.*;

public class Crypto {
	
	public static void main(String[] args) {
		if(args.length!=2 && args.length!=3)
			throw new IllegalArgumentException("You must provide 2 or 3 arguments!");
		Path p;
		if(args.length==2) {
			if(args[0].equals("checksha")) {
				p = Paths.get(args[1]);
				checkSha(p);
			}
			else
				throw new IllegalArgumentException("You must provide a path!");
		}
		else {
			if(!args[0].equals("encrypt") && !args[0].equals("decrypt"))
				throw new IllegalArgumentException("Illegal argument, must be \"encrypt\" or \"decrypt\"");
			else {
				p=Paths.get(args[1]);
				Path destination=Paths.get(args[2]);
				encryptDecrypt(args[0],p,destination);
			}
		}
	}
	
	private static void checkSha(Path p) {
		System.out.println("Please provide expected sha-256 digest for hw05test.bin:\r\n"
				+ ">");
		try (Scanner s=new Scanner(System.in);
				InputStream is = Files.newInputStream(p)) {
			byte[] buff = new byte[4096];
			try {
				String userInput=s.nextLine();
				MessageDigest digestor=MessageDigest.getInstance("SHA-256");
				int numberRead=is.read(buff);
				while(numberRead>1) {
					if(numberRead==4096) {
						digestor.update(buff);
					}else {
						byte[] notFull=new byte[numberRead];
						int j=0;
						for(byte b: buff) {
							if(j==numberRead)
								break;
							notFull[j]=buff[j];
							j++;
						}
						digestor.update(notFull);	
					}
					numberRead=is.read(buff);
				}
				byte[] compare=digestor.digest();
				if(Util.bytetohex(compare).equals(userInput)) {
					System.out.println("Digesting completed. Digest of hw05test.bin matches expected digest.");
				}else {
					System.out.println("Digesting completed. Digest of hw05test.bin does not match the expected digest. Digest\r\n"
							+ "was: "+Util.bytetohex(compare));
					System.exit(0);
				}
			}
			catch(NoSuchAlgorithmException e) {}
		}catch (IOException ex) {}
	}
	
	public static void encryptDecrypt(String direction, Path input, Path destination) {
		File dest=new File(destination.toString());
		if(!dest.exists()){
			try {
				dest.createNewFile();
			}catch(IOException e) {
			}
		}
		try (Scanner s=new Scanner(System.in);
				InputStream is = Files.newInputStream(input);
				OutputStream os = new FileOutputStream(dest)) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\r\n"
					+ ">");
			String keyText=s.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):\r\n"
					+ ">");
			String ivText =s.nextLine();
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				Boolean encrypt=direction.equals("encrypt");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				byte[] buff = new byte[4096];
				int numberRead=is.read(buff);
				while(numberRead>1) {
					if(numberRead==4096) {
						os.write(cipher.update(buff));
					}else {
						byte[] notFull=new byte[numberRead];
						int j=0;
						for(byte b: buff) {
							if(j==numberRead)
								break;
							notFull[j]=buff[j];
							j++;
						}
						os.write(cipher.update(notFull));	
					}
					numberRead=is.read(buff);
				}
				os.write(cipher.doFinal());
				String print;
				if(encrypt==true) {
					print="Encryption";
				}
				else
					print="Decryption";
				System.out.println(print+" completed. Generated file "+destination.toString()+" based on file "+input.toString());
			}catch (NoSuchAlgorithmException|InvalidKeyException|InvalidAlgorithmParameterException| BadPaddingException| IllegalBlockSizeException e) {}
	}catch(IOException | NoSuchPaddingException ex) {}
	}

}