package com.mobidever.activity.base;
import java.lang.reflect.Field;
import com.mobidever.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;
public class ActivityBase extends Activity {
	protected static final int SHOW_TIME = 1;
	private ProgressDialog mProgressDialog;
	protected void ShowMsg(String pMsg) {
		Toast.makeText(this, pMsg, 1).show();
	}
	protected void OpenActivity(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pClass);
		startActivity(_Intent);
	}
	protected LayoutInflater GetLayouInflater() {
		LayoutInflater _LayoutInflater = LayoutInflater.from(this);
		return _LayoutInflater;
	}
	public void SetAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field _Field = pDialog.getClass().getSuperclass().getDeclaredField("mShowing");
			_Field.setAccessible(true);
			_Field.set(pDialog, pIsClose);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected AlertDialog ShowAlertDialog(int p_TitelResID, String p_Message,DialogInterface.OnClickListener p_ClickListener) 
	{
		String _Title = getResources().getString(p_TitelResID);
		return ShowAlertDialog(_Title, p_Message, p_ClickListener);
	}
	protected AlertDialog ShowAlertDialog(String p_Title, String p_Message,DialogInterface.OnClickListener p_ClickListener) 
	{
		return new AlertDialog.Builder(this).setTitle(p_Title)
				.setMessage(p_Message)
				.setPositiveButton(R.string.ButtonTextYes, p_ClickListener)
				.setNegativeButton(R.string.ButtonTextNo, null).show();
	}
	protected void ShowProgressDialog(int p_TitleResID, int pMessageResID) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(getString(p_TitleResID));
		mProgressDialog.setMessage(getString(pMessageResID));
		mProgressDialog.show();
	}
	protected void DismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
}
