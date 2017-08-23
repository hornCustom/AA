package activity.base;

import com.example.aa.R;

import controls.SliderMenuItem;
import controls.SliderMenuView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ActivityFrame extends ActivityBase{
	
	
	private SliderMenuView mSlideMenuView;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.activity_main);
        
	}
	protected void AppendMainBody(int pResID) {
		LinearLayout _MainBody = (LinearLayout) findViewById(R.id.layMainBody);
		
		View _View = LayoutInflater.from(this).inflate(pResID,null);
		RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
	    _MainBody.addView(_View, _LayoutParams);
	}
	protected void CreateSlideMenu(int pResID)
	{
		mSlideMenuView = new SliderMenuView(this);
		String[] _MenuItemArray = getResources().getStringArray(pResID);
		
		for (int i = 0; i < _MenuItemArray.length; i++) {
			SliderMenuItem _Item = new SliderMenuItem(i,_MenuItemArray[i]);
			
			mSlideMenuView.Add(_Item);
		}
		
		mSlideMenuView.BindList();
	}
	protected void SlideMenuToggle(){
		mSlideMenuView.Toggle();
	}
	
	protected void CreateMenu(Menu p_Menu)
	{
		int _Item[] = {1,2};
	    int _GroupID = 0;
	    int _Order = 0;
	    
	    for(int i=0;i<_Item.length;i++)
	    {
	    	switch (_Item[i]) 
	    	{
			case 1:
				p_Menu.add(_GroupID,_Item[i], _Order,R.string.MenuTextEdit);
				break;
			case 2:
				p_Menu.add(_GroupID,_Item[i], _Order,R.string.MenuTextDelete);
			default:
				break;
			}
	    	  
	    }
	}
}

