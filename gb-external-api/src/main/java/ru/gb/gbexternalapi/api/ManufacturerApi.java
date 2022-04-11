package ru.gb.gbexternalapi.api;

import org.springframework.http.ResponseEntity;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerApi {

    List<ManufacturerDto> getManufacturerList();

    ResponseEntity<?> getManufacturer(Long id);

    ResponseEntity<?> handlePost(ManufacturerDto manufacturerDto);

    ResponseEntity<?> handleUpdate(Long id, ManufacturerDto manufacturerDto);

    void deleteById();
}
