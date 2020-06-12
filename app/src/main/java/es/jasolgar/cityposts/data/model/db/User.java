package es.jasolgar.cityposts.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import es.jasolgar.cityposts.data.model.others.Address;
import es.jasolgar.cityposts.data.model.others.Company;

@Entity(tableName = "users",
        primaryKeys = {"id"})
public class User {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private long id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @SerializedName("username")
    @ColumnInfo(name = "user_name")
    @Expose
    private String username;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    @Expose
    private String email;

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("company")
    @Expose
    @Embedded
    private Company company;

    @SerializedName("address")
    @Expose
    @Embedded
    private Address address;

    @Expose
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("username", username).append("email", email).append("address", address).append("phone", phone).append("website", website).append("company", company).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User rhs = ((User) other);
        return new EqualsBuilder().append(website, rhs.website).append(address, rhs.address).append(phone, rhs.phone).append(name, rhs.name).append(company, rhs.company).append(id, rhs.id).append(email, rhs.email).append(username, rhs.username).isEquals();
    }







}
