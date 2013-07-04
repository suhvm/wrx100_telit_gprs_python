package com.teleofis.wrxconfig.transport;

public class SerialParameters {
	
	private final String name;
	
	private final Integer baudrate;
	
	private final Integer databits;
	
	private final Integer stopbits;
	
	private final Integer parity;
	
	private final boolean ctsControl;
	
	public SerialParameters(String name, int baudrate, int databits, int stopbits, int parity, boolean ctsControl) {
		this.name = name;
		this.baudrate = baudrate;
		this.databits = databits;
		this.stopbits = stopbits;
		this.parity = parity;
		this.ctsControl = ctsControl;
	}
	
	/**
	 * Имя
	 * @return значение
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Скорость
	 * @return значение
	 */
	public int getBaudrate() {
		return baudrate.intValue();
	}
	
	/**
	 * Databits
	 * @return значение
	 */
	public int getDatabits() {
		return databits.intValue();
	}
	
	/**
	 * Stopbits
	 * @return значение
	 */
	public int getStopbits() {
		return stopbits.intValue();
	}
	
	/**
	 * Parity
	 * @return значение
	 */
	public int getParity() {
		return parity.intValue();
	}
	
	/**
	 * Контроль линии CTS
	 * @return значение
	 */
	public boolean isCtsControl() {
		return ctsControl;
	}
}
