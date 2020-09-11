package com.tauraimunya.gadsleaderboard.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubmitProjectModel implements Parcelable {
    private String firstName;
    private String lastName;
    private String businessEmail;
    private String projectLink;

    public SubmitProjectModel() {

    }

    public SubmitProjectModel(String firstName, String lastName, String businessEmail, String projectLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.businessEmail = businessEmail;
        this.projectLink = projectLink;
    }

    protected SubmitProjectModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        businessEmail = in.readString();
        projectLink = in.readString();
    }

    public static final Creator<SubmitProjectModel> CREATOR = new Creator<SubmitProjectModel>() {
        @Override
        public SubmitProjectModel createFromParcel(Parcel in) {
            return new SubmitProjectModel(in);
        }

        @Override
        public SubmitProjectModel[] newArray(int size) {
            return new SubmitProjectModel[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    @Override
    public String toString() {
        return "SubmitProjectModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", projectLink='" + projectLink + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(businessEmail);
        parcel.writeString(projectLink);
    }
}

