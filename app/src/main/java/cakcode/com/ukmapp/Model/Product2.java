package cakcode.com.ukmapp.Model;

public class Product2{
	private String description;
	private String updatedAt;
	private int price;
	private int percent;
	private int idCategory;
	private String title;
	private String createdAt;
	private int id;
	private int idStore;
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
			",id = '" + id + '\'' + 
			",id_store = '" + idStore + '\'' + 
			",priceDiscount = '" + priceDiscount + '\'' + 
			"}";
		}
}
