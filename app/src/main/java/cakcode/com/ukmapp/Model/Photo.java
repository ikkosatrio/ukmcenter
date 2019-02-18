package cakcode.com.ukmapp.Model;

import com.google.gson.annotations.SerializedName;

public class Photo{

	@SerializedName("Small")
	private String small;

	@SerializedName("Medium")
	private String medium;

	@SerializedName("Large")
	private String large;

	@SerializedName("Original")
	private String original;

	public void setSmall(String small){
		this.small = small;
	}

	public String getSmall(){
		return small;
	}

	public void setMedium(String medium){
		this.medium = medium;
	}

	public String getMedium(){
		return medium;
	}

	public void setLarge(String large){
		this.large = large;
	}

	public String getLarge(){
		return large;
	}

	public void setOriginal(String original){
		this.original = original;
	}

	public String getOriginal(){
		return original;
	}

	@Override
 	public String toString(){
		return 
			"Photo{" + 
			"small = '" + small + '\'' + 
			",medium = '" + medium + '\'' + 
			",large = '" + large + '\'' + 
			",original = '" + original + '\'' + 
			"}";
		}
}