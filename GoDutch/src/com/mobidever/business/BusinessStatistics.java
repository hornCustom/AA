package com.mobidever.business;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mobidever.business.base.BusinessBase;
import com.mobidever.model.ModelPayout;

import com.mobidever.R;

import android.R.anim;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import jxl.*;   
import jxl.format.Alignment;   
import jxl.format.Border;   
import jxl.format.BorderLineStyle;   
import jxl.format.CellFormat;   
import jxl.write.Boolean;   
import jxl.write.Label;   
import jxl.write.Number;   
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;   
import jxl.write.WritableFont;   
import jxl.write.WritableSheet;   
import jxl.write.WritableWorkbook;   

public class BusinessStatistics extends BusinessBase {
	
	private BusinessPayout m_BusinessPayout;
	private BusinessUser m_BusinessUser;
	private BusinessAccountBook m_BusinessAccountBook;
	
	public BusinessStatistics(Context p_Context)
	{
		super(p_Context);
		m_BusinessPayout = new BusinessPayout(p_Context);
		m_BusinessUser = new BusinessUser(p_Context);
		m_BusinessAccountBook = new BusinessAccountBook(p_Context);
	}
	
	public String GetPayoutUserIDByAccountBookID(int p_AccountBookID)
	{
		String _Result = "";
		List<ModelStatistics> _ListModelStatisticsTotal =  GetPayoutUserID(" And AccountBookID = " + p_AccountBookID);
		
		for (int i = 0; i < _ListModelStatisticsTotal.size(); i++) {
			ModelStatistics _ModelStatistics = _ListModelStatisticsTotal.get(i);
			if(_ModelStatistics.getPayoutType().equals("����")) {
				_Result += _ModelStatistics.PayerUserID + "�������� " + _ModelStatistics.Cost.toString() + "Ԫ\r\n";
			} else if(_ModelStatistics.getPayoutType().equals("����")) {
				if(_ModelStatistics.PayerUserID.equals(_ModelStatistics.ConsumerUserID)) {
					_Result += _ModelStatistics.PayerUserID + "�������� " + _ModelStatistics.Cost.toString() + "Ԫ\r\n";
				} else {
					_Result += _ModelStatistics.ConsumerUserID + "Ӧ֧����" + _ModelStatistics.PayerUserID + _ModelStatistics.Cost + "Ԫ\r\n";
				}
			}
		}
		
		return _Result;
	}
	
	public List<ModelStatistics> GetPayoutUserID(String p_Condition)
	{
		List<ModelStatistics> _ListModelStatistics = GetModelStatisticsList(p_Condition);
		List<ModelStatistics> _ListModelStatisticsTemp = new ArrayList<ModelStatistics>();
		List<ModelStatistics> _ListModelStatisticsTotal = new ArrayList<ModelStatistics>();
		String _Result = "";
		for (int i = 0; i < _ListModelStatistics.size(); i++) {
			ModelStatistics _ModelStatistics = _ListModelStatistics.get(i);
			_Result += _ModelStatistics.PayerUserID + "#" + _ModelStatistics.ConsumerUserID + "#" + _ModelStatistics.Cost + "\r\n";
			String _CurrentPayerUserID = _ModelStatistics.PayerUserID;
			
			ModelStatistics _ModelStatisticsTemp = new ModelStatistics();
			_ModelStatisticsTemp.PayerUserID = _ModelStatistics.PayerUserID;
			_ModelStatisticsTemp.ConsumerUserID = _ModelStatistics.ConsumerUserID;
			_ModelStatisticsTemp.Cost = _ModelStatistics.Cost;
			_ModelStatisticsTemp.setPayoutType(_ModelStatistics.getPayoutType());
			_ListModelStatisticsTemp.add(_ModelStatisticsTemp);
			
			int _NextIndex;
			if((i+1) < _ListModelStatistics.size())
			{
				_NextIndex = i+1;
			}
			else {
				_NextIndex = i;
			}
			
			if (!_CurrentPayerUserID.equals(_ListModelStatistics.get(_NextIndex).PayerUserID) || _NextIndex == i) {
				
				for (int j = 0; j < _ListModelStatisticsTemp.size(); j++) {
					ModelStatistics _ModelStatisticsTotal = _ListModelStatisticsTemp.get(j);
					int _Index = GetPostionByConsumerUserID(_ListModelStatisticsTotal,_ModelStatisticsTotal.PayerUserID, _ModelStatisticsTotal.ConsumerUserID);
					if(_Index != -1)
					{
						_ListModelStatisticsTotal.get(_Index).Cost = _ListModelStatisticsTotal.get(_Index).Cost.add(_ModelStatisticsTotal.Cost);
					}
					else {
						_ListModelStatisticsTotal.add(_ModelStatisticsTotal);
					}
				}
				_ListModelStatisticsTemp.clear();
			}
			
		}
		
		return _ListModelStatisticsTotal;
	}
	
	private int GetPostionByConsumerUserID(List<ModelStatistics> p_ListModelStatisticsTotal,String p_PayerUserID,String p_ConsumerUserID)
	{
		int _Index = -1;
		for (int i = 0; i < p_ListModelStatisticsTotal.size(); i++) {
			if (p_ListModelStatisticsTotal.get(i).PayerUserID.equals(p_PayerUserID) && p_ListModelStatisticsTotal.get(i).ConsumerUserID.equals(p_ConsumerUserID)) {
				_Index = i;
			}
		}
		
		return _Index;
	}

