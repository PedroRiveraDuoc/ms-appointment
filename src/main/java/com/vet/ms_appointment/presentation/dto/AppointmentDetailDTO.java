package com.vet.ms_appointment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailDTO {
    private Long id;
    private Object customer;
    private Object pet;
    private String dateTime;
    private String reason;
}