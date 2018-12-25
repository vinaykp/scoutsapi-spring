package com.example.scoutsapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Getter @Setter @NoArgsConstructor
@Document(collection = "members")
public class Members {
    @Id
    private ObjectId _id;
    private String memberId;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String phone;

    public Members(String memberId, String name, String email, String gender, String age, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phone = phone;


    }


}
