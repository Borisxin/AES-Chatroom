package chat;
import java.io.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class AesFile {
	public static Cipher initAESCipher(String sKey, int cipherMode) {   
        KeyGenerator keyGenerator = null;  
        Cipher cipher = null;  
        try {  
            keyGenerator = KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(sKey.getBytes()));  
            SecretKey secretKey = keyGenerator.generateKey();  
            byte[] codeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");  
            cipher = Cipher.getInstance("AES");  
            cipher.init(cipherMode, key);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        }  
        return cipher;  
    }  
	
	public static File encryptFile(File sourceFile,String fileType, String sKey){  
        File encrypfile = null;  
        InputStream inputStream = null;  
        OutputStream outputStream = null;  
        try {  
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));  
            encrypfile = File.createTempFile(sourceFile.getName(), fileType);  
            outputStream = new FileOutputStream(encrypfile);  
            Cipher cipher = initAESCipher(sKey,Cipher.ENCRYPT_MODE);  
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);  
            byte[] cache = new byte[1024];  
            int nRead = 0;  
            while ((nRead = cipherInputStream.read(cache)) != -1) {  
                outputStream.write(cache, 0, nRead);  
                outputStream.flush();  
            }  
            cipherInputStream.close();  
        }  catch (FileNotFoundException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        }  catch (IOException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        } finally {  
            try {  
                inputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
            try {  
                outputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
        }  
        return encrypfile;  
    }  
	
	
	public static File decryptFile(File sourceFile,String fileType,String sKey){  
        File decryptFile = null;  
        InputStream inputStream = null;  
        OutputStream outputStream = null;  
        try {  
            decryptFile = File.createTempFile(sourceFile.getName(),fileType);  
            Cipher cipher = initAESCipher(sKey,Cipher.DECRYPT_MODE);  
            inputStream = new FileInputStream(sourceFile);  
            outputStream = new FileOutputStream(decryptFile);  
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);  
            byte [] buffer = new byte [1024];  
            int r;  
            while ((r = inputStream.read(buffer)) >= 0) {  
                cipherOutputStream.write(buffer, 0, r);  
            }  
            cipherOutputStream.close();  
        } catch (IOException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        }finally {  
            try {  
                inputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
            try {  
                outputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
        }  
        return decryptFile;  
    }  
	
	public static void main(String[] args) {
		File file=new File("C:\\Users\\gn963\\Desktop\\test.txt");
		File tempfile=encryptFile(file,".txt","123");
		tempfile=decryptFile(tempfile,".txt","123");
		System.out.println(tempfile.getAbsolutePath());
	}

}
