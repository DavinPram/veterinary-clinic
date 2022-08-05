package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimalDTO {
    private String searchById;

    private String searchByName;

    private Integer searchByMinAge;

    private Integer searchByMaxAge;

    private Boolean searchByDelete;
}
