package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.SoapObject;

public class PanchayatData {


    String Pcode="";
    String Pname="";


    /*
    <DistrictCode>string</DistrictCode>
          <BlockCode>string</BlockCode>
          <PanchayatCode>string</PanchayatCode>
          <PanchayatName>string</PanchayatName>
     */
    public static Class<PanchayatData> PanchayatData_CLASS=PanchayatData.class;


    public PanchayatData(SoapObject sobj)
    {

        this.Pcode=sobj.getProperty(0).toString();
        this.Pname=sobj.getProperty(1).toString();
    }
    public PanchayatData() {
        super();
    }
    public String getPcode() {
        return Pcode;
    }

    public void setPcode(String _pcode) {
        Pcode = _pcode;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String _pname) {
        Pname = _pname;
    }


}
