package chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;

public class client extends JFrame {

	private JPanel contentPane;
	private JTextField ipnum;
	private JTextField nametxt;
	private JTextField portnum;
	private JTextField keytxt;
	private JTextField wordtxt;
	private String name,ip="";
	private int port=0;
	private Socket sock;
	private BufferedReader  reader;           
	private PrintStream  writer;
	private JTextArea board;
	private String keynum;
	protected  SecretKeySpec key;
	public static SecretKeySpec fakekey;
	private JTextField filenametxt;
    public static String efilepath="";
    public static String dfilepath="";
	public static void main(String[] args) {
		fakekey=DEMO.passwordtokey("a");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client frame = new client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public client() {
		key=fakekey;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 417);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIp = new JLabel("port:");
		lblIp.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblIp.setBounds(14, 20, 49, 33);
		contentPane.add(lblIp);
		
		JLabel lblName = new JLabel("name:");
		lblName.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblName.setBounds(410, 17, 62, 39);
		contentPane.add(lblName);
		
		JLabel lblKey = new JLabel("key:");
		lblKey.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblKey.setBounds(600, 265, 62, 45);
		contentPane.add(lblKey);
		
		ipnum = new JTextField();
		ipnum.setText("127.0.0.1");
		ipnum.setBounds(233, 27, 110, 25);
		contentPane.add(ipnum);
		ipnum.setColumns(10);
		
		JButton connectbtn = new JButton("go");
		connectbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!nametxt.getText().equals("") && !ipnum.getText().equals("") && !portnum.getText().equals("")){
				   name = nametxt.getText();
				   ip  = ipnum.getText(); 
				   port=Integer.parseInt(portnum.getText());
				   EstablishConnection(); 
				   Thread readerThread = new Thread(new IncomingReader());  
				   readerThread.start();
				}
				else{
					checkinput ch=new checkinput();
					ch.show();
				}
			}
		});
		connectbtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		connectbtn.setBounds(639, 24, 99, 27);
		contentPane.add(connectbtn);
		
		nametxt = new JTextField();
		nametxt.setText("123");
		nametxt.setBounds(500, 27, 116, 25);
		contentPane.add(nametxt);
		nametxt.setColumns(10);
		
		keytxt = new JTextField();
		keytxt.setBounds(658, 278, 99, 25);
		contentPane.add(keytxt);
		keytxt.setColumns(10);
		
		JLabel lblType = new JLabel("talk:");
		lblType.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblType.setBounds(14, 258, 54, 58);
		contentPane.add(lblType);
		
		wordtxt = new JTextField();
		wordtxt.setBounds(67, 278, 267, 25);
		contentPane.add(wordtxt);
		wordtxt.setColumns(10);
		
		JButton sendbtn = new JButton("send");
		sendbtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		sendbtn.addActionListener(
				new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte[] iv=null;
				byte[] cipher = null;
				String tempiv="";
				String tempcipher="";
				if((ip!=null)&&(wordtxt.getText()!=""))    
			{ 
					
				    if(!keytxt.getText().equals(""))
				    {
				    	iv = new byte[128 / 8]; 
				  	    SecureRandom prng = new SecureRandom();
				  	    prng.nextBytes(iv);
				  	    System.out.println("siv:"+iv.length);
				  	  tempiv=DEMO.parseByte2HexStr(iv);
				    	try {
							cipher=DEMO.Encrypt(key, iv, wordtxt.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}
							tempcipher=DEMO.parseByte2HexStr(cipher);
							try{
						        writer.println((name+":"));
						        writer.println(tempiv);
						        writer.println(tempcipher);
						        writer.println(key.toString());
						     writer.flush();         
						    }catch(Exception ex ){
						     System.out.println("送出資料失敗");
						    }
						    wordtxt.setText("");        
				    }
				    else if(keytxt.getText().equals(""))
				    {
				    	try{
				    		writer.println((name+":"+wordtxt.getText()));
					        writer.println(tempiv);
					        writer.println(tempcipher);
					        writer.println("");
					     writer.flush();         
					    }catch(Exception ex ){
					     System.out.println("送出資料失敗");
					    }
					    wordtxt.setText("");
				    	 
				    }
				    
			 }
			}
		});
		sendbtn.setBounds(348, 277, 99, 27);
		contentPane.add(sendbtn);
		
		JButton keybtn = new JButton("set");
		keybtn.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				check n=new check(keytxt.getText());
				n.show();
				keynum=keytxt.getText();
				key=DEMO.passwordtokey(keynum);
			}
		});
		keybtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		keybtn.setBounds(658, 309, 99, 27);
		contentPane.add(keybtn);
		
		JLabel label = new JLabel("ip:");
		label.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		label.setBounds(197, 20, 35, 33);
		contentPane.add(label);
		
		portnum = new JTextField();
		portnum.setColumns(10);
		portnum.setBounds(67, 27, 89, 25);
		contentPane.add(portnum);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 66, 743, 179);
		contentPane.add(scrollPane);
		
		board= new JTextArea();
		scrollPane.setViewportView(board);
		
		JLabel lblUploadFile = new JLabel("upload file:");
		lblUploadFile.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblUploadFile.setBounds(14, 292, 117, 58);
		contentPane.add(lblUploadFile);
		
		JButton btnselect = new JButton("select");
		btnselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
	            int returnValue = fileChooser.showOpenDialog(null);
	            if (returnValue == JFileChooser.APPROVE_OPTION) {
	              File selectedFile = fileChooser.getSelectedFile();
	              System.out.println(selectedFile.getParent());
	              filenametxt.setText(String.valueOf(selectedFile.getAbsoluteFile()));
	              efilepath = String.valueOf(selectedFile.getParent())+"\\"+"encrypt_"+String.valueOf(selectedFile.getName());
	              dfilepath = String.valueOf(selectedFile.getParent())+"\\"+"decrypt_"+String.valueOf(selectedFile.getName());
	              }
	          
		}
	        });
		btnselect.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		btnselect.setBounds(348, 309, 99, 27);
		contentPane.add(btnselect);
		JButton encryptbtn = new JButton("encrypt");
		encryptbtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		encryptbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file=new File(filenametxt.getText());
				int endIndex = file.getName().length();
				int startIndex = file.getName().lastIndexOf(46) + 1;
			    String filetype = file.getName().substring(startIndex, endIndex); 
				File tempfile=encryptFile(file,filetype,keytxt.getText());
				efinish ef=new efinish();
				ef.show();
			}
		});
		encryptbtn.setBounds(461, 308, 116, 28);
		contentPane.add(encryptbtn);
		
		filenametxt = new JTextField();
		filenametxt.setColumns(10);
		filenametxt.setBounds(145, 312, 198, 25);
		contentPane.add(filenametxt);
		
		JButton decryptbtn = new JButton("decrypt");
		decryptbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file=new File(filenametxt.getText());
				int endIndex = file.getName().length();
				int startIndex = file.getName().lastIndexOf(46) + 1;
			    String filetype = file.getName().substring(startIndex, endIndex); 
				File tempfile=decryptFile(file,filetype,keytxt.getText());
			    dfinish df=new dfinish();
				df.show();
			}
		});
		decryptbtn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		decryptbtn.setBounds(461, 342, 116, 28);
		contentPane.add(decryptbtn);

	}
	 private void EstablishConnection(){
		  try{
		   sock = new Socket(ip,port);      
		   InputStreamReader streamReader =  new InputStreamReader(sock.getInputStream());  
		   reader = new BufferedReader(streamReader);    
		   
		   writer = new PrintStream(sock.getOutputStream());
		  
		   System.out.println("網路建立-連線成功");    
		   
		  }catch(IOException ex ){
		   System.out.println("建立連線失敗");
		  }
		 }
	 
	 public class IncomingReader implements Runnable{
		  public void run(){
		   String name;
		   String getiv;
		   String getcipher;
		   String getkey;
		   String message;
		   byte[] tempiv=null; // iv
		   byte[] tempcipher=null; // cipher
		   try{
		    while ((name = reader.readLine()) != null && (getiv=reader.readLine())!=null && (getcipher=reader.readLine())!=null && (getkey=reader.readLine())!=null){
		    	if(getkey.equals(key.toString()))
		    	{
		    		tempiv=DEMO.parseHexStr2Byte(getiv);
		    		tempcipher=DEMO.parseHexStr2Byte(getcipher);
		    		message=DEMO.Decrypt(key, tempcipher,tempiv );
		    		board.append(name);
		    		board.append(message);
		    		board.append("   (此資料已成功解密)"+"\n");
		    	}
		    	else if(getkey.equals("")){
		    		board.append(name);
				    board.append(getcipher);
				    board.append("   (此資料未加密)"+"\n");
		    	}
		    	else{
		    		board.append(name);
		    		board.append(getcipher);
		    		board.append("   (此資料已加密，需要有相對應key才能解開)"+"\n");
		    	}
		    }
		   }catch(Exception ex ){ex.printStackTrace();}
		  }
		 } 
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
	            
				encrypfile = new File(efilepath);
