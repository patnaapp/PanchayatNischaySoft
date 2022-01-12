package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.SoapObject;

public class VillageData {

    String villcode="";
    String villname="";
    String distcode="";
    String distname="";
    String blockcode="";
    String pancode="";

    public static Class<VillageData> VillageData_CLASS=VillageData.class;

    /*
    <PanchayatCode>96397</PanchayatCode>
<VILLCODE>250258</VILLCODE>
<VILLNAME>Itawan</VILLNAME>
     */
    public VillageData(SoapObject sobj)
    {

        this.villcode=sobj.getProperty("VILLCODE").toString();
        this.villname=sobj.getProperty("VILLNAME").toString();
        this.pancode=sobj.getProperty("PanchayatCode").toString();
    }
    public VillageData() {
        super();
    }

    public String getDistcode() {
        return distcode;
    }

    public String getBlockcode() {
        return blockcode;
    }

    public void setBlockcode(String blockcode) {
        this.blockcode = blockcode;
    }

    public void setDistcode(String distcode) {
        this.distcode = distcode;
    }

    public String getDistname() {
        return distname;
    }

    public void setDistname(String distname) {
        this.distname = distname;
    }

    public String getVillcode() {
        return villcode;
    }

    public void setVillcode(String villcode) {
        this.villcode = villcode;
    }

    public String getVillname() {
        return villname;
    }

    public void setVillname(String villname) {
        this.villname = villname;
    }

    public String getPancode() {
        return pancode;
    }

    public void setPancode(String pancode) {
        this.pancode = pancode;
    }
}
