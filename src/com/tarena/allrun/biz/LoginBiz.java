package com.tarena.allrun.biz;

import org.jivesoftware.smack.XMPPException;

import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

import com.tarena.allrun.TApplication;
import com.tarena.allrun.entity.UserEntity;
import com.tarena.allrun.util.Const;

public class LoginBiz {

	private UserEntity userEntity;

	public void LoginBiz() {

	}

	public void login(final UserEntity userEntity) {
		this.userEntity = userEntity;
		
		new Thread() {
			public void run() {
				int status = -1;
				try {
					int count = 0;
					while(count < 60 && TApplication.connection.isConnected() == false){
						count++;
						this.sleep(100);
					}
					if(TApplication.connection.isConnected() == true){
						TApplication.connection.login(userEntity.getUser(),
								userEntity.getPwd());
						Log.i("info", Thread.currentThread().getName() + "  "
								+ TApplication.connection.isAuthenticated());
						
						status = Const.STATUS_OK;
					}else{
						status = Const.STATUS_CONN_FAILED;
					}
				} catch (Exception e) {
					e.printStackTrace();
					String info = e.toString();
					if ("SASL authentication failed using mechanism DIGEST-MD5: "
							.equals(info)) {
						status = Const.STATUS_PASSWORD_ERROR;
						// 在asmack中如果密码错误，必须断开连接，重新连接。
						TApplication.connection.disconnect();
						TApplication.instance.connectChatServer();
					}
				} finally{
					Intent intent = new Intent(Const.ACTION_LOGIN);
					intent.putExtra(Const.KEY_DATA, status);
					TApplication.instance.sendBroadcast(intent);
				}
				
			};

		}.start();

	}
}
