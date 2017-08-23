package controls;

import java.util.ArrayList;
import java.util.List;

import com.example.aa.R;

import adapter.AdapterSlideMenu;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SliderMenuView {
	
	private int h;
	private Activity mActivity;
	private List<SliderMenuItem> mMenuList;
	private boolean mIsClosed;
	private RelativeLayout layBottomBox;
	private onSlideMenuListener mSlideManuListener;
	
	public interface onSlideMenuListener
	{
		public abstract void OnSlideMenuItemClick(View pView, SliderMenuItem pSlideMenuItem);
	}
	
	public SliderMenuView(Activity pActivity){
		mActivity = pActivity;
		mSlideManuListener =(onSlideMenuListener) pActivity;
		InitVariable();
		InitView();
		InitListeners();
	}
	public void InitVariable(){
		mMenuList = new ArrayList();
		mIsClosed = true;
	}
    public void InitView(){
    	layBottomBox = (RelativeLayout) mActivity.findViewById(R.id.IncludeBottom);
		
	}
    public void InitListeners(){
    	layBottomBox.setOnClickListener(new OnSlideMenuClick());
	
    }

	private void Open(){
		RelativeLayout.LayoutParams _laLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		_laLayoutParams.addRule(RelativeLayout.BELOW, R.id.IncludeTitle);
		
		layBottomBox.setLayoutParams(_laLayoutParams);
		
		mIsClosed = false;
	}
	private void Close(){ 
		RelativeLayout rLayout = (RelativeLayout) mActivity.findViewById(R.id.layBottomBar);
		h = rLayout.getLayoutParams().height;
		RelativeLayout.LayoutParams _laLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,h);
		_laLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		layBottomBox.setLayoutParams(_laLayoutParams);
		
		mIsClosed = true;
		
	}
	public void Toggle()
	{
		if(mIsClosed)
		{
			Open();
		}
		else{
			Close();
		}
	}
	
	
	public void Add(SliderMenuItem pSliderMenuItem){
		mMenuList.add(pSliderMenuItem);
		
	}
	
	public void BindList(){
		AdapterSlideMenu _AdapterSlideMenu = new AdapterSlideMenu(mActivity, mMenuList);
		
		ListView _ListView = (ListView) mActivity.findViewById(R.id.lvSlideList);
		_ListView.setAdapter(_AdapterSlideMenu);
		_ListView.setOnItemClickListener(new OnSlideMenuItemClick());
	}
	
	private class  OnSlideMenuClick implements OnClickListener
	{
		@Override
		public void onClick(View p_View) {
			Toggle();
		}
	}
	

	private class OnSlideMenuItemClick implements OnItemClickListener
	{


		@Override
		public void onItemClick(AdapterView<?> pAdapterView, View pView, int pPosition,long arg3) {
			SliderMenuItem _SliderMenuItem = (SliderMenuItem) pAdapterView.getItemAtPosition(pPosition);
			mSlideManuListener.OnSlideMenuItemClick(pView, _SliderMenuItem);
		}	
}
}
