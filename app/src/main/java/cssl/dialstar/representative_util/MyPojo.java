package cssl.dialstar.representative_util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyPojo {


	@JsonIgnore
	
	int id=0;
	
	String name=null;
	String firstname="";
	String middlename="";
	String lastname="";
	String emailid="";
	String mobileno="";
	String gender="";
	@Override
	public String toString() {
		return "MyPojo [id=" + id + ", name=" + name + ", firstname=" + firstname + ", middlename=" + middlename
				+ ", lastname=" + lastname + ", emailid=" + emailid + ", mobileno=" + mobileno + ", gender=" + gender
				+ ", dateofbirth=" + dateofbirth + ", country_name=" + countryname + ", country_id=" + countryid
				+ ", state_name=" + statename + ", city_name=" + cityname + ", state_id=" + stateid + "]";
	}
	String dateofbirth="";
	
	String countryname="";
	String countryid="";
	String statename="";
	String cityname="";
	String stateid="";
	
	int interestid=0;
	String categoryname="";
	
	int redeemtypeid=0;
	int offertypeid=0;
	String offername="";
	
	int advertiserid;
	String txtfirstname;
	String txtmiddlename;
	String txtlastname;
	String txtmobile;
	String txtemail;
	String datetime;
	String txtstreet;
	String txtarea;
	String DLcountry;
	String DLstate;
	String DLcity;
	String txtpincode;
	String txtusername;
	String txtpassword;
	String FileUpload1;
	
	String password;
	String confirmpassword;
	
	int userid;
	//21-8-2017 comment to String countryid and setter getter method, Add int countryid
	/*int countryid;
	
	
	
	
	public int getCountryid() {
		return countryid;
	}
	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}*/
	
	//22-8-2017
	
	int compaignid;
	String compaignname;
	String description;
	String filepath;
	String createdby;
	String path;
	
	//28/8/2017
	int qrid;
	int compaignlocationid;
	
	///30-8-2017
		int offerid;
		double offerprice;
		//4-9-2017
		double latitude;
		double longitude;
		//15-9-2017
		String shopname;












	public String getShopname() {
			return shopname;
		}
		public void setShopname(String shopname) {
			this.shopname = shopname;
		}
	public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	public int getOfferid() {
			return offerid;
		}
		public void setOfferid(int offerid) {
			this.offerid = offerid;
		}
		public double getOfferprice() {
			return offerprice;
		}
		public void setOfferprice(double offerprice) {
			this.offerprice = offerprice;
		}
	public int getCompaignlocationid() {
		return compaignlocationid;
	}
	public void setCompaignlocationid(int compaignlocationid) {
		this.compaignlocationid = compaignlocationid;
	}
	public int getQrid() {
		return qrid;
	}
	public void setQrid(int qrid) {
		this.qrid = qrid;
	}
	public int getCompaignid() {
		return compaignid;
	}
	public void setCompaignid(int compaignid) {
		this.compaignid = compaignid;
	}
	public String getCompaignname() {
		return compaignname;
	}
	public void setCompaignname(String compaignname) {
		this.compaignname = compaignname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public int getAdvertiserid() {
		return advertiserid;
	}
	public void setAdvertiserid(int advertiserid) {
		this.advertiserid = advertiserid;
	}
	public String getFileUpload1() {
		return FileUpload1;
	}
	public void setFileUpload1(String fileUpload1) {
		FileUpload1 = fileUpload1;
	}
	public String getTxtfirstname() {
		return txtfirstname;
	}
	public void setTxtfirstname(String txtfirstname) {
		this.txtfirstname = txtfirstname;
	}
	public String getTxtmiddlename() {
		return txtmiddlename;
	}
	public void setTxtmiddlename(String txtmiddlename) {
		this.txtmiddlename = txtmiddlename;
	}
	public String getTxtlastname() {
		return txtlastname;
	}
	public void setTxtlastname(String txtlastname) {
		this.txtlastname = txtlastname;
	}
	public String getTxtmobile() {
		return txtmobile;
	}
	public void setTxtmobile(String txtmobile) {
		this.txtmobile = txtmobile;
	}
	public String getTxtemail() {
		return txtemail;
	}
	public void setTxtemail(String txtemail) {
		this.txtemail = txtemail;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getTxtstreet() {
		return txtstreet;
	}
	public void setTxtstreet(String txtstreet) {
		this.txtstreet = txtstreet;
	}
	public String getTxtarea() {
		return txtarea;
	}
	public void setTxtarea(String txtarea) {
		this.txtarea = txtarea;
	}
	public String getDLcountry() {
		return DLcountry;
	}
	public void setDLcountry(String dLcountry) {
		DLcountry = dLcountry;
	}
	public String getDLstate() {
		return DLstate;
	}
	public void setDLstate(String dLstate) {
		DLstate = dLstate;
	}
	public String getDLcity() {
		return DLcity;
	}
	public void setDLcity(String dLcity) {
		DLcity = dLcity;
	}
	public String getTxtpincode() {
		return txtpincode;
	}
	public void setTxtpincode(String txtpincode) {
		this.txtpincode = txtpincode;
	}
	public String getTxtusername() {
		return txtusername;
	}
	public void setTxtusername(String txtusername) {
		this.txtusername = txtusername;
	}
	public String getTxtpassword() {
		return txtpassword;
	}
	public void setTxtpassword(String txtpassword) {
		this.txtpassword = txtpassword;
	}

	public int getOffertypeid() {
		return offertypeid;
	}
	public void setOffertypeid(int offertypeid) {
		this.offertypeid = offertypeid;
	}
	public String getOffername() {
		return offername;
	}
	public void setOffername(String offername) {
		this.offername = offername;
	}
	public int getRedeemtypeid() {
		return redeemtypeid;
	}
	public void setRedeemtypeid(int redeemtypeid) {
		this.redeemtypeid = redeemtypeid;
	}
	public int getInterestid() {
		return interestid;
	}
	public void setInterestid(int interestid) {
		this.interestid = interestid;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStateid() {
		return stateid;
	}
	public void setStateid(String state_id) {
		this.stateid = state_id;
	}
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String country_id) {
		this.countryid = country_id;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String state_name) {
		this.statename = state_name;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String city_name) {
		this.cityname = city_name;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String country_name) {
		this.countryname = country_name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
