package com.vet.ms_appointment.service.implementation;

import com.vet.ms_appointment.persistence.entity.Appointment;
import com.vet.ms_appointment.persistence.repository.AppointmentRepository;
import com.vet.ms_appointment.presentation.dto.AppointmentDTO;
import com.vet.ms_appointment.service.exception.AppointmentNotFoundException;
import com.vet.ms_appointment.service.interfaces.AppointmentService;
import com.vet.ms_appointment.util.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Override
    public List<AppointmentDTO> getAll() {
        return repository.findAll().stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + id));
        return AppointmentMapper.toDTO(appointment);
    }

    @Override
public List<AppointmentDTO> findByCustomerId(Long customerId) {
    return repository.findByCustomerId(customerId) // Cambiado de appointmentRepository a repository
            .stream()
            .map(AppointmentMapper::toDTO) // Usar el método estático AppointmentMapper::toDTO
            .collect(Collectors.toList());
}

    @Override
    public List<AppointmentDTO> findByPetId(Long petId) {
        return repository.findByPetId(petId)
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO create(AppointmentDTO dto) {
        Appointment saved = repository.save(AppointmentMapper.toEntity(dto));
        return AppointmentMapper.toDTO(saved);
    }

    @Override
    public AppointmentDTO update(Long id, AppointmentDTO dto) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + id));

        existing.setCustomerId(dto.getCustomerId());
        existing.setPetId(dto.getPetId());
        existing.setDateTime(dto.getDateTime());
        existing.setReason(dto.getReason());

        return AppointmentMapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new AppointmentNotFoundException("Cita no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
}