package app.bih.in.nic.nischayyojana.entity;

import java.io.Serializable;


public class PayJalProperty implements Serializable{
	public static Class<PayJalProperty> USER_CLASS = PayJalProperty.class;

	private String _Row_ID="";
	private String _BoringStarted="";
	private String _StaggingStarted="";
	private String _CompletionDate="";

	public String get_BoringStarted() {
		return _BoringStarted;
	}

	public void set_BoringStarted(String _BoringStarted) {
		this._BoringStarted = _BoringStarted;
	}

	public String get_StaggingStarted() {
		return _StaggingStarted;
	}

	public void set_StaggingStarted(String _StaggingStarted) {
		this._StaggingStarted = _StaggingStarted;
	}

	public String get_CompletionDate() {
		return _CompletionDate;
	}

	public void set_CompletionDate(String _CompletionDate) {
		this._CompletionDate = _CompletionDate;
	}

	public String get_Row_ID() {
		return _Row_ID;
	}

	public void set_Row_ID(String _Row_ID) {
		this._Row_ID = _Row_ID;
	}



	public PayJalProperty() {
		// TODO Auto-generated constructor stub
	}

	public static Class<PayJalProperty> getUSER_CLASS() {
		return USER_CLASS;
	}

	public static void setUSER_CLASS(Class<PayJalProperty> uSER_CLASS) {
		USER_CLASS = uSER_CLASS;
	}

}
