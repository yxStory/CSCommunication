package com.example.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 负责监听客户端的Socket连接请求
 * @author yx
 *
 */
public class MyServer {
	//定义保存所有Socket的List
	public static ArrayList<Socket> socketList=new ArrayList<Socket>();
	
	public static void main(String[] args) throws IOException {
		//创建ServerSocket，监听客户端Socket的连接请求
		ServerSocket ss=new ServerSocket(55559);
		while(true){
			//每次接收到客户端的Socket时，服务器端创建对应的Socket
			Socket s=ss.accept();
			//将socket加入到socketList中
			socketList.add(s);
			//启动新的线程处理通信任务
			new Thread(new ServerThread(s)).start();
		}
	}
}
