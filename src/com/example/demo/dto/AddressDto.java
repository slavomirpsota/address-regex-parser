package com.example.demo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddressDto {
    private String street;
    //supisne cislo
    private String conscriptionNo;
    //orientacne cilo
    private String orientationalNo;
    private String zip;
    private String municipality;
}
