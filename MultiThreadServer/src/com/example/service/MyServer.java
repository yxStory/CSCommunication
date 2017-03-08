package com.example.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ��������ͻ��˵�Socket��������
 * @author yx
 *
 */
public class MyServer {
	//���屣������Socket��List
	public static ArrayList<Socket> socketList=new ArrayList<Socket>();
	
	public static void main(String[] args) throws IOException {
		//����ServerSocket�������ͻ���Socket����������
		ServerSocket ss=new ServerSocket(55559);
		while(true){
			//ÿ�ν��յ��ͻ��˵�Socketʱ���������˴�����Ӧ��Socket
			Socket s=ss.accept();
			//��socket���뵽socketList��
			socketList.add(s);
			//�����µ��̴߳���ͨ������
			new Thread(new ServerThread(s)).start();
		}
	}
}
