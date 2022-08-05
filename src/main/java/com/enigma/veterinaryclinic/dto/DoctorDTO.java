package com.enigma.veterinaryclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDTO {
  private String searchByName;
  private String searchByAlumni;
  private String searchByExperience;
}
