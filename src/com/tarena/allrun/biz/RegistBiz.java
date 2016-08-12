package com.tarena.allrun.biz;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.UUID;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.tarena.allrun.TApplication;
import com.tarena.allrun.entity.UserEntity;
import com.tarena.allrun.util.Const;
import com.tarena.allrun.util.MD5Util;

import android.app.IntentService;
import android.content.Intent;
import android.net.wifi.WifiConfiguration.Status;
import android.util.Log;
import android.widget.Toast;

public class RegistBiz extends IntentService{

	int status = Const.STATUS_OK;
	
	public RegistBiz() {
		super("RegistBiz");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		status = Const.STATUS_OK;
		UserEntity user = (UserEntity)intent.getSerializableExtra(Const.KEY_DATA);
		
//		if(!TApplication.connection.isConnected()){
//			Toast.makeText(this, "网络连接不通...", Toast.LENGTH_SHORT).show();
//			return;
//		}
		
		try {
			AccountManager am = TApplication.connection.getAccountManager();
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("name", user.getName());
			am.createAccount(user.getUser(), user.getPwd(), map);
			
			sendToTomcat(intent, user);
			
		} catch (XMPPException e) {
			status = Const.STATUS_REGISTER_FAILURE;
			e.printStackTrace();
		}catch(IllegalStateException e){
			status = Const.STATUS_REGISTER_FAILURE;
			e.printStackTrace();
		}finally{
			Intent intent2 = new Intent(Const.ACTION_REGISTER);
			intent2.putExtra(Const.KEY_DATA, status);
			sendBroadcast(intent2);
		}
	}

	private void sendToTomcat(Intent intent, UserEntity user) {
		// TODO Auto-generated method stub
		byte[] imageData = intent.getByteArrayExtra("imageData");
		String url = "http://"+"124.207.192.18"+":8080/allRunServer/register.jsp";
		
		HttpUtils httpUtils = new HttpUtils(6000);
		RequestParams requestParams = new RequestParams();
		requestParams.addBodyParameter("username", user.getUser());
		requestParams.addBodyParameter("nickname", user.getName());
		String pwdString = user.getPwd();
		pwdString = MD5Util.getStringMD5(pwdString);
		requestParams.addBodyParameter("md5password", pwdString);
		
		String filename = UUID.randomUUID().toString()+".png";
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		requestParams.addBodyParameter("file", 
				byteArrayInputStream,
				byteArrayInputStream.available(),
				filename,
				"image/png");
		httpUtils.send(HttpMethod.POST, url, requestParams, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String  jsonString = responseInfo.result;
//				Log.i("ii", "jsonString = "+ jsonString);
				try {
					JSONObject jsonObject = new JSONObject(jsonString);
					String statusStr = jsonObject.getString("status");
					Log.i("info", "status"+status);
					if("0".equals(statusStr)){
						status = Const.STATUS_OK;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				Log.i("info", "failed");
			}
			
		});
	}

}
