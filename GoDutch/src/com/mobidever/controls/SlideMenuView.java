package com.mobidever.controls;

import java.util.ArrayList;
import java.util.List;

import com.mobidever.activity.base.ActivityFrame;
import com.mobidever.adapter.AdapterAppGrid;
import com.mobidever.adapter.AdapterSlideMenu;

import com.mobidever.R;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class SlideMenuView {
	private int h;
	private Activity mActivity;
	private List mMenuList;
	private boolean mIsClosed;
	private RelativeLayout layBottomBox;
	private OnSlideMenuListener mSlideMenuListener;
	
	
	public interface OnSlideMenuListener
	{
		public abstract void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem);
	}

	
	public SlideMenuView(Activity pActivity) {
		mActivity = pActivity;
		InitView();
		if (pActivity instanceof OnSlideMenuListener) {
			mSlideMenuListener = (OnSlideMenuListener) pActivity;
			InitVariable();
			InitListeners();
		}
	}

	public void InitVariable() {
		mMenuList = new ArrayList();
		mIsClosed = true;
	}

	public void InitView() {
		layBottomBox = (RelativeLayout) mActivity.findViewById(R.id.IncludeBottom);
	}

	public void InitListeners() {
		layBottomBox.setOnClickListener(new OnSlideMenuClick());
		layBottomBox.setFocusableInTouchMode(true);
		layBottomBox.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_UP) {
					Toggle();
				}
				
				return false;
			}
		});
	}

	private void Open() {
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.IncludeTitle);
		
		layBottomBox.setLayoutParams(layoutParams);
		
		mIsClosed = false;
	}

	private void Close() {
		RelativeLayout rLayout = (RelativeLayout)mActivity.findViewById(R.id.layBottomBar);
		h =	rLayout.getLayoutParams().height;
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, h);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		layBottomBox.setLayoutParams(layoutParams);
		
		mIsClosed = true;
	}

	public void Toggle() {
		if(mIsClosed)
		{
			Open();
		}
		else {
			Close();
		}
	}
	
	public void RemoveBottomBox()
	{
		RelativeLayout _MainLayout = (RelativeLayout)mActivity.findViewById(R.id.layMainLayout);
		_MainLayout.removeView(layBottomBox);
		layBottomBox = null;
	}

	public void Add(SlideMenuItem pSliderMenuItem) {
		mMenuList.add(pSliderMenuItem);
	}

	public void BindList() {
		AdapterSlideMenu _AdapterSlideMenu = new AdapterSlideMenu(mActivity, mMenuList);
		
		ListView _ListView = (ListView) mActivity.findViewById(R.id.lvSlideList);
		_ListView.setAdapter(_AdapterSlideMenu);
		_ListView.setOnItemClickListener(new OnSlideMenuItemClick());
	}

	private class OnSlideMenuClick implements OnClickListener
	{

		public void onClick(View v) {
			Toggle();
		}
		
	}
	
	private class OnSlideMenuItemClick implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> pAdapterView, View pView, int pPosition,
				long arg3) {
			SlideMenuItem _SlideMenuItem = (SlideMenuItem) pAdapterView.getItemAtPosition(pPosition);
			mSlideMenuListener.onSlideMenuItemClick(pView, _SlideMenuItem);
		}
		
	}
}
