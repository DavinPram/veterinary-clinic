package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperatorDTO {

    private String searchById;

    private String searchByName;

    private Boolean searchByDelete;
}
