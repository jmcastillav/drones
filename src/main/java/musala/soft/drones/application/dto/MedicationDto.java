package musala.soft.drones.application.dto;

import java.net.URL;
import lombok.Builder;

@Builder
public record MedicationDto(String name, Integer weight, String code, URL imageUrl) {}
