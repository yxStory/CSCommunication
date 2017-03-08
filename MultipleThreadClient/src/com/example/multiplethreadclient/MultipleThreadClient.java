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
	//�����������ͨ�ŵ����߳�
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
				//�����Ϣ�������̣߳���ȡ��Ϣ
				if(msg.what==0x123){
					//����ȡ��������չʾ���ı�����
					show.append("\n"+msg.obj.toString());
				}
			}
		};
		clientThread=new ClientThread(handler);
		//�ͻ�������ClientThread�����������ӡ���ȡ���Է�����������
		new Thread(clientThread).start();
		//������Ϣ��������
		button1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//���û����·��ͼ��󣬽����ݷ�װ��Message���͸����̵߳�Handle
					Message msg=new Message();
					msg.what=0x789;
					msg.obj=editText1.getText().toString();
					clientThread.revHandler.sendMessage(msg);
					//������Ϣ����������
					editText1.setText("");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	}
}
