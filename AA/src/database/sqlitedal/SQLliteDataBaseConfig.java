package database.sqlitedal;

import java.util.ArrayList;

import com.example.aa.R;


import android.content.Context;

public class SQLliteDataBaseConfig {
	private static final String DATABASE_NAME = "GoDutchDataBase";
	private static final int VERSION =1;
	private static SQLliteDataBaseConfig INSTANCE;
	private static Context mContext;
	
	private SQLliteDataBaseConfig(){
		
	}
	
	public static SQLliteDataBaseConfig GetInstance(Context pContext){
		if (INSTANCE == null) {
			INSTANCE = new SQLliteDataBaseConfig();
			mContext = pContext;
		} 

		
		return INSTANCE;
	}
	
      public String GetDataBaseName(){
    	 return DATABASE_NAME;
      }
      
      public int GetVersion(){
    	  return VERSION;
      }
      public ArrayList<String> GetTables(){
    	  ArrayList<String> _ArrayList = new ArrayList<String>();
    	  String[] _SQLiteDALClassName = mContext.getResources().getStringArray(R.array.SQLiteDALClassName);
    	  //String _PackagePath = mContext.getPackageName() + ".Database.SQLiteDAL.";
    	  String _PackagePath = "database.";
    	  for (int i = 0; i < _SQLiteDALClassName.length; i++) {
			_ArrayList.add(_PackagePath + _SQLiteDALClassName[i]);
		}
    	  return _ArrayList;
      }
}
