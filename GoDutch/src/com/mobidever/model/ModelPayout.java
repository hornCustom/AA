package com.mobidever.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import android.R.integer;

public class ModelPayout implements Serializable {
	private int m_PayoutID;
	private int m_AccountBookID;
	private String m_AccountBookName;
	private int m_CategoryID;
	private String m_CategoryName;
	private String m_Path;
	private int m_PayWayID;
	private int m_PlaceID;
	private BigDecimal m_Amount;
	private Date m_PayoutDate;
	private String m_PayoutType;
	private String m_PayoutUserID;
	private String m_Comment;
	private Date m_CreateDate = new Date();
	private int m_State = 1;
	public int GetPayoutID() {
		return m_PayoutID;
	}
	public void SetPayoutID(int p_PayoutID) {
		this.m_PayoutID = p_PayoutID;
	}
	public int GetAccountBookID() {
		return m_AccountBookID;
	}
	public void SetAccountBookID(int p_AccountBookID) {
		this.m_AccountBookID = p_AccountBookID;
	}
	public String GetAccountBookName() {
		return m_AccountBookName;
	}
	public void SetAccountBookName(String p_AccountBookName) {
		this.m_AccountBookName = p_AccountBookName;
	}
	public int GetCategoryID() {
		return m_CategoryID;
	}
	public void SetCategoryID(int p_CategoryID) {
		this.m_CategoryID = p_CategoryID;
	}
	public String GetPath() {
		return m_Path;
	}
	public void SetPath(String p_Path) {
		this.m_Path = p_Path;
	}
	public String GetCategoryName() {
		return m_CategoryName;
	}
	public void SetCategoryName(String p_CategoryName) {
		this.m_CategoryName = p_CategoryName;
	}
	public int GetPayWayID() {
		return m_PayWayID;
	}
	public void SetPayWayID(int p_PayWayID) {
		this.m_PayWayID = p_PayWayID;
	}
	public int GetPlaceID() {
		return m_PlaceID;
	}
	public void SetPlaceID(int p_PlaceID) {
		this.m_PlaceID = p_PlaceID;
	}
	public BigDecimal GetAmount() {
		return m_Amount;
	}
	public void SetAmount(BigDecimal p_Amount) {
		this.m_Amount = p_Amount;
	}
	public Date GetPayoutDate() {
		return m_PayoutDate;
	}
	public void SetPayoutDate(Date p_PayoutDate) {
		this.m_PayoutDate = p_PayoutDate;
	}
	public String GetPayoutType() {
		return m_PayoutType;
	}
	public void SetPayoutType(String p_PayoutType) {
		this.m_PayoutType = p_PayoutType;
	}
	public String GetPayoutUserID() {
		return m_PayoutUserID;
	}
	public void SetPayoutUserID(String p_PayoutUserID) {
		this.m_PayoutUserID = p_PayoutUserID;
	}	
	public String GetComment() {
		return m_Comment;
	}
	public void SetComment(String p_Comment) {
		this.m_Comment = p_Comment;
	}
	public Date GetCreateDate() {
		return m_CreateDate;
	}
	public void SetCreateDate(Date p_CreateDate) {
		this.m_CreateDate = p_CreateDate;
	}
	public int GetState() {
		return m_State;
	}
	public void SetState(int p_State) {
		this.m_State = p_State;
	}
}
