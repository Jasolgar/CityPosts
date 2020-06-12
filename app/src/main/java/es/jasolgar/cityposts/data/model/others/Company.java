package es.jasolgar.cityposts.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;


public class Company {

    public Company(){}

    @SerializedName("name")
    @Expose
    private String companyName;

    @SerializedName("catchPhrase")
    @Expose
    private String catchPhrase;

    @SerializedName("bs")
    @Expose
    private String bs;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public String toString() {
        return companyName + '\n' + bs;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Company)) {
            return false;
        }
        Company rhs = ((Company) other);
        return new EqualsBuilder().append(companyName, rhs.companyName).append(bs, rhs.bs).append(catchPhrase, rhs.catchPhrase).isEquals();
    }

}