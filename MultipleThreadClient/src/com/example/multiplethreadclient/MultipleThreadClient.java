package com.example.multiplethreadclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MultipleThreadClient extends Activity {
	
	private Button button1;
	private EditText editText1;
	private TextView show;
	
	Handler handler;
	//定义与服务器通信的子线程
	ClientThread clientThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show=(TextView)findViewById(R.id.textView1);
		editText1=(EditText)findViewById(R.id.editText1);
		button1=(Button)findViewById(R.id.button1);
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				//如果消息来自子线程，读取消息
				if(msg.what==0x123){
					//将读取到的内容展示在文本框中
					show.append("\n"+msg.obj.toString());
				}
			}
		};
		clientThread=new ClientThread(handler);
		//客户端启动ClientThread创建网络连接、读取来自服务器的数据
		new Thread(clientThread).start();
		//发送消息给服务器
		button1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//当用户按下发送键后，将数据封装成Message发送给子线程的Handle
					Message msg=new Message();
					msg.what=0x789;
					msg.obj=editText1.getText().toString();
					clientThread.revHandler.sendMessage(msg);
					//发送消息后清空输入框
					editText1.setText("");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}
}
