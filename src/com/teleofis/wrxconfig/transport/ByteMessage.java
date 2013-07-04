package com.teleofis.wrxconfig.transport;

import java.util.ArrayList;
import java.util.List;

public class ByteMessage {

	List<Byte> data;
	
	public ByteMessage() {
		this.data = new ArrayList<Byte>();
	}
	
	public void addByte(Byte b) {
		data.add(b);
	}
	
	public List<Byte> getBytes() {
		return data;
	}
	
}
