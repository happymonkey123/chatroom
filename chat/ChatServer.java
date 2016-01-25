package chat;


import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	ServerSocket ss;
	List<Client> clients=new ArrayList<Client>();
	
	public static void main(String[] args)throws Exception{
		new ChatServer().start();
		
		
	
			
			
			
		}
	public void start(){	
		 try {
			ss = new ServerSocket(8888);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			try{
			 boolean start=true;
			while(start){
				
				 Socket s=ss.accept();
				 Client c=new Client(s);
				 new Thread(c).start();
				 clients.add(c);
				}
				
				
			}catch(Exception e){
				try {
					ss.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
	}
	
	
	
	
	class Client implements Runnable{
		Socket s=null;
		DataInputStream is = null;
		DataOutputStream os = null;
        boolean start1=false;
		
		public Client(Socket s){
			this.s=s;
			try {
				is=new DataInputStream (s.getInputStream());
				os=new DataOutputStream (s.getOutputStream());
				start1=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void send(String str){
			try {
				os.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		public void run() {
			// TODO Auto-generated method stub
			
				String line;
				
				while(start1){
				try {
					try{
					line=is.readUTF();
					for(int i=0;i<clients.size();i++){
						Client c=clients.get(i);
						c.send(line);
					}
					
					
					
					}catch(EOFException e){
						is.close();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(Exception e){
					try {
						is.close();
						os.close();
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				}
				}
		}
		
		
	}


