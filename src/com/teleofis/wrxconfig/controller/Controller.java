package com.teleofis.wrxconfig.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.teleofis.wrxconfig.IProgress;
import com.teleofis.wrxconfig.Model;
import com.teleofis.wrxconfig.model.Parameter;
import com.teleofis.wrxconfig.telit.TelitLoader;
import com.teleofis.wrxconfig.transport.SerialTransport;

public enum Controller {
	
	INSTANCE;
	
	private final String FILENAME = "wrx100.pyo";
	
	private SerialTransport transport;
	
	private TelitLoader telit;
	
	private TelitThread telitThread;
	
	private boolean terminated = false;
	
	private ArrayBlockingQueue<Command> CommandsQueue = new ArrayBlockingQueue<Command>(10);
	
	private Controller() {
		transport = new SerialTransport((byte) 0x0D);
		telit = new TelitLoader(transport);
		
		this.telitThread = new TelitThread();
		this.telitThread.setName("Telit Thread");
		this.telitThread.start();
	}
	
	public boolean openSettings(String path) {
		boolean result = false;
		Model.INSTANCE.getModel().clearParameters();
		try {
			Scanner scanner = new Scanner(new FileInputStream(path), "UTF-8");
			try {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] param = line.split("::");
					if (param.length > 2) {
						Model.INSTANCE.getModel().addParameter(
								new Parameter(param[0], param[2], param[1]));
					}
				}
				result = true;
			} finally {
				scanner.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public boolean dumpSettings(String path) {
		boolean result = false;
		String output = "";
		for(Parameter parameter : Model.INSTANCE.getModel().getParameters()) {
			output += parameter.toString() + "\r\n";
		}
		if(path == null) {
			path = Model.INSTANCE.getModel().getSettingsPath();
		}
		if(path.equals("")) {
			return result;
		}
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
			out.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	      try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    }
		result = true;
		return result;
	}
	
	class Command {
		private final int command;
		private final int parameter;
		private final IProgress progress;
		
		public Command(int command, IProgress progress, int parameter) {
			this.command = command;
			this.progress = progress;
			this.parameter = parameter;
		}
		
		public int getCommand() {
			return command;
		}
		
		public int getParameter() {
			return parameter;
		}
		
		public IProgress getProgress() {
			return progress;
		}
	}
	
	/**
	 * 
	 * @author Pavel Gololobov
	 */
	class TelitThread extends Thread {
	    public void run() {
	    	while(!terminated) {
	    		try {
	    			Command cmd = CommandsQueue.poll(1000, TimeUnit.MILLISECONDS);
					if(cmd != null) {
						switch(cmd.getCommand()) {
						case 0:
							upload(cmd.getProgress(), cmd.getParameter());
							break;
						}
					}
				} catch (InterruptedException e) {
					terminated = true;
				}
	    	}
	    }
	}
	
	public void terminate() {
		terminated = true;
		try {
			telitThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		transport.close();
	}
	
	public boolean open() {
		return transport.open(Model.INSTANCE.getModel().getSerialParameters());
	}

	public boolean close() {
		return transport.close();
	}
	
	public void startUpload(IProgress progress, int uploadMode) {
		try {
			CommandsQueue.put(new Command(0, progress, uploadMode));
		} catch (InterruptedException e) {
		}
	}
	
	private boolean upload(IProgress progressBar, int uploadMode) {
		progressBar.setTotal(100);
		progressBar.setCompleted(0);
		
		boolean result = dumpSettings("scripts/settings.ini");
		if(result) {
			progressBar.setCompleted(10);
			result = telit.upload("scripts/", "settings.ini");
		}

		if(uploadMode == 1) {
			if(result) {
				progressBar.setCompleted(25);
				result = telit.upload("scripts/", ".pyo");
			}
		}
		
		if(result) {
			progressBar.setCompleted(75);
			result = telit.start(FILENAME);
		}
		progressBar.setCompleted(100);
		
		if(result) {
			progressBar.setError(0);
		} else {
			progressBar.setError(1);
		}
		
		return result;
	}
}
