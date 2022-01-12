package app.bih.in.nic.nischayyojana.entity;

public class EntityInfo {
	// SupplyToSFC, SupplyToRM_MillName,
	// SupplyToRM_PaddyQty, RcptFromRiceMill_MillName,
	// RcptFromRiceMill_CMRQty, SupToSFC_CMR_CMRQty;

	private int _id;
	private String _DistCode;
	private String _BlockCode;
	private String _PanchayatCode;
	private String _PacsCode;
	private String _FYearID;

	private String _ProcQuantity;
	private String _NoOfFarmers;

	private String _SupplyToSFC;

//	private String _SupplyToRM_MillName;
//	private String _SupplyToRM_Address;

	private String _SupplyToRM_MillId;
	private String _SupplyToRM_PaddyQty;

//	private String _RcptFromRiceMill_MillName;
//	private String _RcptFromRiceMill_Address;
	private String _RcptFromRiceMill_MillId;
	private String _RcptFromRiceMill_CMRQty;

	private String _SupToSFC_CMR_CMRQty;

	private String _UploadedBy;
	private String _UserRole;
	private String _EntryDate;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
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

	public String get_PanchayatCode() {
		return _PanchayatCode;
	}

	public void set_PanchayatCode(String _PanchayatCode) {
		this._PanchayatCode = _PanchayatCode;
	}

	public String get_PacsCode() {
		return _PacsCode;
	}

	public void set_PacsCode(String _PacsCode) {
		this._PacsCode = _PacsCode;
	}

	public String get_FYearID() {
		return _FYearID;
	}

	public void set_FYearID(String _FYearID) {
		this._FYearID = _FYearID;
	}

	public String get_ProcQuantity() {
		return _ProcQuantity;
	}

	public void set_ProcQuantity(String _ProcQuantity) {
		this._ProcQuantity = _ProcQuantity;
	}

	public String get_NoOfFarmers() {
		return _NoOfFarmers;
	}

	public void set_NoOfFarmers(String _NoOfFarmers) {
		this._NoOfFarmers = _NoOfFarmers;
	}

	public String get_SupplyToSFC() {
		return _SupplyToSFC;
	}

	public void set_SupplyToSFC(String _SupplyToSFC) {
		this._SupplyToSFC = _SupplyToSFC;
	}

//	public String get_SupplyToRM_MillName() {
//		return _SupplyToRM_MillName;
//	}
//
//	public void set_SupplyToRM_MillName(String _SupplyToRM_MillName) {
//		this._SupplyToRM_MillName = _SupplyToRM_MillName;
//	}
//
//	public String get_SupplyToRM_Address() {
//		return _SupplyToRM_Address;
//	}
//
//	public void set_SupplyToRM_Address(String _SupplyToRM_Address) {
//		this._SupplyToRM_Address = _SupplyToRM_Address;
//	}

	public String get_SupplyToRM_PaddyQty() {
		return _SupplyToRM_PaddyQty;
	}

	public void set_SupplyToRM_PaddyQty(String _SupplyToRM_PaddyQty) {
		this._SupplyToRM_PaddyQty = _SupplyToRM_PaddyQty;
	}

//	public String get_RcptFromRiceMill_MillName() {
//		return _RcptFromRiceMill_MillName;
//	}
//
//	public void set_RcptFromRiceMill_MillName(String _RcptFromRiceMill_MillName) {
//		this._RcptFromRiceMill_MillName = _RcptFromRiceMill_MillName;
//	}
//
//	public String get_RcptFromRiceMill_Address() {
//		return _RcptFromRiceMill_Address;
//	}
//
//	public void set_RcptFromRiceMill_Address(String _RcptFromRiceMill_Address) {
//		this._RcptFromRiceMill_Address = _RcptFromRiceMill_Address;
//	}

	public String get_RcptFromRiceMill_CMRQty() {
		return _RcptFromRiceMill_CMRQty;
	}

	public void set_RcptFromRiceMill_CMRQty(String _RcptFromRiceMill_CMRQty) {
		this._RcptFromRiceMill_CMRQty = _RcptFromRiceMill_CMRQty;
	}

	public String get_SupToSFC_CMR_CMRQty() {
		return _SupToSFC_CMR_CMRQty;
	}

	public void set_SupToSFC_CMR_CMRQty(String _SupToSFC_CMR_CMRQty) {
		this._SupToSFC_CMR_CMRQty = _SupToSFC_CMR_CMRQty;
	}

	public String get_UploadedBy() {
		return _UploadedBy;
	}

	public void set_UploadedBy(String _UploadedBy) {
		this._UploadedBy = _UploadedBy;
	}

	public String get_UserRole() {
		return _UserRole;
	}

	public void set_UserRole(String _UserRole) {
		this._UserRole = _UserRole;
	}

	public String get_EntryDate() {
		return _EntryDate;
	}

	public void set_EntryDate(String _EntryDate) {
		this._EntryDate = _EntryDate;
	}

	public String get_SupplyToRM_MillId() {
		return _SupplyToRM_MillId;
	}

	public void set_SupplyToRM_MillId(String _SupplyToRM_MillId) {
		this._SupplyToRM_MillId = _SupplyToRM_MillId;
	}

	public String get_RcptFromRiceMill_MillId() {
		return _RcptFromRiceMill_MillId;
	}

	public void set_RcptFromRiceMill_MillId(String _RcptFromRiceMill_MillId) {
		this._RcptFromRiceMill_MillId = _RcptFromRiceMill_MillId;
	}

}
