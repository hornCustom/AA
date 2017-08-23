package com.mobidever.activity;

import com.mobidever.activity.base.ActivityFrame;
import com.mobidever.adapter.AdapterAccountBook;
import com.mobidever.business.BusinessAccountBook;
import com.mobidever.controls.SlideMenuItem;
import com.mobidever.controls.SlideMenuView.OnSlideMenuListener;
import com.mobidever.model.ModelAccountBook;
import com.mobidever.utility.RegexTools;

import com.mobidever.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityAccountBook extends ActivityFrame implements OnSlideMenuListener {
	
	private ListView lvAccountBookList;
	
	private AdapterAccountBook mAdapterAccountBook;
	private BusinessAccountBook mBusinessAccountBook;
	private ModelAccountBook mSelectModlAccountBook;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.account_book);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        CreateSlideMenu(R.array.SlideMenuAccountBook);
    }
    
    public void InitVariable()
    {
    	mBusinessAccountBook = new BusinessAccountBook(this);
    }
    
    public void InitView()
    {
    	lvAccountBookList = (ListView) findViewById(R.id.lvAccountBookList);
    }
    
    public void InitListeners()
    {
    	registerForContextMenu(lvAccountBookList);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
    	ListAdapter _ListAdapter = lvAccountBookList.getAdapter();
    	
    	mSelectModlAccountBook = (ModelAccountBook)_ListAdapter.getItem(_AdapterContextMenuInfo.position);
    	
    	menu.setHeaderIcon(R.drawable.account_book_small_icon);
    	menu.setHeaderTitle(mSelectModlAccountBook.GetAccountBookName());
    	
    	CreateMenu(menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {

    	switch (item.getItemId()) {
		case 1:
			ShowAccountBookAddOrEditDialog(mSelectModlAccountBook);
			break;
		case 2:
			Delete();
			break;
		default:
			break;
		}

    	return super.onContextItemSelected(item);
    }
    
    public void BindData()
    {
    	mAdapterAccountBook = new AdapterAccountBook(this);
    	lvAccountBookList.setAdapter(mAdapterAccountBook);
    	SetTitle();
    }

	public void onSlideMenuItemClick(View pView, SlideMenuItem pSlideMenuItem) {
		SlideMenuToggle();
		if (pSlideMenuItem.getItemID() == 0) {
			ShowAccountBookAddOrEditDialog(null);
		}
	}
	
	private void SetTitle() {
		int _Count = mAdapterAccountBook.getCount();
		String _Titel = getString(R.string.ActivityTitleAccountBook, new Object[]{_Count});
		SetTopBarTitle(_Titel);
	}
	
	private void ShowAccountBookAddOrEditDialog(ModelAccountBook pModelAccountBook)
	{
		View _View = GetLayouInflater().inflate(R.layout.account_book_add_or_edit, null);
		
		EditText _etAccountBookName = (EditText) _View.findViewById(R.id.etAccountBookName);
		CheckBox _chkIsDefault = (CheckBox)_View.findViewById(R.id.chkIsDefault);
		
		if (pModelAccountBook != null) {
			_etAccountBookName.setText(pModelAccountBook.GetAccountBookName());
		}
		
		String _Title;
		
		if(pModelAccountBook == null)
		{
			_Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleAdd)});
		}
		else {
			_Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleEdit)});
		}
		
		AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
		_Builder.setTitle(_Title)
				.setView(_View)
				.setIcon(R.drawable.account_book_big_icon)
				.setNeutralButton(getString(R.string.ButtonTextSave), new OnAddOrEditAccountBookListener(pModelAccountBook,_etAccountBookName,_chkIsDefault,true))
				.setNegativeButton(getString(R.string.ButtonTextCancel), new OnAddOrEditAccountBookListener(null,null,null,false))
				.show();
	}
	
	private class OnAddOrEditAccountBookListener implements DialogInterface.OnClickListener
	{
		private ModelAccountBook mModelAccountBook;
		private EditText etAccountBookName;
		private boolean mIsSaveButton;
		private CheckBox chkIsDefault;
		
		public OnAddOrEditAccountBookListener(ModelAccountBook pModelAccountBook,EditText petAccountBookName,CheckBox pchkIsDefault,Boolean pIsSaveButton)
		{
			mModelAccountBook = pModelAccountBook;
			etAccountBookName = petAccountBookName;
			mIsSaveButton = pIsSaveButton;
			chkIsDefault = pchkIsDefault;
		}
		
		public void onClick(DialogInterface dialog, int which) {
			if(mIsSaveButton == false)
			{
				SetAlertDialogIsClose(dialog, true);
				return;
			}
			
			if (mModelAccountBook == null) {
				mModelAccountBook = new ModelAccountBook();
			}
			
			String _AccountBookName = etAccountBookName.getText().toString().trim();
			
			boolean _CheckResult = RegexTools.IsChineseEnglishNum(_AccountBookName);
			
			if (!_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextChineseEnglishNum,new Object[]{etAccountBookName.getHint()}), SHOW_TIME).show();
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			_CheckResult = mBusinessAccountBook.IsExistAccountBookByAccountBookName(_AccountBookName, mModelAccountBook.GetAccountBookID());
			
			if (_CheckResult) {
				Toast.makeText(getApplicationContext(), getString(R.string.CheckDataTextAccountBookExist), SHOW_TIME).show();
				
				SetAlertDialogIsClose(dialog, false);
				return;
			}
			else {
				SetAlertDialogIsClose(dialog, true);
			}
			
			mModelAccountBook.SetAccountBookName(etAccountBookName.getText().toString());
			
			mModelAccountBook.SetAccountBookName(String.valueOf(etAccountBookName.getText().toString().trim()));
			if(chkIsDefault.isChecked())
			{
				mModelAccountBook.SetIsDefault(1);
			}
			else {
				mModelAccountBook.SetIsDefault(0);
			}
			
			if(mModelAccountBook.GetAccountBookID() > 0)
			{
				mModelAccountBook.SetIsDefault(1);
			}
			
			boolean _Result = false;
			
			if (mModelAccountBook.GetAccountBookID() == 0) {
				_Result = mBusinessAccountBook.InsertAccountBook(mModelAccountBook);
			}
			else {
				_Result = mBusinessAccountBook.UpdateAccountBookByAccountBookID(mModelAccountBook);
			}
			
			if (_Result) {
				BindData();
			}
			else {
				Toast.makeText(ActivityAccountBook.this, getString(R.string.TipsAddFail), 1).show();
			}
		}
		
	}

	private void Delete() {
		String _Message = getString(R.string.DialogMessageAccountBookDelete,new Object[]{mSelectModlAccountBook.GetAccountBookName()});
		ShowAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener());
	}
	
	private class OnDeleteClickListener implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialog, int which) {
			boolean _Result = mBusinessAccountBook.DeleteAccountBookByAccountBookID(mSelectModlAccountBook.GetAccountBookID());
			
			if (_Result == true) {
				BindData();
			}
		}
	}
}