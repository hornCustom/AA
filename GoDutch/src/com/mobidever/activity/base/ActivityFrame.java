package com.mobidever.activity.base;
import com.mobidever.controls.SlideMenuItem;
import com.mobidever.controls.SlideMenuView;
import com.mobidever.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityFrame extends ActivityBase {
	private SlideMenuView mSlideMenuView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		View view = findViewById(R.id.ivAppBack);
		OnBackListener _OnBackListener = new OnBackListener();
		view.setOnClickListener(_OnBackListener);
	}
	private class OnBackListener implements android.view.View.OnClickListener{
		public void onClick(View v) {
			finish();
		}
	}
	protected void onDestroy() {
		super.onDestroy();
	}
	protected void HideTitleBackButton()
	{
		findViewById(R.id.ivAppBack).setVisibility(View.GONE);
	}
	protected void AppendMainBody(int pResID) {
		LinearLayout _MainBody = (LinearLayout) findViewById(R.id.layMainBody);
		View _View = LayoutInflater.from(this).inflate(pResID, null);
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(_View, _LayoutParams);
	}
	
	protected void AppendMainBody(View pView)
	{
		LinearLayout _MainBody = (LinearLayout)findViewById(getMainBodyLayoutID());		
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(pView,_LayoutParams);
	}
	protected void SetTopBarTitle(String pText) {
		TextView _tvTitle = (TextView) findViewById(R.id.tvTopTitle);
		_tvTitle.setText(pText);
	}
	private int getMainBodyLayoutID() {
		return R.id.layMainBody;
	}
	protected void CreateSlideMenu(int pResID)
	{
		mSlideMenuView = new SlideMenuView(this);
		String[] _MenuItemArray = getResources().getStringArray(pResID);
		for (int i = 0; i < _MenuItemArray.length; i++) {
			SlideMenuItem _Item = new SlideMenuItem(i,_MenuItemArray[i]);
			mSlideMenuView.Add(_Item);
		}
		mSlideMenuView.BindList();
	}
	protected void RemoveBottomBox()
	{
		mSlideMenuView = new SlideMenuView(this);
		mSlideMenuView.RemoveBottomBox();
	}
	protected void SlideMenuToggle() {
		mSlideMenuView.Toggle();
	}
	protected void CreateMenu(Menu p_Menu)
	{
		int _Item[] = {1,2};
		int _GroupID = 0;
		int _Order = 0;
		for(int i=0;i<_Item.length;i++)
		{
			switch(_Item[i])
			{
			case 1:
				p_Menu.add(_GroupID, _Item[i], _Order, R.string.MenuTextEdit);
				break;
			case 2:
				p_Menu.add(_GroupID, _Item[i], _Order, R.string.MenuTextDelete);
				break;
			default:
				break;
			}
		}
	}
}
