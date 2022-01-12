package app.bih.in.nic.nischayyojana.entity;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class UserInfo implements KvmSerializable {
	public static Class<UserInfo> USER_CLASS = UserInfo.class;


	private String _UserID = "";
	private String _UserName = "";
	private String _Password = "";

	private String _DistCode = "";
	private String _DistName = "";
	private String _BlockCode = "";
	private String _BlockName = "";
	private String _PanchayatCode = "";
	private String _PanchayatName = "";
	private String _Role = "";
	private String _IMEI = "";
	private Boolean _isAuthenticated = false;

	private String _Designation = "";
	private String _LastVisitedOn = "";
	private String _MobileNo = "";
	private String _BalanceAMount = "";
	private String _TotalApproved = "";
	private String _TotalNotApproved = "";
	private String _CardNumber = "";
	private String _AreaType = "";
	/*

	<Designation>PAN</Designation>
<LastVisitedOn>2016-01-02T00:00:00</LastVisitedOn>
<MobileNo>NA</MobileNo>
	 */

	public UserInfo() {
	}

	public UserInfo(SoapObject obj) {

		this._isAuthenticated = Boolean.parseBoolean(obj.getProperty("isAuthenticated").toString());
		this._UserID = obj.getProperty("UserID").toString().trim();
		this._UserName = obj.getProperty("UserName").toString().trim();
		this._Password = obj.getProperty("Password").toString().trim();
		this._Role = obj.getProperty("Role").toString().trim();
		if (this._isAuthenticated) {
			this._DistCode = obj.getProperty("DistrictCode").toString().trim();
			this._DistName = obj.getProperty("DistrictName").toString().trim();
			this._BlockCode = obj.getProperty("BlockCode").toString().trim();
			this._BlockName = obj.getProperty("BlockName").toString().trim();
			this._MobileNo= obj.getProperty("MobileNo").toString().trim();
			this._LastVisitedOn=obj.getProperty("LastVisitedOn").toString().trim();
			this._Designation=obj.getProperty("Designation").toString().trim();
			this._IMEI=obj.getProperty("IMEI").toString().trim();

			this._PanchayatCode = obj.getProperty("PanchayatCode").toString().trim();
			this._PanchayatName = obj.getProperty("PanchayatName").toString().trim();
		}
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public Object getProperty(int index) {
		Object object = null;
		switch (index) {
			case 0: {
				object = this._isAuthenticated;
				break;
			}
			case 1: {
				object = this._UserID;
				break;
			}
			case 2: {
				object = this._UserName;
				break;
			}
			case 3: {
				object = this._Password;
				break;
			}
			case 4: {
				object = this._DistCode;
				break;
			}
			case 5: {
				object = this._BlockCode;
				break;

			}
			case 6: {
				object = this._PanchayatCode;
				break;

			}
			case 7: {
				object = this._Role;
				break;

			}
			case 8: {
				object = this._Designation;
				break;

			}
			case 9: {
				object = this._MobileNo;
				break;
			}
			case 10: {
				object = this.get_IMEI();
				break;

			}
			case 11: {
				object = this._LastVisitedOn;
				break;

			}

		}
		return object;
	}


	@Override
	public void getPropertyInfo(int index, Hashtable arg1,
								PropertyInfo propertyInfo) {
		switch (index) {
			case 0: {
				propertyInfo.name = "isAuthenticated";
				propertyInfo.type = PropertyInfo.BOOLEAN_CLASS;
				break;
			}
			case 1: {
				propertyInfo.name = "UserID";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 2: {
				propertyInfo.name = "UserName";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 3: {
				propertyInfo.name = "Password";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}

			case 4: {
				propertyInfo.name = "DistCode";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 5: {
				propertyInfo.name = "BlockCode";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 6: {
				propertyInfo.name = "PanchayatCode";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 7: {
				propertyInfo.name = "Role";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 8: {
				propertyInfo.name = "Designation";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 9: {
				propertyInfo.name = "MobileNo";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 10: {
				propertyInfo.name = "IMEI";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 11: {
				propertyInfo.name = "MobileNo";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 12: {
				propertyInfo.name = "BalanceAmount";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 13: {
				propertyInfo.name = "TotalApproved";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
			case 14: {
				propertyInfo.name = "TotalPending";
				propertyInfo.type = PropertyInfo.STRING_CLASS;
				break;
			}
		}
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
			case 0: {
				this._isAuthenticated = Boolean.parseBoolean(obj.toString());
				break;
			}
			case 1: {
				this._UserID = obj.toString();
				break;
			}
			case 2: {
				this._UserName = obj.toString();
				break;
			}
			case 3: {
				this._Password = obj.toString();
				break;
			}

			case 4: {
				this._DistCode = obj.toString();
				break;
			}
			case 5: {
				this._BlockCode = obj.toString();
				break;
			}
			case 6: {
				this._PanchayatCode = obj.toString();
				break;
			}
			case 7: {
				this._Role = obj.toString();
				break;
			}

			case 8: {
				this._Designation = (obj.toString());
				break;
			}
			case 9: {
				this._LastVisitedOn = obj.toString();
				break;
			}
			case 10: {
				this.set_IMEI(obj.toString());
				break;
			}
			case 11: {
				this._MobileNo=obj.toString();
				break;
			}
			case 12: {
				this._BalanceAMount=obj.toString();
				break;
			}
			case 13: {
				this._TotalApproved=obj.toString();
				break;
			}
			case 14: {
				this._TotalNotApproved=obj.toString();
				break;
			}
		}
	}

	public String get_AreaType() {
		return _AreaType;
	}

	public void set_AreaType(String _AreaType) {
		this._AreaType = _AreaType;
	}

	public String get_CardNumber() {
		return _CardNumber;
	}

	public void set_CardNumber(String _CardNumber) {
		this._CardNumber = _CardNumber;
	}

	public String get_UserID() {
		return _UserID;
	}

	public void set_UserID(String _UserID) {
		this._UserID = _UserID;
	}

	public String get_UserName() {
		return _UserName;
	}

	public void set_UserName(String _UserName) {
		this._UserName = _UserName;
	}

	public String get_Password() {
		return _Password;
	}

	public void set_Password(String _Password) {
		this._Password = _Password;
	}

	public String get_DistCode() {
		return _DistCode;
	}

	public void set_DistCode(String _DistCode) {
		this._DistCode = _DistCode;
	}

	public String get_BlockCode() {
		return _BlockCode;
	}

	public void set_BlockCode(String _BlockCode) {
		this._BlockCode = _BlockCode;
	}

	public String get_BlockName() {
		return _BlockName;
	}

	public void set_BlockName(String _BlockName) {
		this._BlockName = _BlockName;
	}

	public String get_PanchayatCode() {
		return _PanchayatCode;
	}

	public void set_PanchayatCode(String _PanchayatCode) {
		this._PanchayatCode = _PanchayatCode;
	}

	public String get_PanchayatName() {
		return _PanchayatName;
	}

	public void set_PanchayatName(String _PanchayatName) {
		this._PanchayatName = _PanchayatName;
	}

	public String get_Role() {
		return _Role;
	}

	public void set_Role(String _Role) {
		this._Role = _Role;
	}


	public String get_IMEI() {
		return _IMEI;
	}

	public void set_IMEI(String _IMEI) {
		this._IMEI = _IMEI;
	}

	public Boolean get_isAuthenticated() {
		return _isAuthenticated;
	}

	public void set_isAuthenticated(Boolean _isAuthenticated) {
		this._isAuthenticated = _isAuthenticated;
	}


	public String get_DistName() {
		return _DistName;
	}

	public void set_DistName(String _DistName) {
		this._DistName = _DistName;
	}

	public String get_Designation() {
		return _Designation;
	}

	public void set_Designation(String _Designation) {
		this._Designation = _Designation;
	}

	public String get_LastVisitedOn() {
		return _LastVisitedOn;
	}

	public void set_LastVisitedOn(String _LastVisitedOn) {
		this._LastVisitedOn = _LastVisitedOn;
	}

	public String get_MobileNo() {
		return _MobileNo;
	}

	public void set_MobileNo(String _MobileNo) {
		this._MobileNo = _MobileNo;
	}

	public String get_BalanceAMount() {
		return _BalanceAMount;
	}

	public void set_BalanceAMount(String _BalanceAMount) {
		this._BalanceAMount = _BalanceAMount;
	}

	public String get_TotalNotApproved() {
		return _TotalNotApproved;
	}

	public void set_TotalNotApproved(String _TotalNotApproved) {
		this._TotalNotApproved = _TotalNotApproved;
	}

	public String get_TotalApproved() {
		return _TotalApproved;
	}

	public void set_TotalApproved(String _TotalApproved) {
		this._TotalApproved = _TotalApproved;
	}
}



//	private String _UserID = "";
//	private String _UserName = "";
//	private String _Password = "";
//
//	private String _DistCode = "";
//	private String _DistName = "";
//	private String _BlockCode = "";
//	private String _BlockName = "";
//	private String _PanchayatCode = "";
//	private String _PacsCode = "";
//	private String _PacsnName = "";
//
//	private String _Role = "";
//	private String _BankCode = "";
//	private String _PACSBankID = "";
//	private String _IMEI = "";
//	private Boolean _isAuthenticated = false;
//	private String _WardCode = "";
//
//	public UserInfo() {
//	}
//
//	public UserInfo(SoapObject obj) {
//
//		this._isAuthenticated = Boolean.parseBoolean(obj.getProperty(
//				"isAuthenticated").toString());
//		this._UserID = obj.getProperty("UserID").toString();
//		this._UserName = obj.getProperty("UserName").toString();
//		this._Password = obj.getProperty("Password").toString();
//
//		if (this._isAuthenticated) {
//			this._DistCode = obj.getProperty("DistCode").toString();
//			this._DistName = obj.getProperty("DistName").toString();
//			this._BlockCode = obj.getProperty("BlockCode").toString();
//			this._BlockName = obj.getProperty("BlockName").toString();
//			this._PanchayatCode = obj.getProperty("PanchayatCode").toString();
//			//this._PacsCode = obj.getProperty("PacsCode").toString();
//			this._PacsnName= obj.getProperty("PacsName").toString();
//			this._Role = obj.getProperty("Role").toString();
//			this._BankCode = obj.getProperty("BankCode").toString();
//			this._PACSBankID = obj.getProperty("PACSBankID").toString();
//			this._IMEI = obj.getProperty("IMEI").toString();
//			this._WardCode = obj.getProperty("WardCode").toString();
//		}
//	}
//
//	public String get_WardCode() {
//		return _WardCode;
//	}
//
//	public void set_WardCode(String _WardCode) {
//		this._WardCode = _WardCode;
//	}
//
//	@Override
//	public int getPropertyCount() {
//		// TODO Auto-generated method stub
//		return 15;
//	}
//
//	@Override
//	public Object getProperty(int index) {
//		Object object = null;
//		switch (index) {
//		case 0: {
//			object = this._isAuthenticated;
//			break;
//		}
//		case 1: {
//			object = this._UserID;
//			break;
//		}
//		case 2: {
//			object = this._UserName;
//			break;
//		}
//		case 3: {
//			object = this._Password;
//			break;
//		}
//		case 4: {
//			object = this._DistCode;
//			break;
//		}
//		case 5: {
//			object = this._BlockCode;
//			break;
//
//		}
//		case 6: {
//			object = this._PanchayatCode;
//			break;
//
//		}
//		case 7: {
//			object = this._Role;
//			break;
//
//		}
//		case 8: {
//			object = this._BankCode;
//			break;
//
//		}
//		case 9: {
//			object = this._PACSBankID;
//			break;
//		}
//		case 10: {
//			object = this.get_IMEI();
//			break;
//
//		}
//
//		}
//		return object;
//	}
//
//	// private String _DistCode = "";
//	// private String _BlockCode = "";
//	// private String _PanchayatCode = "";
//	// private String _Role = "";
//	// private String _BankCode = "";
//	// private String _PACSBankID = "";
//	// private String _IMEI = "";
//	// private Boolean _isAuthenticated = false
//
//	@Override
//	public void getPropertyInfo(int index, Hashtable arg1,
//			PropertyInfo propertyInfo) {
//		switch (index) {
//		case 0: {
//			propertyInfo.name = "isAuthenticated";
//			propertyInfo.type = PropertyInfo.BOOLEAN_CLASS;
//			break;
//		}
//		case 1: {
//			propertyInfo.name = "UserID";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 2: {
//			propertyInfo.name = "UserName";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 3: {
//			propertyInfo.name = "Password";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//
//		case 4: {
//			propertyInfo.name = "DistCode";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 5: {
//			propertyInfo.name = "BlockCode";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 6: {
//			propertyInfo.name = "PanchayatCode";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 7: {
//			propertyInfo.name = "Role";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 8: {
//			propertyInfo.name = "BankCode";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 9: {
//			propertyInfo.name = "PacsBankId";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//		case 10: {
//			propertyInfo.name = "IMEI";
//			propertyInfo.type = PropertyInfo.STRING_CLASS;
//			break;
//		}
//
//		}
//	}
//
//	@Override
//	public void setProperty(int index, Object obj) {
//		switch (index) {
//		case 0: {
//			this._isAuthenticated = Boolean.parseBoolean(obj.toString());
//			break;
//		}
//		case 1: {
//			this._UserID = obj.toString();
//			break;
//		}
//		case 2: {
//			this._UserName = obj.toString();
//			break;
//		}
//		case 3: {
//			this._Password = obj.toString();
//			break;
//		}
//
//		case 4: {
//			this._DistCode = obj.toString();
//			break;
//		}
//		case 5: {
//			this._BlockCode = obj.toString();
//			break;
//		}
//		case 6: {
//			this._PanchayatCode = obj.toString();
//			break;
//		}
//		case 7: {
//			this._Role = obj.toString();
//			break;
//		}
//
//		case 8: {
//			this._BankCode = (obj.toString());
//			break;
//		}
//		case 9: {
//			this._PACSBankID = obj.toString();
//			break;
//		}
//		case 10: {
//			this.set_IMEI(obj.toString());
//			break;
//		}
//
//		}
//	}
//
//	public String get_UserID() {
//		return _UserID;
//	}
//
//	public void set_UserID(String _UserID) {
//		this._UserID = _UserID;
//	}
//
//	public String get_UserName() {
//		return _UserName;
//	}
//
//	public void set_UserName(String _UserName) {
//		this._UserName = _UserName;
//	}
//
//	public String get_Password() {
//		return _Password;
//	}
//
//	public void set_Password(String _Password) {
//		this._Password = _Password;
//	}
//
//	public String get_DistCode() {
//		return _DistCode;
//	}
//
//	public void set_DistCode(String _DistCode) {
//		this._DistCode = _DistCode;
//	}
//
//	public String get_BlockCode() {
//		return _BlockCode;
//	}
//
//	public void set_BlockCode(String _BlockCode) {
//		this._BlockCode = _BlockCode;
//	}
//
//	public String get_BlockName() {
//		return _BlockName;
//	}
//
//	public void set_BlockName(String _BlockName) {
//		this._BlockName = _BlockName;
//	}
//
//	public String get_PanchayatCode() {
//		return _PanchayatCode;
//	}
//
//	public void set_PanchayatCode(String _PanchayatCode) {
//		this._PanchayatCode = _PanchayatCode;
//	}
//
//	public String get_PacsCode() {
//		return _PacsCode;
//	}
//
//	public void set_PacsCode(String _PacsCode) {
//		this._PacsCode = _PacsCode;
//	}
//
//	public String get_Role() {
//		return _Role;
//	}
//
//	public void set_Role(String _Role) {
//		this._Role = _Role;
//	}
//
//	public String get_BankCode() {
//		return _BankCode;
//	}
//
//	public void set_BankCode(String _BankCode) {
//		this._BankCode = _BankCode;
//	}
//
//	public String get_PACSBankID() {
//		return _PACSBankID;
//	}
//
//	public void set_PACSBankID(String _PACSBankID) {
//		this._PACSBankID = _PACSBankID;
//	}
//
//	public String get_IMEI() {
//		return _IMEI;
//	}
//
//	public void set_IMEI(String _IMEI) {
//		this._IMEI = _IMEI;
//	}
//
//	public Boolean get_isAuthenticated() {
//		return _isAuthenticated;
//	}
//
//	public void set_isAuthenticated(Boolean _isAuthenticated) {
//		this._isAuthenticated = _isAuthenticated;
//	}
//
//	public String get_PacsnName() {
//		return _PacsnName;
//	}
//
//	public void set_PacsnName(String _PacsnName) {
//		this._PacsnName = _PacsnName;
//	}
//
//	public String get_DistName() {
//		return _DistName;
//	}
//
//	public void set_DistName(String _DistName) {
//		this._DistName = _DistName;
//	}
//
//}
