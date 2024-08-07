package com.rcs.dto.request.user;


import com.rcs.domain.base.BaseSearchRequest;
import com.rcs.enums.GenderType;
import com.rcs.enums.Status;
import com.rcs.enums.UserType;
import lombok.Data;

@Data
public class UserSearchRequest extends BaseSearchRequest {

    private Long id;

    private String firstName;
    private String lastName;
    private String contactNo;
    private String dateJoin;
    private Integer age;
    private GenderType genderType;
    private String nic;
    private String nationality;
    private String image;
    private String religion;
    private String email;
    private String userLogging;
    private String passWord;
    private String role;
    private Status status;

    private String sortProperty = "lastModifiedAt";

    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}
