package com.mobidever.activity;



import com.mobidever.activity.base.ActivityFrame;
import com.mobidever.adapter.AdapterAppGrid;
import com.mobidever.business.BusinessDataBackup;
import com.mobidever.controls.SlideMenuItem;
import com.mobidever.controls.SlideMenuView.OnSlideMenuListener;

import com.mobidever.R;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityMain extends ActivityFrame implements OnSlideMenuListener{
	
	private GridView gvAppGrid;
	private AlertDialog mDatabaseBackupDialog;
	private AdapterAppGrid mAdapterAppGrid;
	private BusinessDataBackup mBusinessDataBackup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.main_body);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        CreateSlideMenu(R.array.SlideMenuActivityMain);
    }

	public void InitVariable()
    {
    	mAdapterAppGrid = new AdapterAppGrid(this);
    	mBusinessDataBackup = new BusinessDataBackup(this);
    }
    
    public void InitView()
    {
    	gvAppGrid = (GridView) findViewById(R.id.gvAppGrid);
    }
    
    public void InitListeners()
    {
    	gvAppGrid.setOnItemClickListener(new onAppGridItemClickListener());
    }
    
    private class onAppGridItemClickListener implements OnItemClickListener
    {
    	
		public void onItemClick(AdapterView p_Adapter, View p_View, int p_Position,long arg3) {
			String _MenuName = (String)p_Adapter.getAdapter().getItem(p_Position);
			if(_MenuName.equals(getString(R.string.appGridTextUserManage)))
			{
				OpenActivity(ActivityUser.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextAccountBookManage)))
			{
				OpenActivity(ActivityAccountBook.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextCategoryManage)))
			{
				OpenActivity(ActivityCategory.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextPayoutAdd)))
			{
				OpenActivity(ActivityPayoutAddOrEdit.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextPayoutManage)))
			{
				OpenActivity(ActivityPayout.class);
				return;
			}
			if(_MenuName.equals(getString(R.string.appGridTextStatisticsManage)))
			{
				OpenActivity(ActivityStatistics.class);
				return;
			}
		}
	}
    
    public void BindData()
    {
    	gvAppGrid.setAdapter(mAdapterAppGrid);
    }

	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
			OpenActivity(AboutActivity.class);
	}
	
	
}