	private List<ModelStatistics> GetModelStatisticsList(String p_Condition) {
		List<ModelPayout> _ListPayout = m_BusinessPayout.GetPayoutOrderByPayoutUserID(p_Condition);
		
		String payoutTypeArr[] = GetContext().getResources().getStringArray(R.array.PayoutType);
		
		List<ModelStatistics> listModelStatistics = new ArrayList<ModelStatistics>();
		
		if(_ListPayout != null)
		{
			for (int i = 0; i < _ListPayout.size(); i++) {
				ModelPayout modelPayout = _ListPayout.get(i);
				String payoutUserName[] = m_BusinessUser.GetUserNameByUserID(modelPayout.GetPayoutUserID()).split(",");
				String payoutUserID[] = modelPayout.GetPayoutUserID().split(",");
				
				String payoutType = modelPayout.GetPayoutType();
				
				BigDecimal cost;
				
				if(payoutType.equals(payoutTypeArr[0]))
				{
					int payoutTotal = payoutUserName.length;
					


					cost = modelPayout.GetAmount().divide(new BigDecimal(payoutTotal),2, BigDecimal.ROUND_HALF_EVEN);
				}
				else {
					cost = modelPayout.GetAmount();
				}
				
				for (int j = 0; j < payoutUserID.length; j++) {
					
					if (payoutType.equals(payoutTypeArr[1]) && j == 0) {
						continue;
					}
					
					ModelStatistics modelStatistics = new ModelStatistics();
					modelStatistics.PayerUserID = payoutUserName[0];
					modelStatistics.ConsumerUserID = payoutUserName[j];
					modelStatistics.setPayoutType(payoutType);
					modelStatistics.Cost = cost;
					
					listModelStatistics.add(modelStatistics);
				}
			}
		}
		
		return listModelStatistics;
	}
	
	public String ExportStatistics(int p_AccountBookID) throws Exception {
		String result = "";
		String _AccountBookName = m_BusinessAccountBook.GetAccountBookNameByAccountId(p_AccountBookID);
		Date _Date = new Date();
		String fileName = String.valueOf(_Date.getYear()) + String.valueOf(_Date.getMonth()) + String.valueOf(_Date.getDay()) + ".xls";
		File _FileDir = new File("/sdcard/GoDutch/Export/");
		if (!_FileDir.exists()) {
			_FileDir.mkdirs();
		}
		File file = new File("/sdcard/GoDutch/Export/" + fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		
		WritableWorkbook wBookData;
		wBookData = Workbook.createWorkbook(file);
		WritableSheet wsAccountBook = wBookData.createSheet(_AccountBookName, 0);
		
		String[] _Titles = {"���", "����", "���", "������Ϣ", "��������"};
		Label _Label;
		for (int i = 0; i < _Titles.length; i++) {
			_Label = new Label(i, 0, _Titles[i]);
			wsAccountBook.addCell(_Label);
		}
		
		List<ModelStatistics> _ListModelStatisticsTotal =  GetPayoutUserID(" And AccountBookID = " + p_AccountBookID);
		
		for(int i = 0;i < _ListModelStatisticsTotal.size(); i++) {
			ModelStatistics _ModelStatistics = _ListModelStatisticsTotal.get(i);
			
			jxl.write.Number _IdCell = new Number(0, i+1, i+1);
			wsAccountBook.addCell(_IdCell);
			
			Label _lblName = new Label(1, i+1, _ModelStatistics.PayerUserID);
			wsAccountBook.addCell(_lblName);
			
			NumberFormat nfMoney = new NumberFormat("#.##");
			WritableCellFormat wcfFormat = new WritableCellFormat(nfMoney);
			
			Number _CostCell = new Number(2, i+1, _ModelStatistics.Cost.doubleValue(), wcfFormat);
			wsAccountBook.addCell(_CostCell);
			
			String _Info = "";
			if(_ModelStatistics.getPayoutType().equals("����")) {
				_Info = _ModelStatistics.PayerUserID + "�������� " + _ModelStatistics.Cost.toString() + "Ԫ";
			} else if(_ModelStatistics.getPayoutType().equals("����")) {
				if(_ModelStatistics.PayerUserID.equals(_ModelStatistics.ConsumerUserID)) {
					_Info = _ModelStatistics.PayerUserID + "�������� " + _ModelStatistics.Cost.toString() + "Ԫ";
				} else {
					_Info = _ModelStatistics.ConsumerUserID + "Ӧ֧����" + _ModelStatistics.PayerUserID + _ModelStatistics.Cost + "Ԫ";
				}
			} 
			Label _lblInfo = new Label(3, i+1, _Info);
			wsAccountBook.addCell(_lblInfo);
			
			Label _lblPayoutType = new Label(4, i+1, _ModelStatistics.getPayoutType());
			wsAccountBook.addCell(_lblPayoutType);
		}
		
		wBookData.write();
		wBookData.close();
		result = "�����Ѿ�������λ���ڣ�/sdcard/GoDutch/Export/" + fileName;
		return result;
	}
	
	public class ModelStatistics
	{
		public String PayerUserID;
		public String ConsumerUserID;
		private String m_PayoutType;
		public BigDecimal Cost;
		
		public String getPayoutType() {
			return m_PayoutType;
		}
		
		public void setPayoutType(String p_Value) {
			m_PayoutType = p_Value;
		}
	}
}