//	            encrypfile = File.createTempFile(sourceFile.getName(), fileType);  
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
	        	decryptFile = new File(dfilepath);
//	            decryptFile = File.createTempFile(sourceFile.getName(),fileType);  
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
}
class DEMO {

	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
}  
 public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
} 
    public static byte[] Encrypt(SecretKey secretKey, byte[] iv, String msg) throws Exception{
	  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); 
	  cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));    
	  byte[] byteCipherText = cipher.doFinal(msg.getBytes("UTF-8"));
	  return byteCipherText;
	 }
	 
	 public static String Decrypt(SecretKey secretKey, byte[] cipherText, byte[] iv) throws Exception{
	  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); 
	  cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));    
	  byte[] decryptedText = cipher.doFinal(cipherText);
	  String strDecryptedText = new String(decryptedText,"UTF-8");
	  return strDecryptedText;
	 }
	 public static SecretKeySpec passwordtokey(String password){
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
           kgen.init(128, new SecureRandom(password.getBytes()));
           SecretKey secretKey = kgen.generateKey();
           byte[] enCodeFormat = secretKey.getEncoded();
           SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			return key;
		}
			catch (NoSuchAlgorithmException e) {
		
				e.printStackTrace();
			}
		return null;
	 }
	 public static void main(String args[]) throws Exception{
	 }

}
