package com.mobidever.business;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.mobidever.business.base.BusinessBase;
import com.mobidever.utility.FileUtil;


import android.content.Context;
import android.content.SharedPreferences;

public class BusinessDataBackup extends BusinessBase {

	public BusinessDataBackup(Context p_Context) {
		super(p_Context);
	}

	public boolean DatabaseBackup(Date p_Backup) {
		boolean _Result = false;
		try {
			File _SourceFile = new File("/data/data/" + GetContext().getPackageName() + "/databases/GoDutchDataBase");
			
			if(_SourceFile.exists())
			{
				File _FileDir = new File("/sdcard/GoDutch/DataBaseBak/");
				if (!_FileDir.exists()) {
					_FileDir.mkdirs();
				}
				
				FileUtil.cp("/data/data/" + GetContext().getPackageName() + "/databases/GoDutchDataBase", "/sdcard/GoDutch/DataBaseBak/GoDutchDataBase");
				
			}
			
			SaveDatabaseBackupDate(p_Backup.getTime());
			
			_Result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return _Result;
	}
	
	public boolean DatabaseRestore() {
		boolean _Result = false;
		try {
			long _DatabaseBackupDate = LoadDatabaseBackupDate();
			
			if(_DatabaseBackupDate != 0)
			{
				FileUtil.cp("/sdcard/GoDutch/DataBaseBak/GoDutchDataBase", "/data/data/Mobidever.GoDutch/databases/GoDutchDataBase");
			}
			
			_Result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return _Result;
	}
	
	public void SaveDatabaseBackupDate(long _Millise)
	{		
		SharedPreferences _SP = GetContext().getSharedPreferences("DatabaseBackupDate",Context.MODE_PRIVATE);
		SharedPreferences.Editor _Editor = _SP.edit();
		_Editor.putLong("DatabaseBackupDate", _Millise);
		_Editor.commit();
	}
	
	public long LoadDatabaseBackupDate()
	{
		long _DatabaseBackupDate = 0;
		SharedPreferences _SP = GetContext().getSharedPreferences("DatabaseBackupDate",Context.MODE_PRIVATE);
		if (_SP != null) {
			_DatabaseBackupDate = _SP.getLong("DatabaseBackupDate", 0);
		}
		
		return _DatabaseBackupDate;
	}
}
