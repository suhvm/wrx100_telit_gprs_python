package com.teleofis.wrxconfig.telit;

import com.teleofis.utils.FileStream;
import com.teleofis.wrxconfig.transport.SerialTransport;

public class TelitLoader {
	
	private final SerialTransport transport;

	public TelitLoader(SerialTransport transport) {
		this.transport = transport;
	}
	
	private boolean sendCommand(String command, String response, int timeout) {
		command = command.concat("\r");
		System.out.println("OUT: " + command);
		return sendCommand(command.getBytes(), response, timeout);
	}
	
	private boolean sendCommand(byte[] command, String response, int timeout) {
		boolean result = transport.sendBytes(command);
		if(result) {
			String received = "";
			long endTime = System.currentTimeMillis() + timeout;
			while(System.currentTimeMillis() < endTime) {
				byte[] rcvBytes = transport.receiveBytes();
				if(rcvBytes != null) {
					received = received.concat(new String(rcvBytes));
					if(received.indexOf(response) != -1) {
						System.out.println("IN: " + received);
						return true;
					}
					if(received.indexOf("ERROR") != -1) {
						System.out.println("IN: " + received);
						break;
					}
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		}
		return false;
	}

	private boolean checkConnection() {
		boolean result = sendCommand("AT", "OK", 3000);
		return result;
	}
	
	private boolean deleteFile(String file) {
		boolean result = sendCommand("AT#DSCRIPT=\"" + file + "\"", "OK", 3000);
		return result;
	}
	
	private boolean writeFile(String path, String file) {
		boolean result = false;
		byte[] data = FileStream.read(path + file);

		result = sendCommand("AT#WSCRIPT=\"" + file + "\"," + Integer.toString(data.length), ">>>", 3000);
		if(result) {
			result = sendCommand(data, "OK", 30000);
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean listFiles() {
		boolean result = sendCommand("AT#LSCRIPT", "OK", 3000);
		return result;
	}
	
	private boolean enableFile(String file) {
		boolean result = sendCommand("AT#ESCRIPT=\"" + file + "\"", "OK", 3000);
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean executeFile() {
		boolean result = sendCommand("AT#EXECSCR", "OK", 3000);
		return result;
	}
	
	@SuppressWarnings("unused")
	private boolean setMode() {
		boolean result = sendCommand("AT#STARTMODESCR=0", "OK", 3000);
		return result;
	}
	
	public boolean upload(String path, String filter) {
		boolean result = false;
		String[] files = FileStream.getFilesList(path, filter);
		if(files.length == 0) {
			return false;
		}
		result = checkConnection();
		if(result) {
			for(String file : files) {
				deleteFile(file);
				result = writeFile(path, file);
			}
		}
		return result;
	}
	
	public boolean start(String name) {
		boolean result = enableFile(name);
		if(result) {
//			result = setMode();
//			result = executeFile();
		}
		return result;
	}
}
