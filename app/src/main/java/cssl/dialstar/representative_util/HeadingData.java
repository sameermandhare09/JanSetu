package cssl.dialstar.representative_util;

import java.util.ArrayList;

import cssl.dialstar.representative_util.InfoData;

/**
 * Created by sameer on 4/12/17.
 */

public class HeadingData {
    String complaint_name;
    String complaint_date;
    ArrayList <InfoData> infoDatas;
    String Representative;

    public String getRepresentative() {
        return Representative;
    }

    public void setRepresentative(String representative) {
        Representative = representative;
    }

    public String getComplaint_name() {
        return complaint_name;
    }

    public void setComplaint_name(String complaint_name) {
        this.complaint_name = complaint_name;
    }

    public String getComplaint_date() {
        return complaint_date;
    }

    public void setComplaint_date(String complaint_date) {
        this.complaint_date = complaint_date;
    }

    public ArrayList<InfoData> getInfoDatas() {
        return infoDatas;
    }

    public void setInfoDatas(ArrayList<InfoData> infoDatas) {
        this.infoDatas = infoDatas;
    }
}
