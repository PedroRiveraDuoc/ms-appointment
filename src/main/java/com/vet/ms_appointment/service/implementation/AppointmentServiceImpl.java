package com.vet.ms_appointment.service.implementation;

import com.vet.ms_appointment.presentation.dto.AppointmentDetailDTO;
import com.vet.ms_appointment.persistence.entity.Appointment;
import com.vet.ms_appointment.persistence.repository.AppointmentRepository;
import com.vet.ms_appointment.presentation.dto.AppointmentDTO;
import com.vet.ms_appointment.service.exception.AppointmentNotFoundException;
import com.vet.ms_appointment.service.interfaces.AppointmentService;
import com.vet.ms_appointment.util.mapper.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String CUSTOMER_API = "http://localhost:8080/api/customers/";
    private final String PET_API = "http://localhost:8081/api/pets/";

    private void validateCustomerAndPet(Long customerId, Long petId) {
        try {
            restTemplate.getForEntity(CUSTOMER_API + customerId, Void.class);
            restTemplate.getForEntity(PET_API + petId, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Cliente o mascota no existen");
        }
    }

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
        return repository.findByCustomerId(customerId)
                .stream()
                .map(AppointmentMapper::toDTO)
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
        validateCustomerAndPet(dto.getCustomerId(), dto.getPetId());
        Appointment saved = repository.save(AppointmentMapper.toEntity(dto));
        return AppointmentMapper.toDTO(saved);
    }

    @Override
    public AppointmentDTO update(Long id, AppointmentDTO dto) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + id));

        validateCustomerAndPet(dto.getCustomerId(), dto.getPetId());

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

    @Override
    public AppointmentDetailDTO getDetailedById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada con ID: " + id));

        Object customer = restTemplate.getForObject(CUSTOMER_API + appointment.getCustomerId(), Object.class);
        Object pet = restTemplate.getForObject(PET_API + appointment.getPetId(), Object.class);

        return AppointmentDetailDTO.builder()
                .id(appointment.getId())
                .customer(customer)
                .pet(pet)
                .dateTime(appointment.getDateTime().toString())
                .reason(appointment.getReason())
                .build();
    }
}
