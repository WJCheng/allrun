package com.tarena.allrun.entity;

public class XmppConfig {

	public String host;
	public String serviceName;
	public int port = 0;
	
	
	public XmppConfig() {
		super();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "XmppConfig [host=" + host + ", serviceName=" + serviceName
				+ ", port=" + port + "]";
	}
	
	public boolean isValid(){
		
		return host.length()>0 && serviceName.length()>0 && port != 0;
	}
	
}
