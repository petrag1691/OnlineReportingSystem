package edu.uic.ids.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="errorMessageBean")
@SessionScoped
public class ErrorMessageBean {
	private int errorCode;
	private String errorMessage;
	public ErrorMessageBean(){
		errorCode =0;
		errorMessage = "";
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
