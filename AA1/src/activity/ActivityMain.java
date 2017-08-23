package activity;



import com.example.aa.R;

import controls.SliderMenuItem;
import controls.SliderMenuView.onSlideMenuListener;
import activity.base.ActivityFrame;
import adapter.AdapterAppGrid;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ActivityMain extends ActivityFrame implements onSlideMenuListener{
	
	private GridView gvAppGrid;
	
	private AdapterAppGrid mAdapterAppGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.main_body);
        
        InitVarible();
        InitView();
        InitListeners();
        BindData();
        CreateSlideMenu(R.array.SlideMenuActivityMain);
    }
    
    public void InitVarible() 
    {
		mAdapterAppGrid =  new AdapterAppGrid(this);
	}
    public void InitView() 
    {
		gvAppGrid = (GridView) findViewById(R.id.gvAppGrid);
	}
    public void InitListeners() 
    {
    	gvAppGrid.setOnItemClickListener(new OnAppGridTiemClickListener());
    }
    private class OnAppGridTiemClickListener implements OnItemClickListener
    {

		@Override
		public void onItemClick(AdapterView p_Adapter, View p_View, int p_Position,
				long arg3) {
			String _MenuName = (String) p_Adapter.getAdapter().getItem(p_Position);
			if (_MenuName.equals(getString(R.string.renyuanguanli))) 
			{
				OpenActivity(ActivityUser.class);
				return;
			}
			
		}
    	
    }
    
    
    public void BindData() 
    {
		gvAppGrid.setAdapter(mAdapterAppGrid);
	}

	public void OnSlideMenuItemClick(View pView, SliderMenuItem pSlideMenuItem) {
		Toast.makeText(this, pSlideMenuItem.getTitle(), 1).show();
		
	}
    
}
