package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ������ÿ���̵߳�ͨ�ŵ��߳���
 * @author yx
 *
 */
public class ServerThread implements Runnable{

	//���嵱ǰ�߳��������Socket
	Socket s=null;
	//���߳��������Socket����Ӧ��������
	BufferedReader br=null;
	
	public ServerThread(Socket s) throws IOException{
		// TODO Auto-generated constructor stub
		this.s=s;
		//��ʼ����Socket��Ӧ��������,�ͻ��˴�����Ϣ
		br=new BufferedReader(new InputStreamReader(
				s.getInputStream(),"utf-8"));
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			String content=null;
			while((content=readFromClient())!= null ){
				//����socketList������Socket����������������ÿ���ͻ��˷�һ��
				for(Socket s:MyServer.socketList){
					//�����������������д��������У�utf-8����
					OutputStream os=s.getOutputStream();
					os.write((content+"\n").getBytes("utf-8"));
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�ͻ��˵����ݵķ���
	 * @return
	 */
	private String readFromClient(){
		try {
			return br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			//��������쳣��������Socket��Ӧ�Ŀͻ����ѹرգ���ɾ����Socket
			MyServer.socketList.remove(s);
		}
		return null;		
	}

}
