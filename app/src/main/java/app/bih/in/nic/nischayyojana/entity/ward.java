package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.SoapObject;

public class ward {

    String wardCode="";
    String wardname="";
    String blockCode="";
    String panchayatCode="";

    public static Class<ward> ward_CLASS=ward.class;
    public ward(SoapObject sobj,String pancode)
    {

        this.wardCode=sobj.getProperty(0).toString();
        this.wardname=sobj.getProperty(1).toString();
        this.panchayatCode=pancode;
    }
    public ward() {
        super();
    }
    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String _wardCode) {
        this.wardCode = _wardCode;
    }

    public String getWardname() {
        return wardname;
    }

    public void setWardname(String _wardname) {
        this.wardname = _wardname;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getPanchayatCode() {
        return panchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        this.panchayatCode = panchayatCode;
    }
}
