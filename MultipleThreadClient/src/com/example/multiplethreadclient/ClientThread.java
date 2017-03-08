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
	//定义向MultipleThreadClient发送消息的Handler对象
	private Handler handler;
	//定义接收MultipleThreadClient消息的Handler对象
	public Handler revHandler;
	//定义BufferedReader，封装输入流
	BufferedReader br=null;
	//定义输出流
	OutputStream os=null;
	
	public ClientThread(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//设置客户端的Socket，连接服务器的固定IP和端口号
			s=new Socket("192.168.0.101",55559);
			//利用BufferedReader封装输入流，从服务器输入的数据
			br=new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			//输出流，客户端输出给服务器
			os=s.getOutputStream();
			new Thread(){
				public void run() {
					String content=null;
					try {
						//当服务器输入的数据不为空时
						while((content=br.readLine())!=null){
							//发送数据给MultipleThreadClient
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
			//调用prepare()方法，自动创建MessageQueue队列，存储消息
			Looper.prepare();
			//接收来自MultipleThreadClient的数据，发送给服务器
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
			//启动Looper
			Looper.loop();
			
		}catch(SocketTimeoutException e1){
			System.out.println("网络连接超时！");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
