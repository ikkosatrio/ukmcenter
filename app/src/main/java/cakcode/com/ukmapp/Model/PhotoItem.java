package cakcode.com.ukmapp.Model;

import com.google.gson.annotations.SerializedName;

public class PhotoItem{

	@SerializedName("Title")
	private String title;

	@SerializedName("Photo")
	private Photo photo;

	@SerializedName("ID")
	private int iD;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setPhoto(Photo photo){
		this.photo = photo;
	}

	public Photo getPhoto(){
		return photo;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	@Override
 	public String toString(){
		return 
			"PhotoItem{" + 
			"title = '" + title + '\'' + 
			",photo = '" + photo + '\'' + 
			",iD = '" + iD + '\'' + 
			"}";
		}
}