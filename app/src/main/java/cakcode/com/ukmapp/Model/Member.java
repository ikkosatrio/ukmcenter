package cakcode.com.ukmapp.Model;

import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("DateOfBirth")
    private String dateOfBirth;

    @SerializedName("Status")
    private String status;

    @SerializedName("Email")
    private String email;

    @SerializedName("CodeActivation")
    private String codeActivation;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("Username")
    private String username;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("Name")
    private String name;

    @SerializedName("Password")
    private String password;

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCodeActivation(String codeActivation) {
        this.codeActivation = codeActivation;
    }

    public String getCodeActivation() {
        return codeActivation;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdStore(int id_store) {
        this.id_store = id_store;
    }

    public int getIdStore() {
        return id_store;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return
                "Member{" +
                        "dateOfBirth = '" + dateOfBirth + '\'' +
                        ",status = '" + status + '\'' +
                        ",email = '" + email + '\'' +
                        ",codeActivation = '" + codeActivation + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",username = '" + username + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",id = '" + id + '\'' +
                        ",gender = '" + gender + '\'' +
                        ",name = '" + name + '\'' +
                        ",password = '" + password + '\'' +
                        "}";
    }
}