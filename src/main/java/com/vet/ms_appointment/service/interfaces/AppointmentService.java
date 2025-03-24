package com.vet.ms_appointment.service.interfaces;

import com.vet.ms_appointment.presentation.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> getAll();
    List<AppointmentDTO> findByCustomerId(Long customerId);
    List<AppointmentDTO> findByPetId(Long petId);
    AppointmentDTO getById(Long id);
    AppointmentDTO create(AppointmentDTO dto);
    AppointmentDTO update(Long id, AppointmentDTO dto);
    void delete(Long id);
}