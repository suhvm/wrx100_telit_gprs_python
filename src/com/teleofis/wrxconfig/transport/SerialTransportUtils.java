package com.teleofis.wrxconfig.transport;

import jssc.SerialPortList;

public class SerialTransportUtils {

	public static String[] getPortList() {
		return SerialPortList.getPortNames();
	}
	
}
