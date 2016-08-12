package com.tarena.allrun.view;

import org.jivesoftware.smackx.workgroup.agent.UserRequest;

import com.tarena.allrun.R;
import com.tarena.allrun.R.id;
import com.tarena.allrun.R.layout;
import com.tarena.allrun.R.menu;
import com.tarena.allrun.TApplication;
import com.tarena.allrun.biz.LoginBiz;
import com.tarena.allrun.entity.UserEntity;
import com.tarena.allrun.util.Const;
import com.tarena.allrun.util.NetworkUtil;
import com.tarena.allrun.util.Tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btLogin;
	private Button btRegist;
	private EditText etLoginUser;
	private EditText etLoginPwd;
	private UserEntity userEntity;
	private LoginReceiver receiver;
	private IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		setViews();
		setListeners();
		
		receiver = new LoginReceiver();
		filter = new IntentFilter();
		filter.addAction(Const.ACTION_LOGIN);
		this.registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(receiver);
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		MyListener listener = new MyListener();
		btLogin.setOnClickListener(listener);
		btRegist.setOnClickListener(listener);
	}

	private void setViews() {
		
		btLogin = (Button)this.findViewById(R.id.btLogin);
		btRegist = (Button)this.findViewById(R.id.btRegist);
		
		etLoginUser = (EditText)this.findViewById(R.id.et_login_user);
		etLoginPwd = (EditText)this.findViewById(R.id.et_login_password);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MyListener implements OnClickListener{

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btLogin:
				login();
				break;
			case R.id.btRegist:
//				Tools.showViewInfo(LoginActivity.this, "hello");
//				showRegistActivity();
				NetworkUtil.checkNetworkState(LoginActivity.this);
				break;

			default:
				break;
			}
		}
		
	}

	public void login() {
		// TODO Auto-generated method stub
		userEntity = getUserInfo();
		LoginBiz loginBiz = new LoginBiz();
		loginBiz.login(userEntity);
		btLogin.setEnabled(false);
		
		Tools.showProgressDialog(this, "ÕýÔÚµÇÂ¼...");
	}
	
	class LoginReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Tools.closeProgressDialog();
			int status = intent.getIntExtra(Const.KEY_DATA, -1);
			if (status == Const.STATUS_OK) {
				userEntity.setUser(userEntity.getUser() + "@" + TApplication.config.serviceName);
				TApplication.currentUser = userEntity;
				Tools.showToast(context, "µÇÂ¼³É¹¦");
				startActivity(new Intent(context,
						 MainFragmentActivity.class));
			}
			if(status == Const.STATUS_PASSWORD_ERROR){
				Tools.showToast(context, "ÃÜÂë´íÎó");
				btLogin.setEnabled(true);
			}
			if(status == Const.STATUS_CONN_FAILED){
				Tools.showToast(context, "·þÎñÆ÷Á¬½ÓÊ§°Ü£¬Çë¼ì²éÍøÂç£¡");
				btLogin.setEnabled(true);
			}
			
		}
		
	}

	public void showRegistActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	/**
	 * 
	 */
	private UserEntity getUserInfo() {
		// TODO Auto-generated method stub
		UserEntity userEntity = new UserEntity();
		userEntity.setUser(etLoginUser.getText().toString());
		userEntity.setPwd(etLoginPwd.getText().toString());
		return userEntity;
	}

	private boolean varifyInfo(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return false;
	}
}
