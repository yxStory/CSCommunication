package com.example.multiplethreadclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ClientThread implements Runnable{

	private Socket s;
	//������MultipleThreadClient������Ϣ��Handler����
	private Handler handler;
	//�������MultipleThreadClient��Ϣ��Handler����
	public Handler revHandler;
	//����BufferedReader����װ������
	BufferedReader br=null;
	//���������
	OutputStream os=null;
	
	public ClientThread(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//���ÿͻ��˵�Socket�����ӷ������Ĺ̶�IP�Ͷ˿ں�
			s=new Socket("192.168.0.101",55559);
			//����BufferedReader��װ���������ӷ��������������
			br=new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			//��������ͻ��������������
			os=s.getOutputStream();
			new Thread(){
				public void run() {
					String content=null;
					try {
						//����������������ݲ�Ϊ��ʱ
						while((content=br.readLine())!=null){
							//�������ݸ�MultipleThreadClient
							Message msg=new Message();
							msg.what=0x123;
							msg.obj=content;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				};
			}.start();
			//����prepare()�������Զ�����MessageQueue���У��洢��Ϣ
			Looper.prepare();
			//��������MultipleThreadClient�����ݣ����͸�������
			revHandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(msg.what==0x789){
						try {
							os.write((msg.obj.toString()+"\r\n")
									.getBytes("utf-8"));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			};
			//����Looper
			Looper.loop();
			
		}catch(SocketTimeoutException e1){
			System.out.println("�������ӳ�ʱ��");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
