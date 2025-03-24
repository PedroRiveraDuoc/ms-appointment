package com.vet.ms_appointment.util.mapper;

import com.vet.ms_appointment.persistence.entity.Appointment;
import com.vet.ms_appointment.presentation.dto.AppointmentDTO;

public class AppointmentMapper {
    public static AppointmentDTO toDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .customerId(appointment.getCustomerId())
                .petId(appointment.getPetId())
                .dateTime(appointment.getDateTime())
                .reason(appointment.getReason())
                .build();
    }

    public static Appointment toEntity(AppointmentDTO dto) {
        return Appointment.builder()
                .customerId(dto.getCustomerId())
                .petId(dto.getPetId())
                .dateTime(dto.getDateTime())
                .reason(dto.getReason())
                .build();
    }
}