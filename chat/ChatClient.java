package chat;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;





public class ChatClient {
	public static void main(String[] args){
		ChatClient1 chatclient=new ChatClient1();
		chatclient.LaunchFrame();
		
	}
	
	

}

class ChatClient1 extends Frame {
	TextField text = new TextField();
	TextArea area=new TextArea(10,10);
	Socket s;
	DataOutputStream os;
	DataInputStream is;
	boolean connected=false;
	Thread recv=new Thread(new RecvThread());
	public void LaunchFrame(){
		this.setLocation(400,300);
		this.setSize(300,300);
		this.setLayout(new BorderLayout());
		
		this.add(text,BorderLayout.NORTH);    //����ı���
		
		this.add(area,BorderLayout.CENTER);   //�����ʾ��
		//��Ӵ��ڹرմ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
				disconnnect();
				System.exit(0);
			}
		}
		);
		//���ı�����Ӽ�����������ʾ�������
		text.addActionListener(new MonitorText());
		
		
		this.setVisible(true);
		connect();   //���ӷ�����
		recv.start();   //���շ����������߳�����
		
	}
	
	public void connect(){
		try {
			s=new Socket("127.0.0.1",8888);
			os = new DataOutputStream(s.getOutputStream());
			is=new DataInputStream(s.getInputStream());
			connected=true;
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void disconnnect(){
		
		try {
			connected=false;
			try {
				recv.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			os.close();
			is.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class MonitorText implements ActionListener{

		public void actionPerformed(ActionEvent e){
			String str=text.getText();
			
			text.setText("");
			
			
				
				try {
					os.writeUTF(str);
					os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//os.close();
				
			
		}
	}
	
	class RecvThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(connected){
				try {
					while(connected){
					String s=is.readUTF();
					area.append(s+"\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	


}






