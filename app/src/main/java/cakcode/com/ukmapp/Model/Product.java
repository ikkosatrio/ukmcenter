package cakcode.com.ukmapp.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("Description")
	private String description;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("Price")
	private int price;

	@SerializedName("Percent")
	private int percent;

	@SerializedName("id_category")
	private int idCategory;

	@SerializedName("Title")
	private String title;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("Photo")
	private List<PhotoItem> photo;

	@SerializedName("id")
	private int id;

	@SerializedName("id_store")
	private int idStore;

	@SerializedName("PriceDiscount")
	private int priceDiscount;

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setPercent(int percent){
		this.percent = percent;
	}

	public int getPercent(){
		return percent;
	}

	public void setIdCategory(int idCategory){
		this.idCategory = idCategory;
	}

	public int getIdCategory(){
		return idCategory;
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

	public void setPhoto(List<PhotoItem> photo){
		this.photo = photo;
	}

	public List<PhotoItem> getPhoto(){
		return photo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdStore(int idStore){
		this.idStore = idStore;
	}

	public int getIdStore(){
		return idStore;
	}

	public void setPriceDiscount(int priceDiscount){
		this.priceDiscount = priceDiscount;
	}

	public int getPriceDiscount(){
		return priceDiscount;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"description = '" + description + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",price = '" + price + '\'' + 
			",percent = '" + percent + '\'' + 
			",id_category = '" + idCategory + '\'' + 
			",title = '" + title + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",photo = '" + photo + '\'' + 
			",id = '" + id + '\'' + 
			",id_store = '" + idStore + '\'' + 
			",priceDiscount = '" + priceDiscount + '\'' + 
			"}";
		}
}