package cakcode.com.ukmapp.Model;

import com.google.gson.annotations.SerializedName;

public class Store{

	@SerializedName("URLSegment")
	private String uRLSegment;

	@SerializedName("Description")
	private String description;

	@SerializedName("Address")
	private String address;

	@SerializedName("id_member")
	private int idMember;

	@SerializedName("ProvinceIDRajaOnkir")
	private String provinceIDRajaOnkir;

	@SerializedName("Title")
	private String title;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("City")
	private String city;

	@SerializedName("Image")
	private ImagePhoto image;

	@SerializedName("Province")
	private String province;

	@SerializedName("CityIDRajaOnkir")
	private String cityIDRajaOnkir;

	@SerializedName("DistrictIDRajaOnkir")
	private String districtIDRajaOnkir;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("Slogan")
	private String slogan;

	@SerializedName("id")
	private int id;

	@SerializedName("District")
	private String district;

	public void setURLSegment(String uRLSegment){
		this.uRLSegment = uRLSegment;
	}

	public String getURLSegment(){
		return uRLSegment;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setIdMember(int idMember){
		this.idMember = idMember;
	}

	public int getIdMember(){
		return idMember;
	}

	public void setProvinceIDRajaOnkir(String provinceIDRajaOnkir){
		this.provinceIDRajaOnkir = provinceIDRajaOnkir;
	}

	public String getProvinceIDRajaOnkir(){
		return provinceIDRajaOnkir;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setImage(ImagePhoto image){
		this.image = image;
	}

	public ImagePhoto getImage(){
		return image;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCityIDRajaOnkir(String cityIDRajaOnkir){
		this.cityIDRajaOnkir = cityIDRajaOnkir;
	}

	public String getCityIDRajaOnkir(){
		return cityIDRajaOnkir;
	}

	public void setDistrictIDRajaOnkir(String districtIDRajaOnkir){
		this.districtIDRajaOnkir = districtIDRajaOnkir;
	}

	public String getDistrictIDRajaOnkir(){
		return districtIDRajaOnkir;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSlogan(String slogan){
		this.slogan = slogan;
	}

	public String getSlogan(){
		return slogan;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	@Override
 	public String toString(){
		return 
			"Store{" + 
			"uRLSegment = '" + uRLSegment + '\'' + 
			",description = '" + description + '\'' + 
			",address = '" + address + '\'' + 
			",id_member = '" + idMember + '\'' + 
			",provinceIDRajaOnkir = '" + provinceIDRajaOnkir + '\'' + 
			",title = '" + title + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",city = '" + city + '\'' + 
			",image = '" + image + '\'' +
			",province = '" + province + '\'' + 
			",cityIDRajaOnkir = '" + cityIDRajaOnkir + '\'' + 
			",districtIDRajaOnkir = '" + districtIDRajaOnkir + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",slogan = '" + slogan + '\'' + 
			",id = '" + id + '\'' + 
			",district = '" + district + '\'' + 
			"}";
		}
}