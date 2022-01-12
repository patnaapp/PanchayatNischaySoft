package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by .nic on 9/10/2016.
 */
public class BankData implements KvmSerializable, Serializable {

    private static final long serialVersionUID = 1L;

    public static Class<BankData> Bank_CLASS = BankData.class;

    private String BankID = "";
    private String BankName = "";
    private String BankType = "";
    public BankData() {
        super();
    }

    public BankData(SoapObject obj) {
        //BankCode,BankName,BankType
        this.BankID = obj.getProperty("BankID").toString();
        this.BankName = obj.getProperty("BankName").toString();
        this.BankType = obj.getProperty("BankType").toString();
    }

    @Override
    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    public String getBankType() {
        return BankType;
    }

    public void setBankType(String bankType) {
        BankType = bankType;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String BankID) {
        this.BankID = BankID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }
}


