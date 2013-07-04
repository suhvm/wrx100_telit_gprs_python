package com.teleofis.wrxconfig.transport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialTransport {
	
	private SerialPort serialPort;
	
	private byte messageSeparator;
	
	private ByteMessage message;
	
	private LinkedBlockingQueue<ByteMessage> rxQueue;
	
	public SerialTransport(byte messageSeparator) {
		this.messageSeparator = messageSeparator;
		this.message = new ByteMessage();
		this.rxQueue = new LinkedBlockingQueue<ByteMessage>();
	}
	
	/**
	 * Events Handler
	 */
	class SerialPortReader implements SerialPortEventListener {
		
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {
				try {
					int size = event.getEventValue();
					byte buffer[] = serialPort.readBytes(size);
					process(buffer);
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
			} 
		}
		
	}
	
	/**
	 * Close port
	 */
	public boolean close() {
		if ((serialPort != null) && (serialPort.isOpened())) {
			try {
				serialPort.closePort();
				serialPort = null;
			} catch (SerialPortException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * Open port
	 * @param serialParameters
	 * @return success
	 */
	public boolean open(SerialParameters serialParameters) {
		if(!close()) {
			return false;
		}
		serialPort = new SerialPort(serialParameters.getName());
		try {
			serialPort.openPort();
			
//			serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
//			serialPort.addEventListener(new SerialPortReader());
			
			serialPort.setParams(serialParameters.getBaudrate(), serialParameters.getDatabits(), 
					serialParameters.getStopbits(), serialParameters.getParity());
			
			if(serialParameters.isCtsControl()) {
				serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
			}

		} catch (SerialPortException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Send byte array
	 * @param data
	 * @return success
	 */
	public boolean sendBytes(byte[] data) {
		try {
			serialPort.writeBytes(data);
		} catch (SerialPortException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Receive all available bytes
	 * @return
	 */
	public byte[] receiveBytes() {
		try {
			return serialPort.readBytes();
		} catch (SerialPortException e) {
		}
		return null;
	}
	
	/**
	 * Send string
	 * @param data
	 * @return success
	 */
	public boolean sendString(String data) {
		try {
			serialPort.writeString(data);
		} catch (SerialPortException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void clearReceiveQueue() {
		rxQueue.clear();
	}
	
	/**
	 * Receive message
	 * @return message
	 */
	public ByteMessage receive() {
		ByteMessage message = null;
		try {
			message = rxQueue.poll(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
		return message;
	}
	
	/**
	 * Process received bytes
	 * @param data
	 */
	private void process(byte[] data) {
		for(byte b : data) {
			if(b == messageSeparator) {
				if(message.getBytes().size() > 0) {
					rxQueue.add(message);
					message = new ByteMessage();
				}
			} else {
				message.addByte(b);
			}
		}
	}

}
