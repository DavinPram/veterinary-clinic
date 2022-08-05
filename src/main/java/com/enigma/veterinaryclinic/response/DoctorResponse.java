package com.enigma.veterinaryclinic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorResponse {
    private String id;

    private String name;

    private String alumni;

    private String description;

    private String experience;

    private String sip;

//    @Lob
//    private byte[] image;

    private String url;
}
