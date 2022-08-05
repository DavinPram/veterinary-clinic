package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CageDTO {
    private String searchByID;

    private String searchByCageName;

    private Boolean searchByDelete;
}
