package com.vet.ms_appointment.presentation.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Pet ID is required")
    private Long petId;

    @Future(message = "Appointment date must be in the future")
    private LocalDateTime dateTime;

    @Size(max = 255, message = "Reason can have up to 255 characters")
    private String reason;
}