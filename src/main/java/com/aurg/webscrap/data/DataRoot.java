package com.aurg.webscrap.data;

public class DataRoot {
	TYPE type;
	public TYPE getType() {return type;} 
	public String toString() { return "";} 
    public String getFailInfo() { return "";}
    public String getSuccessColumnHead() { return "";} 
    public String getFailColumnHead() { return "";} 
    
    //public void init() { } 
	public enum TYPE { SaGunInput,SaGunOutput,SaGunFail, AuctionInput, AuctionOutput, AuctionFail };
	
}
