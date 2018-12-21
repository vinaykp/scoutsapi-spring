package com.example.scoutsapi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "members")
public class Members {
    @Id
    private ObjectId _id;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String phone;

    public Members(){
    }

    public Members(ObjectId _id, String name, String email, String gender, String age, String phone) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
