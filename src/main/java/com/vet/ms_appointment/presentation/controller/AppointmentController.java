package com.vet.ms_appointment.presentation.controller;

import com.vet.ms_appointment.presentation.dto.AppointmentDTO;
import com.vet.ms_appointment.presentation.dto.AppointmentDetailDTO;
import com.vet.ms_appointment.service.interfaces.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<AppointmentDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(appointmentService.findByCustomerId(customerId));
    }

    @GetMapping("/{id}/details")
public ResponseEntity<AppointmentDetailDTO> getDetails(@PathVariable Long id) {
    return ResponseEntity.ok(appointmentService.getDetailedById(id));
}

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @Valid @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}