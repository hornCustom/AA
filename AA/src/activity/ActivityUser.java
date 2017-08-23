package activity;

import utility.RegexTools;
import model.ModelUser;

import businness.BusinessUser;

import com.example.aa.R;

import adapter.AdapterUser;
import controls.SliderMenuItem;
import controls.SliderMenuView.onSlideMenuListener;
import activity.base.ActivityFrame;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityUser extends ActivityFrame implements onSlideMenuListener{
     private ListView lvUserList;
     
     private AdapterUser mAdapterUser;
     private BusinessUser mBusinessUser;
     private ModelUser mSelectModlUser;
     
     @Override
     public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 AppendMainBody(R.layout.user);
    	 InitVariable();
    	 InitView();
    	 InitListeners();
    	 BindData();
    	 CreateSlideMenu(R.array.SlieMenuUser);
     }
     
    public void InitVariable(){
    	mBusinessUser = new BusinessUser(this);
    } 
    public void InitView(){
    	lvUserList = (ListView)findViewById(R.id.lvUserList);
    }
    public void InitListeners(){
    	registerForContextMenu(lvUserList);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	AdapterContextMenuInfo _AdapterContextMenuInfo =(AdapterContextMenuInfo) menuInfo;
    	ListAdapter _ListAdapter = lvUserList.getAdapter();
    	
    mSelectModlUser = (ModelUser)_ListAdapter.getItem(_AdapterContextMenuInfo.position);
    
    menu.setHeaderIcon(R.drawable.user_big_icon);
    menu.setHeaderTitle(mSelectModlUser.getUserName());
    
    CreateMenu(menu);
    }  

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case 1:
			ShowUserAddOrEditDialog(mSelectModlUser);
			break;
		case 2:
			Delete();
		default:
			break;
		}
    	return super.onContextItemSelected(item);
    }

public void BindData(){
	mAdapterUser = new AdapterUser(this);
	lvUserList.setAdapter(mAdapterUser);
    }

    @Override
	public void OnSlideMenuItemClick(View pView, SliderMenuItem pSlideMenuItem) {
    	SlideMenuToggle();
		if (pSlideMenuItem.getmItemID() == 0) {
			ShowUserAddOrEditDialog(null);
		}
	}
    
    
	private void ShowUserAddOrEditDialog(ModelUser pModelUser)
    {
    	View _View = getLayoutInflater().inflate(R.layout.user_add_or_edit, null);
    	EditText _etUserName = (EditText) _View.findViewById(R.id.etUserName);
    	
    	if (pModelUser != null) {
			_etUserName.setText(pModelUser.getUserName());
		}
    	
    	String _Title;
    	
    	if (pModelUser == null) {
			_Title = getString(R.string.DialogTitleUser,new Object[]{getString(R.string.TitleAdd)});
		}
    	else {
    		_Title = getString(R.string.DialogTitleUser,new Object[]{getString(R.string.TitleEdit)});
		}
    	
    	AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
    	_Builder.setTitle(_Title)
    	        .setView(_View)
    	        .setIcon(R.drawable.user_big_icon)
    	        .setNeutralButton(getString(R.string.ButtonTextSave), new OnAddOrEditUserListener(pModelUser,_etUserName,true))
				.setNegativeButton(getString(R.string.ButtonTextCancel), new OnAddOrEditUserListener(null,null,false))
				.show();
    }
	private class OnAddOrEditUserListener implements DialogInterface.OnClickListener
	{
		private ModelUser mModelUser;
		private EditText etUserName;
		private boolean mIsSaveButton;
		
		public OnAddOrEditUserListener(ModelUser pModelUser,EditText petUserName,Boolean pIsSaveButton)
		{
			mModelUser = pModelUser;
			etUserName = petUserName;
			mIsSaveButton = pIsSaveButton;
		}
		
		public void onClick(DialogInterface dialog, int which) {
			if(mIsSaveButton == false)
			{
				SetAlertDialogIsClose(dialog, true);
				return;
			}
			
			if (mModelUser == null) {
				mModelUser = new ModelUser();
			}
			
			String _UserName = etUserName.getText().toString().trim();
			
			boolean _CheckResult = RegexTools.IsChineseEnglishNum(_UserName);
			
			if (!_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextChineseEngilshNum,new Object[]{etUserName.getHint()}), SHOW_TIME).show();
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			_CheckResult = mBusinessUser.IsExistUserByUserName(_UserName, mModelUser.getUserID());
			
			if (_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextUserExist), SHOW_TIME).show();
				
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			mModelUser.setUserName(etUserName.getText().toString());
			
			boolean _Result = false;
			
			if (mModelUser.getUserID() == 0) {
				_Result = mBusinessUser.InsertUser(mModelUser);
			}
			else {
				_Result = mBusinessUser.UpdateUserByUserID(mModelUser);
			}
			
			if (_Result) {
				BindData();
			}
			else {
				Toast.makeText(ActivityUser.this, getString(R.string.TipsAddFail), 1).show();
			}
		}
		
	}

	private void Delete() {
		String _Message = getString(R.string.DialogMessageUserDelete,new Object[]{mSelectModlUser.getUserName()});
		ShowAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener());
	}
	
	private class OnDeleteClickListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialog, int which) {
			boolean _Result = mBusinessUser.HideUserByUserID(mSelectModlUser.getUserID());
			
			if (_Result == true) {
				BindData();
			}
		}
	}
}