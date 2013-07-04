package com.teleofis.wrxconfig;

public interface IProgress {
	
	public void setTotal(int total);
	
	public void setCompleted(int completeValue);
	
	public void setError(int errorCode);
}

