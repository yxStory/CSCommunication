package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 负责处理每个线程的通信的线程类
 * @author yx
 *
 */
public class ServerThread implements Runnable{

	//定义当前线程所处理的Socket
	Socket s=null;
	//该线程所处理的Socket所对应的输入流
	BufferedReader br=null;
	
	public ServerThread(Socket s) throws IOException{
		// TODO Auto-generated constructor stub
		this.s=s;
		//初始化该Socket对应的输入流,客户端传入消息
		br=new BufferedReader(new InputStreamReader(
				s.getInputStream(),"utf-8"));
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			String content=null;
			while((content=readFromClient())!= null ){
				//遍历socketList的所有Socket，将读到的内容向每个客户端发一遍
				for(Socket s:MyServer.socketList){
					//输出流，将所有内容写到输出流中，utf-8编码
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
	 * 读取客户端的数据的方法
	 * @return
	 */
	private String readFromClient(){
		try {
			return br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
			//如果捕获到异常，表明该Socket对应的客户端已关闭，则删除该Socket
			MyServer.socketList.remove(s);
		}
		return null;		
	}

}
