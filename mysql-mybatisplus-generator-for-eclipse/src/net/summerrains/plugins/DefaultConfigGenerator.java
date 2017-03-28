package net.summerrains.plugins;

import net.summerrains.generator.ConfigGenerator;

public class DefaultConfigGenerator extends ConfigGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String host;
	private String port;
	private String database;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	

}
