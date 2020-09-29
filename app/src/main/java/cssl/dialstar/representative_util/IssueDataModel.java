package cssl.dialstar.representative_util;

/**
 * Created by sameer on 1/12/17.
 */

public class IssueDataModel {
    String issuename;
    int issuenumber;
    String image;
    public IssueDataModel(String issuename, int issuenumber, String image){
        this.issuename=issuename;
        this.issuenumber=issuenumber;
        this.image=image;
    }

    public IssueDataModel() {

    }


    public void setIssuename(String issuename) {
        this.issuename = issuename;
    }

    public void setIssuenumber(int issuenumber) {
        this.issuenumber = issuenumber;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getName(){

        return issuename;
    }

    public int getIssuenumber(){
        return issuenumber;
    }

    public String getImage(){
        return image;
    }
}
