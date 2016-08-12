package com.tarena.allrun.fragment;

import com.tarena.allrun.NearbyActivity;
import com.tarena.allrun.R;
import com.tarena.allrun.util.ExceptionUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DiscoveryFragment extends Fragment {

	View view;
	private LinearLayout club;
	private LinearLayout sportGroup;
	private LinearLayout nearby;
	private LinearLayout friend;
	private LinearLayout mall;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			view = View.inflate(getActivity(), R.layout.fragment_discovery, null);
			setViews();
			setListeners();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
		return view;
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		DiscoverListener discoverListener = new DiscoverListener();
		club.setOnClickListener(discoverListener);
		sportGroup.setOnClickListener(discoverListener);
		nearby.setOnClickListener(discoverListener);
		friend.setOnClickListener(discoverListener);
		mall.setOnClickListener(discoverListener);
	}

	private void setViews() {
		// TODO Auto-generated method stub
		club = (LinearLayout)view.findViewById(R.id.ll_discover_club);
		sportGroup = (LinearLayout)view.findViewById(R.id.ll_discover_sportGroup);
		nearby = (LinearLayout)view.findViewById(R.id.ll_discover_nearby);
		friend = (LinearLayout)view.findViewById(R.id.ll_discover_friend);
		mall = (LinearLayout)view.findViewById(R.id.ll_discover_mall);
	}
	
	class DiscoverListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				switch(v.getId()){
				case R.id.ll_discover_club:
					break;
				case R.id.ll_discover_sportGroup:
					break;
				case R.id.ll_discover_nearby:
					startActivity(new Intent(getActivity(), NearbyActivity.class));
					break;
				case R.id.ll_discover_friend:
					break;
				case R.id.ll_discover_mall:
					break;
				}
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			}
		}
		
	}
}
