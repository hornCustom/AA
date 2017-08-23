package model;

import java.util.Date;

public class ModelUser {
	
	private int mUserID;
	private String mUserName;
	private Date mcreateDate = new Date();
	private int mstate = 1;
	
	public int getUserID(){
		return mUserID;
	}
	public void setUserID(int userID){
		this.mUserID = userID;
	}

	public int getState() {
		return mstate;
	}
	public String getUserName() {
		return mUserName;
	}

	public Date getCreateData() {
		return mcreateDate;
	}
	public void setState(int i) {
		// TODO Auto-generated method stub
		mstate = i;
		
	}
	public void setUserName(String string) {
		// TODO Auto-generated method stub
		mUserName =string;
		
	}
	public void setCreateDate(Date _CreateDate) {
		// TODO Auto-generated method stub
		mcreateDate = _CreateDate;
		
	}

	
	

}
