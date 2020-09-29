package cssl.dialstar.representative_util;

/**
 * Created by sameer on 2/12/17.
 */

public class RepresentativeDataModel {
    String repimage;
    String repname;
    int repwardno;
    int issuenumber;
    public RepresentativeDataModel(String repimage,String repname,int repwardno,int issuenumber){
        this.repimage = repimage;
        this.repname = repname;
        this.repwardno = repwardno;
        this.issuenumber = issuenumber;

    }
    public RepresentativeDataModel(){

    }

    public String getRepimage() {
        return repimage;
    }

    public void setRepimage(String repimage) {
        this.repimage = repimage;
    }

    public String getRepname() {
        return repname;
    }

    public void setRepname(String repname) {
        this.repname = repname;
    }

    public int getRepwardno() {
        return repwardno;
    }

    public void setRepwardno(int repwardno) {
        this.repwardno = repwardno;
    }

    public int getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(int issuenumber) {
        this.issuenumber = issuenumber;
    }
}
