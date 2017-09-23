package chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class chatroom extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Vector<PrintStream> output;
    private int portnum;
    private ServerSocket serverSock;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatroom frame = new chatroom();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public chatroom() {
		super("chat server");
		setBackground(Color.PINK);
		setForeground(Color.PINK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 180);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setForeground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIp = new JLabel("ip:");
		lblIp.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblIp.setBounds(14, 13, 34, 41);
		contentPane.add(lblIp);
		
		JLabel label = new JLabel("127.0.0.1");
		label.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		label.setBounds(49, 24, 107, 19);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("server start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output = new Vector<PrintStream>();
			    portnum = Integer.parseInt(textField.getText());
				Thread s=new Thread(new Accept(portnum));
				s.start();
				 
			}
		});
		btnNewButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		btnNewButton.setBounds(200, 21, 156, 28);
		contentPane.add(btnNewButton);
		
		JLabel lblPort = new JLabel("port:");
		lblPort.setFont(new Font("Bauhaus 93", Font.PLAIN, 22));
		lblPort.setBounds(14, 56, 56, 41);
		contentPane.add(lblPort);
		
		textField = new JTextField();
		textField.setBounds(69, 67, 116, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnServerPause = new JButton("server pause");
		btnServerPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					serverSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnServerPause.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		btnServerPause.setBounds(200, 66, 156, 28);
		contentPane.add(btnServerPause);
	}
	public class Accept implements Runnable{
		int portnum;
		public Accept(int portnum){
			this.portnum=portnum;
		}
		@Override
		public void run() {
			 try{
				   
				   serverSock = new ServerSocket(portnum); 
				   while(true){
				    Socket cSocket = serverSock.accept();    
				    PrintStream writer =  new PrintStream(cSocket.getOutputStream());  
				    System.out.println(writer); 
				    output.add(writer);         
				    Thread t = new Thread(new Process(cSocket)); 
				    t.start();           
				    System.out.println(cSocket.getLocalSocketAddress()+"有"+(output.size())+"個連接");               
				  } 
				  }catch(Exception ex){System.out.println("連接失敗");}
			
		}
		
	}
	public class Process implements Runnable{   
		  BufferedReader reader;  
		  Socket sock;            
		  public Process(Socket cSocket)
		  {
		   try{
		    sock = cSocket;
		    InputStreamReader isReader =  new InputStreamReader(sock.getInputStream()); 
		    reader = new BufferedReader(isReader);
		   }catch(Exception ex){
		    System.out.println("連接失敗Process");
		   } 
		  }
		  public void run(){
		   String message;
		   try{
		    while ((message = reader.readLine())!=null){   
		    	
		     tellApiece(message);
		     System.out.println(message);
		    }
		   }catch(Exception ex){System.out.println("有一個連接離開");}
		  }
		  public void tellApiece(String message){
		   Iterator<PrintStream> it = output.iterator(); 
		   while(it.hasNext()){          
		    try{
		    PrintStream writer = (PrintStream) it.next();  
		    writer.println(message); 
		    writer.flush();           
		    }
		    catch(Exception ex){
		     System.out.println("連接失敗Process");
		    }
		   }
		  }
		 } 
		}

