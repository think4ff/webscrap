package com.aurg.webscrap;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public class Log {

	Log(JTextArea textarea) {
		this.taLog = textarea;
	}
	JTextArea taLog;
	
	public void out(String str)
	{
	   Date dt = new Date();
	   SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss"); 

	   taLog.append(sdf.format(dt).toString() + " - " + str + "\n");// (str+"\n", taLog.get);
	}
	
	void clear()
	{
	   taLog.removeAll();		
	}
}
