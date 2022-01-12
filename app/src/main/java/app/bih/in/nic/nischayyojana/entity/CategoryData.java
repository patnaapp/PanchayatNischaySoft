package app.bih.in.nic.nischayyojana.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by .nic on 9/10/2016.
 */
public class CategoryData implements KvmSerializable, Serializable {

    private static final long serialVersionUID = 1L;

    public static Class<CategoryData> Category_CLASS = CategoryData.class;

    private String CategoryID = "";
    private String CategoryName = "";

    public CategoryData() {
        super();
    }

    public CategoryData(SoapObject obj) {
        this.CategoryID = obj.getProperty("CategoryId").toString();
        this.CategoryName = obj.getProperty("CategoryName").toString();

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



    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
}


