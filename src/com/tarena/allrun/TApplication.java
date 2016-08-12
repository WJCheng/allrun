package com.tarena.allrun;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.tarena.allrun.entity.UserEntity;
import com.tarena.allrun.entity.XmppConfig;
import com.tarena.allrun.util.LogUtil;
import com.tarena.allrun.util.Tools;
import com.tarena.allrun.util.XmlUtil;

import android.app.Application;
import android.util.Log;

public class TApplication extends Application {

	/**
	 * isRelease
	 * true �ѷ���
	 * false ������
	 */
	public static boolean isRelease = false;
	
	public static XmppConfig config;
	public static XMPPConnection connection;
	public static TApplication instance;
//	public static String serviceName;
	
	public static int networkType;
	public static int NETWORKTYPE_NONE=1;
	public static int NETWORKTYPE_WIFI=2;
	public static int NETWORKTYPE_MOBILE=3;

	public static UserEntity currentUser;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		instance = this;
				
		Log.i("TApplication", "onCreate");

		config = new XmppConfig();
		try {
			XmlUtil.loadConfig(this, config);
			if (config.isValid()) {
				connectChatServer();
			}else{
				Log.i("info", "��ȡ������Ϣ����");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectChatServer(){
		ConnectionConfiguration configuration = new ConnectionConfiguration(
				config.getHost(), config.getPort(),
				config.getServiceName());
		connection = new XMPPConnection(configuration);
		new Thread() {
			public void run() {
				try {
					connection.connect();
					Log.i("info", "�Ƿ�ɹ������ϣ� " + connection.isConnected());
				} catch (XMPPException e) { 
					e.printStackTrace();
//					Tools.showToast(instance, "�޷����Ӳ��Ϸ�����");
					LogUtil.i("TApplication", "�޷����ӷ�����"+config.host);
				}finally{
//					synchronized (connection) {
//						connection.notify();
//					}
				}
				
			};
		}.start();

	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.i("TApplication", "onLowMemory");
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
		Log.i("TApplication", "onTrimMemory");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i("TApplication", "onTerminate");
	}
}
