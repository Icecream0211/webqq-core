package iqq.im.bean;

import java.io.Serializable;

public class SysMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4370829896385172632L;
	private String account;
	private String from_uin;//requestUIN
	private String msg;//message
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFrom_uin() {
		return from_uin;
	}
	public void setFrom_uin(String from_uin) {
		this.from_uin = from_uin;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
