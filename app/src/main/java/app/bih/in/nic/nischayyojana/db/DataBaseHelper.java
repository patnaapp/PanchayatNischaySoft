package app.bih.in.nic.nischayyojana.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import app.bih.in.nic.nischayyojana.entity.Block;
import app.bih.in.nic.nischayyojana.entity.CurrentPhysical;
import app.bih.in.nic.nischayyojana.entity.District;
import app.bih.in.nic.nischayyojana.entity.Fyear;
import app.bih.in.nic.nischayyojana.entity.MODEOFPAYMENT;
import app.bih.in.nic.nischayyojana.entity.NONFUNCTIONINGREASION;
import app.bih.in.nic.nischayyojana.entity.PANCHAYAT;
import app.bih.in.nic.nischayyojana.entity.PanchayatData;
import app.bih.in.nic.nischayyojana.entity.QUALITY;
import app.bih.in.nic.nischayyojana.entity.SCHEME;
import app.bih.in.nic.nischayyojana.entity.VillageData;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportGNProperty;
import app.bih.in.nic.nischayyojana.entity.WorkProgressReportProperty;
import app.bih.in.nic.nischayyojana.entity.YOJANA;
import app.bih.in.nic.nischayyojana.entity.ward;

public class DataBaseHelper extends SQLiteOpenHelper {
	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/app.bih.in.nic.epacsmgmt/databases/";
	//private static String DB_NAME = "eCountingAC.sqlite";
	//private static String DB_NAME = "PACSDB1";
	private static String DB_NAME = "PACSDB2";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 *
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 2);
		//super(context, DB_NAME, null, 2);
		if (android.os.Build.VERSION.SDK_INT >= 4.2) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist

		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 *
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

			myDataBase=checkDB;

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		this.getReadableDatabase().close();

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.e("oldVersion",""+oldVersion);
		Log.e("newVersion",""+newVersion);

