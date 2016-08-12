package com.tarena.allrun.view;

import com.tarena.allrun.R;
import com.tarena.allrun.fragment.DiscoveryFragment;
import com.tarena.allrun.fragment.MeFragment;
import com.tarena.allrun.fragment.MsgFragment;
import com.tarena.allrun.fragment.SportFragment;
import com.tarena.allrun.util.ExceptionUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainFragmentActivity extends FragmentActivity{

	private Fragment[] fragments;
	
	private Button btSport;
	private Button btMsg;
	private Button btDiscovery;
	private Button btMe;
	
	private Button[] btnArray = new Button[4];
	
	int currentIndex = 0;
	int selectedIndex;

//	private FragmentManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		
		try {
			setView();
			setListeners();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
	}
	private void setListeners() {
		// TODO Auto-generated method stub
		MyListener listener = new MyListener();
		for (Button btn : btnArray) {
			btn.setOnClickListener(listener);
		}
	}
	private void setView() {
		// TODO Auto-generated method stub
		btnArray[0] = (Button)this.findViewById(R.id.btn_main_fragment_sport);
		btnArray[1] = (Button)this.findViewById(R.id.btn_main_fragment_msg);
		btnArray[2] = (Button)this.findViewById(R.id.btn_main_fragment_discovery);
		btnArray[3] = (Button)this.findViewById(R.id.btn_main_fragment_me);
		btnArray[0].setSelected(true);
		
		fragments = new Fragment[4];
		fragments[0] = new SportFragment();
		fragments[1] = new MsgFragment();
		fragments[2] = new DiscoveryFragment();
		fragments[3] = new MeFragment();
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fragment_container, fragments[0]);
		transaction.show(fragments[0]);
		transaction.commit();		
	}
	
	class MyListener implements OnClickListener{


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				switch(v.getId()){
				case R.id.btn_main_fragment_sport:
					selectedIndex = 0;
					break;
				case R.id.btn_main_fragment_msg:
					selectedIndex = 1;
					break;
				case R.id.btn_main_fragment_discovery:
					selectedIndex = 2;
					break;
				case R.id.btn_main_fragment_me:
					selectedIndex = 3;
					break;
				}
				
				repalceFragment();
//				btnArray[selectedIndex].setSelected(true);
//				for (int i = 0; i < 4; i++) {
//					if (i == selectedIndex) {
//						btnArray[i].setSelected(true);
//					}else{
//						btnArray[i].setSelected(false);
//					}
//				}
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			}

		}

		private void repalceFragment() {
			if (selectedIndex != currentIndex) {
				FragmentManager manager = getSupportFragmentManager();
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.hide(fragments[currentIndex]);
				if (!fragments[selectedIndex].isAdded()) {
					transaction.add(R.id.fragment_container, fragments[selectedIndex]);
				}
				transaction.show(fragments[selectedIndex]);
				transaction.commit();
				
				btnArray[currentIndex].setSelected(false);
				btnArray[selectedIndex].setSelected(true);
				currentIndex = selectedIndex;
			}
		}
		
	}
}
