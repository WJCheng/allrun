package com.tarena.allrun.fragment;

import com.tarena.allrun.R;
import com.tarena.allrun.util.ExceptionUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportFragment extends Fragment {

	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			view = View.inflate(getActivity(), R.layout.fragment_sport, null);
			setViews();
			setListeners();
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
		return view;
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		
	}

	private void setViews() {
		// TODO Auto-generated method stub
		
	}
}