//		if(newVersion>oldVersion)
//		{
//			if(newVersion==2)
//			{
//				db.execSQL("ALTER TABLE PayJaltbl ADD COLUMN BoringFtMt TEXT");
//				db.execSQL("ALTER TABLE PayJaltbl ADD COLUMN StageFtMt TEXT");
//				db.execSQL("ALTER TABLE PayJaltbl ADD COLUMN VitranFtMt TEXT");
//				db.execSQL("ALTER TABLE PayJaltbl ADD COLUMN VitranDepthFtMt TEXT");
//				Log.e("ALTER","DONE");
//
//			}
//		}
	}


	public ArrayList<District> getDistrict() {

		ArrayList<District> districtList = new ArrayList<District>();

		try {

			SQLiteDatabase db = this.getReadableDatabase();
			// ORDER BY DistName
			Cursor cur = db
					.rawQuery(
							"SELECT DistCode,DistNameHN,DistName from Districts",
							null);
			int x = cur.getCount();

			while (cur.moveToNext()) {

				District district = new District();
				district.set_DistCode(cur.getString(cur
						.getColumnIndex("DistCode")));
				district.set_DistName(cur.getString(cur
						.getColumnIndex("DistNameHN")));

				districtList.add(district);
			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
		return districtList;

	}

	public ArrayList<Block> getBlock(String distName) {

		ArrayList<Block> blockList = new ArrayList<Block>();

		try {

			//ORDER BY BlockName
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[] { distName };
			Cursor cur = db
					.rawQuery(
							"SELECT blocks.BlockCode,blocks.BlockNameHN,districts.DistName from Blocks inner join districts on blocks.DistCode = districts.DistCode WHERE districts.DistCode = ? ",
							params);
			int x = cur.getCount();

			while (cur.moveToNext()) {

				Block block = new Block();
				block.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
				block.setBlockName(cur.getString(cur.getColumnIndex("BlockNameHN")));

				blockList.add(block);
			}

			cur.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
		return blockList;

	}
	public ArrayList<PanchayatData> getPanchayatLocal(String blkId) {
		ArrayList<PanchayatData> pdetail = new ArrayList<PanchayatData>();
		try {
			//order by PanchayatName
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM  Panchayat where Blockcode='" + blkId + "'", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				PanchayatData panchayat = new PanchayatData();
				panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")).trim());
				panchayat.setPname((cur.getString(cur.getColumnIndex("PanchayatName"))).trim());
				pdetail.add(panchayat);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
		}
		return pdetail;
	}
	public String getPanchayatName(String pcode) {

		try {
			//order by PanchayatName
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM  Panchayat where PanchayatCode='" + pcode + "'", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				return  cur.getString(cur.getColumnIndex("PanchayatName")).trim();
			}
			cur.close();
			db.close();
		} catch (Exception e) {
		}
		return null;
	}
	public ArrayList<ward> getWardLocal(String panId) {
		ArrayList<ward> pdetail = new ArrayList<ward>();
		//CREATE TABLE [Ward] ( [Slno] [varchar](10) NOT NULL, [WardName] [varchar](100) NULL,
		// [WardCode] [nvarchar](10) NULL, [DistCode] [nvarchar](10) NULL, [BlockCode] [varchar](10) NOT NULL, [PanchayatCode] [nvarchar](10) NULL)
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM  Ward where PanchayatCode='" + panId + "' order by WardName", null);
			//Cursor cur = db.rawQuery("SELECT * FROM  Ward order by WardName", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				ward wd = new ward();
				wd.setWardCode(cur.getString(cur.getColumnIndex("WardCode")));
				wd.setWardname(cur.getString(cur.getColumnIndex("WardName")));
				pdetail.add(wd);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<VillageData> getVillageLocal(String panId) {
		ArrayList<VillageData> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM  VillageList where PanchayatCode='" + panId + "' order by VillageName", null);

			int x = cur.getCount();
			while (cur.moveToNext()) {
				VillageData wd = new VillageData();
				wd.setVillcode(cur.getString(cur.getColumnIndex("VillageCode")));
				wd.setVillname(cur.getString(cur.getColumnIndex("VillageName")));
				pdetail.add(wd);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<SCHEME> getSchemeList() {
		ArrayList<SCHEME> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
//	CREATE TABLE `Schemes` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `SchemeCode` TEXT, `SchemeName` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM  Schemes order by SchemeName", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				SCHEME vl = new SCHEME();
				vl.set_SchemeID(cur.getString(cur.getColumnIndex("SchemeCode")));
				vl.set_SchemeName(cur.getString(cur.getColumnIndex("SchemeNameHN")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<YOJANA> getYojanaList(String schemecode,String pancode,String wradcode) {
		ArrayList<YOJANA> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM  SubSchemes WHERE (SchemeCode='"+ schemecode +"' OR SchemeCode='0') AND PanchayatCode='"+pancode+"' AND (WardCode='"+wradcode+"' OR WardCode='0') order by SchemeCode DESC", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				YOJANA vl = new YOJANA();
				vl.set_YojanaID(cur.getString(cur.getColumnIndex("SubSchemeCode")));
				vl.set_YojanaName(cur.getString(cur.getColumnIndex("SubSchemeName")));
				vl.set_WardCode(cur.getString(cur.getColumnIndex("WardCode")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}

	public ArrayList<MODEOFPAYMENT> getPayModeList() {
		ArrayList<MODEOFPAYMENT> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
//CREATE TABLE `PaymentMode` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
// `PaymentModeID` TEXT, `PaymentModeName` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM  PaymentMode order by PaymentModeName", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				MODEOFPAYMENT vl = new MODEOFPAYMENT();
				vl.set_PayModeID  (cur.getString(cur.getColumnIndex("PaymentModeID")));
				vl.set_PayModeName(cur.getString(cur.getColumnIndex("PaymentModeName")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<NONFUNCTIONINGREASION> getNONFUNCTIONINGREASIONList() {
		ArrayList<NONFUNCTIONINGREASION> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
//	CREATE TABLE `NONFUNCTIONINGREASIONtbl` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `NONFUNCTIONINGREASION_ID` TEXT, `NONFUNCTIONINGREASION_Name` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM  NONFUNCTIONINGREASIONtbl order by NONFUNCTIONINGREASION_Name", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				NONFUNCTIONINGREASION vl = new NONFUNCTIONINGREASION();
				vl.set_NONFUNCTIONINGREASIONID(cur.getString(cur.getColumnIndex("NONFUNCTIONINGREASION_ID")));
				vl.set_NONFUNCTIONINGREASIONName(cur.getString(cur.getColumnIndex("NONFUNCTIONINGREASION_Name")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<Fyear> getFYearList() {
		ArrayList<Fyear> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
//	CREATE TABLE [FYEAR] ([FYID] TEXT ,[FYName] TEXT,[STATUS] TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM  FYEAR order by FYName", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				Fyear vl = new Fyear();
				vl.setFyId(cur.getString(cur.getColumnIndex("FYID")));
				vl.setFy(cur.getString(cur.getColumnIndex("FYName")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}

	public long setWardLocal(ArrayList<ward> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("Ward", null, null);
		ArrayList<ward> warddata = plist;
		ContentValues values = new ContentValues();

		if (warddata != null) {
			try {
				for (int i = 0; i < warddata.size(); i++) {

					values.put("WardCode", warddata.get(i).getWardCode());
					values.put("WardName", warddata.get(i).getWardname());
					//values.put("Blockcode", warddata.get(i).getBlockCode());
					values.put("PanchayatCode", warddata.get(i).getPanchayatCode());
					String[] whereArgs = new String[]{warddata.get(i).getWardCode()};
					c = db.update("Ward", values, "WardCode=?", whereArgs);
					if (!(c > 0)) {

						c = db.insert("Ward", null, values);
					}

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}
	public long setNischayLocal(ArrayList<SCHEME> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("Schemes", null, null);
		ArrayList<SCHEME> warddata = plist;
		ContentValues values = new ContentValues();

		if (warddata != null) {
			try {
				for (int i = 0; i < warddata.size(); i++) {

					values.put("SchemeCode", warddata.get(i).get_SchemeID());
					values.put("SchemeName", warddata.get(i).get_SchemeName());
					values.put("SchemeNameHN", warddata.get(i).get_SchemeNameHN());
					String[] whereArgs = new String[]{warddata.get(i).get_SchemeID(),};
					c = db.update("Schemes", values, "SchemeCode=?", whereArgs);

					if (!(c > 0)) {
						c = db.insert("Schemes", null, values);
					}

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}
	public long setVillageLocal(ArrayList<VillageData> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("VillageList", null, null);
		ArrayList<VillageData> vdata = plist;
		ContentValues values = new ContentValues();

		if (vdata != null) {
			try {
				for (int i = 0; i < vdata.size(); i++) {

					values.put("VillageCode", vdata.get(i).getVillcode());
					values.put("VillageName", vdata.get(i).getVillname());
					values.put("PanchayatCode", vdata.get(i).getPancode());
					String[] whereArgs = new String[]{vdata.get(i).getVillcode()};
					c = db.update("VillageList", values, "VillageCode=?", whereArgs);

					if (!(c > 0)) {
						c = db.insert("VillageList", null, values);
					}

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}

	public long setYojanaLocal(ArrayList<YOJANA> plist,String pancode) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("SubSchemes", null, null);
		ArrayList<YOJANA> warddata = plist;
		ContentValues values = new ContentValues();

		if (warddata != null) {
			try {
				for (int i = 0; i < warddata.size(); i++) {

					values.put("SchemeCode", warddata.get(i).get_SchemeID());
					values.put("SubSchemeCode", warddata.get(i).get_YojanaID());
					values.put("SubSchemeName", warddata.get(i).get_YojanaName());
					values.put("PanchayatCode",pancode);
					values.put("WardCode", warddata.get(i).get_WardCode());
					String[] whereArgs = new String[]{warddata.get(i).get_YojanaID()};
					c = db.update("SubSchemes", values, "SubSchemeCode=?", whereArgs);
					if (!(c > 0)) {
						c = db.insert("SubSchemes", null, values);
					}
				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}
	public long setPayModeLocal(ArrayList<MODEOFPAYMENT> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("PaymentMode", null, null);
		ArrayList<MODEOFPAYMENT> warddata = plist;
		ContentValues values = new ContentValues();

		if (warddata != null) {
			try {
				for (int i = 0; i < warddata.size(); i++) {

					values.put("PaymentModeID", warddata.get(i).get_PayModeID());
					values.put("PaymentModeName", warddata.get(i).get_PayModeName());
					String[] whereArgs = new String[]{warddata.get(i).get_PayModeID()};
					c = db.update("PaymentMode", values, "PaymentModeID=?", whereArgs);
					if (!(c > 0)) {
						c = db.insert("PaymentMode", null, values);
					}

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}
	public long setPayJalRecordsLocal(ArrayList<WorkProgressReportProperty> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("ReportPayJaltbl", null, null);
		ArrayList<WorkProgressReportProperty> PJdata = plist;
		ContentValues values = new ContentValues();

		if (PJdata != null) {
			try {
				for (int i = 0; i < PJdata.size(); i++) {

					values.put("DistCode", PJdata.get(i).getDistCode());
					values.put("DistName", PJdata.get(i).getDistName());
					values.put("BlockCode", PJdata.get(i).getBlockCode());
					values.put("BlockName", PJdata.get(i).getBlockName());
					values.put("PanCode", PJdata.get(i).getPanCode());
					values.put("PanName", PJdata.get(i).getPanName());
					values.put("WardCode", PJdata.get(i).getWardCode());
					values.put("WardName", PJdata.get(i).getWardName());
					values.put("NischayCode", PJdata.get(i).getNischayCode());
					values.put("FYear", PJdata.get(i).getFYear());
					values.put("SchemeName", PJdata.get(i).getSchemeName());
					values.put("SchemeNo", PJdata.get(i).getSchemeNum());
					values.put("RequestDate", PJdata.get(i).getRequestDate());
					values.put("SubmitDate", PJdata.get(i).getSubmitDate());
					values.put("TechAcceptedAmount", PJdata.get(i).getTechAcceptedAmount());
					values.put("TechAcceptedDate", PJdata.get(i).getTechAcceptedDate());
					values.put("PanAcceptedAmount", PJdata.get(i).getPanAcceptedAmount());
					values.put("PanAcceptedDate", PJdata.get(i).getPanAcceptedDate());
					values.put("PanNischayPrbhariName", PJdata.get(i).getPanNischayPrbhariName());
					values.put("PanNischayPrbhariMobNum", PJdata.get(i).getPanNischayPrbhariMobNum());
					values.put("PanYojanaPrbhariName", PJdata.get(i).getPanYojanaPrbhariName());
					values.put("PanYojanaPrbhariMobNum", PJdata.get(i).getPanYojanaPrbhariMobNum());
					values.put("PrashakhiDeptName", PJdata.get(i).getPrashakhiDeptName());
					values.put("MaapPustNum", PJdata.get(i).getMaapPustNumprivate());
					values.put("WorkStartingDate", PJdata.get(i).getWorkStartingDate());
					values.put("WorkEndDuration", PJdata.get(i).getWorkEndDuration());
					values.put("TotalAllotment", PJdata.get(i).getTotalAllotment());
					values.put("TotalExpenditure", PJdata.get(i).getTotalExpenditure());
					values.put("CurrentPhysicalStatus", PJdata.get(i).getCurrentPhysicalStatus());

					//---------------------------------
					values.put("YojanaCode", PJdata.get(i).getYojanaCode());
					values.put("YojanaTypeNAME", PJdata.get(i).getYojanaTypeNAME());

					//this.isBoringStartedp", PJdata.get(i).("DistrictCode").toString().trim();
					values.put("isBoringStarted", PJdata.get(i).getIsBoringStartedp());
					values.put("BoringDepth", PJdata.get(i).getBoringDepth());
					values.put("ISIMarkBoring", PJdata.get(i).getISIMarkBoring());
					values.put("UPVCPipeCMLNo", PJdata.get(i).getUPVCPipeCMLNo());

					//this.isMotorPumpStatus", PJdata.get(i).("DistrictCode").toString().trim();
					values.put("isMotorPumpStatus", PJdata.get(i).getIsMotorPumpStatus());
					values.put("ISIMarkMotorPump", PJdata.get(i).getISIMarkBoring());
					values.put("MotorPumpCapacity", PJdata.get(i).getMotorPumpCapacity());

					//this.isStaggingDone", PJdata.get(i).("DistrictCode").toString().trim();
					values.put("isStaggingDone", PJdata.get(i).getIsStaggingDone());
					values.put("TypeOfStagging", PJdata.get(i).getTypeOfStagging());
					values.put("HeightOfStagging", PJdata.get(i).getHeightOfStagging());
					values.put("TenkiCapacity", PJdata.get(i).getTenkiCapacity());
					values.put("TenkiQuantity", PJdata.get(i).getTenkiQuantity());

					//this.isVitranPranali", PJdata.get(i).("DistrictCode").toString().trim();
					values.put("isVitranPranali", PJdata.get(i).getIsVitranPranali());
					values.put("TotalLength", PJdata.get(i).getTotalLength());
					values.put("DepthFromSurfaceLevel", PJdata.get(i).getDepthFromSurfaceLevel());
					values.put("ISIMarkVitranPranali", PJdata.get(i).getISIMarkVitranPranali());
					values.put("CMLNumber", PJdata.get(i).getCMLNumber());

					values.put("isElectrictyConnection", PJdata.get(i).getIsElectrictyConnection());
					values.put("isElectConnection", PJdata.get(i).getIsElectConnection());
					values.put("ConsumerNumber", PJdata.get(i).getConsumerNumber());

					//this.isGrihJalSunyojan", PJdata.get(i).("DistrictCode").toString().trim();
					values.put("isGrihJalSunyojan", PJdata.get(i).getIsGrihJalSunyojan());
					values.put("TotalHouseNum", PJdata.get(i).getTotalHouseNum());
					values.put("WaterSupplyStatus", PJdata.get(i).getWaterSupplyStatus());
					values.put("JalStumbhKaNirman", PJdata.get(i).getJalStumbhKaNirman());
					values.put("TypeOfPipe", PJdata.get(i).getTypeOfPipe());
					values.put("FerulKaUpyog", PJdata.get(i).getFerulKaUpyog());

					values.put("isProjectCompleted", PJdata.get(i).getIsProjectCompleted());
					values.put("ProjectCompleteionDate", PJdata.get(i).getProjectCompleteionDate());
					values.put("Remarks", PJdata.get(i).getRemarks());
					values.put("CreatedBy", PJdata.get(i).getCreatedBy());
					values.put("CreatedDate", PJdata.get(i).getCreatedDate());

					values.put("Photo1", PJdata.get(i).getPhoto1()=="N"?null:PJdata.get(i).getPhoto1());
					values.put("Photo2", PJdata.get(i).getPhoto2()=="N"?null:PJdata.get(i).getPhoto2());
					values.put("Photo3", PJdata.get(i).getPhoto3()=="N"?null:PJdata.get(i).getPhoto3());
					values.put("Photo4", PJdata.get(i).getPhoto4()=="N"?null:PJdata.get(i).getPhoto4());

                    values.put("InspectorName", PJdata.get(i).getInspectorName());
                    values.put("InspectorPost", PJdata.get(i).getInspectorPost());
                    values.put("VillageCode", PJdata.get(i).getVillageCode());

					c = db.insert("ReportPayJaltbl", null, values);


				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}

	public long setGaliNaaliRecordsLocal(ArrayList<WorkProgressReportGNProperty> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		c = db.delete("ReportGaliNali", null, null);
		ArrayList<WorkProgressReportGNProperty> GNdata = plist;
		ContentValues values = new ContentValues();

		if (GNdata != null) {
			try {
				for (int i = 0; i < GNdata.size(); i++) {

					values.put("DistCode", GNdata.get(i).getDistCode());
					values.put("DistName", GNdata.get(i).getDistName());
					values.put("BlockCode", GNdata.get(i).getBlockCode());
					values.put("BlockName", GNdata.get(i).getBlockName());
					values.put("PanCode", GNdata.get(i).getPanCode());
					values.put("PanName", GNdata.get(i).getPanName());
					values.put("WardCode", GNdata.get(i).getWardCode());
					values.put("WardName", GNdata.get(i).getWardName());
					values.put("NischayCode", GNdata.get(i).getNischayCode());
					values.put("FYear", GNdata.get(i).getFYear());
					values.put("SchemeName", GNdata.get(i).getSchemeName());
					values.put("SchemeNum", GNdata.get(i).getSchemeNum());
					values.put("RequestDate", GNdata.get(i).getRequestDate());
					values.put("SubmitDate", GNdata.get(i).getSubmitDate());
					values.put("TechAcceptedAmount", GNdata.get(i).getTechAcceptedAmount());
					values.put("TechAcceptedDate", GNdata.get(i).getTechAcceptedDate());
					values.put("PanAcceptedAmount", GNdata.get(i).getPanAcceptedAmount());
					values.put("PanAcceptedDate", GNdata.get(i).getPanAcceptedDate());
					values.put("PanNischayPrbhariName", GNdata.get(i).getPanNischayPrbhariName());
					values.put("PanNischayPrbhariMobNum", GNdata.get(i).getPanNischayPrbhariMobNum());
					values.put("PanYojanaPrbhariName", GNdata.get(i).getPanYojanaPrbhariName());
					values.put("PanYojanaPrbhariMobNum", GNdata.get(i).getPanYojanaPrbhariMobNum());
					values.put("PrashakhiDeptName", GNdata.get(i).getPrashakhiDeptName());
					values.put("MaapPustNum", GNdata.get(i).getMaapPustNumprivate());
					values.put("WorkStartingDate", GNdata.get(i).getWorkStartingDate());
					values.put("WorkEndDuration", GNdata.get(i).getWorkEndDuration());
					values.put("TotalAllotment", GNdata.get(i).getTotalAllotment());
					values.put("TotalExpenditure", GNdata.get(i).getTotalExpenditure());
					values.put("CurrentPhysicalStatus", GNdata.get(i).getCurrentPhysicalStatus());
					values.put("YojanaCode", GNdata.get(i).getYojanaCode());

					values.put("YojanaTypeNAME", GNdata.get(i).getYojanaTypeNAME());

					values.put("RoadType", GNdata.get(i).getRoadType());
					values.put("PathRoadType", GNdata.get(i).getPathRoadType());

					values.put("NaliType", GNdata.get(i).getNaliType());

					values.put("JalNikasi", GNdata.get(i).getJalNikasi());
					values.put("TotalRoadDistance_Length", GNdata.get(i).getTotalRoadDistance_Length());
					values.put("TotalRoadDistance_Breadth", GNdata.get(i).getTotalRoadDistance_Breadth());
					values.put("TotalRoadDistance_Fat", GNdata.get(i).getTotalRoadDistance_Fat());

					values.put("TotalPathRoadDistance_Breadth", GNdata.get(i).getTotalPathRoadDistance_Breadth());

					values.put("TotalNaliDistance_Length", GNdata.get(i).getTotalNaliDistance_Length());
					values.put("TotalNali_Breadth", GNdata.get(i).getTotalNali_Breadth());
					values.put("TotalNali_Gharai", GNdata.get(i).getTotalNali_Gharai());

					values.put("SokhtaKiSankhya", GNdata.get(i).getSokhtaKiSankhya());
					values.put("MittiKrya", GNdata.get(i).getMittiKrya());
					values.put("HugePipe", GNdata.get(i).getHugePipe());
					//values.put("isProjectCompleted", GNdata.get(i).getIsProjectCompleted());

					values.put("ProjectCompleteionDate", GNdata.get(i).getProjectCompleteionDate());
					values.put("Remarks", GNdata.get(i).getRemarks());
					values.put("CreatedBy", GNdata.get(i).getCreatedBy());
					values.put("CreatedDate", GNdata.get(i).getCreatedDate());

//					values.put("Photo1", GNdata.get(i).getPhoto1());
//					values.put("Photo2", GNdata.get(i).getPhoto2());
//					values.put("Photo3", GNdata.get(i).getPhoto3());
//					values.put("Photo4", GNdata.get(i).getPhoto4());

					values.put("Photo1", GNdata.get(i).getPhoto1()=="N"?null:GNdata.get(i).getPhoto1());
					values.put("Photo2", GNdata.get(i).getPhoto2()=="N"?null:GNdata.get(i).getPhoto2());
					values.put("Photo3", GNdata.get(i).getPhoto3()=="N"?null:GNdata.get(i).getPhoto3());
					values.put("Photo4", GNdata.get(i).getPhoto4()=="N"?null:GNdata.get(i).getPhoto4());

                    values.put("InspectorName", GNdata.get(i).getInspectorName());
                    values.put("InspectorPost", GNdata.get(i).getInspectorPost());
					values.put("VillageCode", GNdata.get(i).getVillageCode());

					c = db.insert("ReportGaliNali", null, values);

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}

	public long setPanchayatLocal(ArrayList<PANCHAYAT> plist) {

		long c = -1;
		SQLiteDatabase db = this.getWritableDatabase();
		//c = db.delete("Panchayat", null, null);
		ArrayList<PANCHAYAT> data = plist;
		ContentValues values = new ContentValues();

		//CREATE TABLE [Panchayat]( [PanchayatCode] [varchar](8) NOT NULL, [PanchayatName] [varchar](50) NULL, [PanchayatNameHnd] [nvarchar](100) NULL, [PACName] [nvarchar](100) NULL,
		// [BlockCode] [varchar](6) NOT NULL, [PACchairmanName] [nvarchar](50) NULL, [Mobileno1] [varchar](10) NULL, [Mobileno2] [varchar](10) NULL, [PartNo] [char](1) NOT NULL, [PanchayatCodeOld] [varchar](6) NULL, [StateCode] [float] NULL, [DistrictName] [nvarchar](255) NULL, [DistrictCode] [nvarchar](255) NULL,
		// [Block Name] [nvarchar](255) NULL, [BlockCodeOld] [varchar](6) NULL )

		if (data != null) {
			try {
				for (int i = 0; i < data.size(); i++) {

					values.put("DistrictCode", data.get(i).get_DistrictCode());
					values.put("BlockCode", data.get(i).get_BlockCode());
					values.put("PanchayatCode", data.get(i).get_PanCode());
					values.put("PanchayatName", data.get(i).get_PanName().toString());
					values.put("PanchayatNameHnd", data.get(i).get_PanName().toString());
					//values.put("PartNo", "0"); //no use of this calumn
					String[] whereArgs = new String[]{data.get(i).get_PanCode()};
					c = db.update("Panchayat", values, "PanchayatCode=?", whereArgs);
					if (!(c > 0)) {

						c = db.insert("Panchayat", null, values);
					}

				}
				db.close();


			} catch (Exception e) {
				e.printStackTrace();
				return c;
			}
		}
		return c;
	}

	public ArrayList<WorkProgressReportProperty> getLocalData(String schemeCode,String panCode) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor=null;
		String[] whereArgs = new String[]{panCode};
		if(schemeCode.equalsIgnoreCase("1"))
		{
			cursor = db.rawQuery("SELECT id,wardName,SchemeName,TotalAllotment,TotalExpenditure,CurrentPhysicalStatus,CreatedDate,Remarks FROM ReportPayJaltbl WHERE PanCode=?", whereArgs);
		}
		if(schemeCode.equalsIgnoreCase("2"))
		{
			cursor = db.rawQuery("SELECT id,wardName,SchemeName,TotalAllotment,TotalExpenditure,CurrentPhysicalStatus,CreatedDate,Remarks FROM ReportGaliNali WHERE PanCode=?", whereArgs);
		}
		ArrayList<WorkProgressReportProperty> WorkList = new ArrayList<>();
		try {

			if(cursor!=null) {
				int x = cursor.getCount();
				if (x > 0) {
					while (cursor.moveToNext()) {
						WorkProgressReportProperty wi = new WorkProgressReportProperty();

						wi.setRow_id(cursor.getString(0));
						wi.setWardName(cursor.getString(1));
						wi.setYojanaTypeNAME(cursor.getString(2));
						wi.setTotalAllotment(cursor.getString(3));
						wi.setTotalExpenditure(cursor.getString(4));
						wi.setCurrentPhysicalStatus(cursor.getString(5));
						wi.setCreatedDate(cursor.getString(6));
						wi.setRemarks(cursor.getString(7));
						WorkList.add(wi);
					}
					Log.e("C COunt", "" + cursor.getCount());
					cursor.close();
					db.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			// TODO: handle exception
		}
		return WorkList;
	}
	public ArrayList<CurrentPhysical> getCurrentPhysicalList() {
		ArrayList<CurrentPhysical> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
// CREATE TABLE `NONFUNCTIONINGREASIONtbl` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `NONFUNCTIONINGREASION_ID` TEXT, `NONFUNCTIONINGREASION_Name` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM CurrentPhysical", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				CurrentPhysical vl = new CurrentPhysical();
				vl.setCurrentPhysical_Id(cur.getString(cur.getColumnIndex("CurrentPhysical_Id")));
				vl.setCurrentPhysical_Name(cur.getString(cur.getColumnIndex("CurrentPhysical_Name")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<Fyear> getFYear() {
		ArrayList<Fyear> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
// CREATE TABLE `NONFUNCTIONINGREASIONtbl` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `NONFUNCTIONINGREASION_ID` TEXT, `NONFUNCTIONINGREASION_Name` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM FYEAR", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				Fyear vl = new Fyear();
				vl.setFyId(cur.getString(cur.getColumnIndex("FYID")));
				vl.setFy(cur.getString(cur.getColumnIndex("FYNameHn")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<Fyear> get_FYearNew() {
		ArrayList<Fyear> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
// CREATE TABLE `NONFUNCTIONINGREASIONtbl` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `NONFUNCTIONINGREASION_ID` TEXT, `NONFUNCTIONINGREASION_Name` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM FYEAR", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				Fyear vl = new Fyear();
				vl.setFyId(cur.getString(cur.getColumnIndex("FYID")));
				vl.setFy(cur.getString(cur.getColumnIndex("FYName")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}
	public ArrayList<QUALITY> getQualityList() {
		ArrayList<QUALITY> pdetail = new ArrayList<>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
//CREATE TABLE `Qualitytbl` ( `id` INTEGER NOT NULL PRIMARY
// KEY AUTOINCREMENT, `QualityCode` TEXT, `QualityName` TEXT )
			Cursor cur = db.rawQuery("SELECT * FROM Qualitytbl", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				QUALITY vl = new QUALITY();
				vl.set_QualityID(cur.getString(cur.getColumnIndex("QualityCode")));
				vl.set_QualityName(cur.getString(cur.getColumnIndex("QualityName")));
				pdetail.add(vl);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdetail;
	}

	public String getYojanaName(String yid) {

		String yname="na";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM SubSchemes WHERE SubSchemeCode='"+yid+"'", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				yname = cur.getString(cur.getColumnIndex("SubSchemeName"));
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yname;
	}
	public String getProjectStatus(String pid) {

		String pname="na";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM CurrentPhysical WHERE CurrentPhysical_Id='"+pid+"'", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				pname = cur.getString(cur.getColumnIndex("CurrentPhysical_Name"));
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pname;
	}
	public String isPhotoExist(String pid) {

		String pname="na";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("SELECT * FROM ReportPayJaltbl WHERE CurrentPhysical_Id='"+pid+"'", null);
			int x = cur.getCount();
			while (cur.moveToNext()) {
				pname = cur.getString(cur.getColumnIndex("CurrentPhysical_Name"));
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pname;
	}
}