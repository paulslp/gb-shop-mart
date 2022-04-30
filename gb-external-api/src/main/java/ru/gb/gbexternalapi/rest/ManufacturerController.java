package ru.gb.gbexternalapi.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manufacturer")
public class ManufacturerController {

    private final ManufacturerGateway manufacturerGateway;

    public ManufacturerController(
            @Qualifier("manufacturerIntGateway") ManufacturerGateway manufacturerGateway
    ) {
        this.manufacturerGateway = manufacturerGateway;
    }

    @GetMapping
    List<ManufacturerDto> getManufacturerList() {
        return manufacturerGateway.getManufacturerList();
    }

    @GetMapping({"/{manufacturerId}"})
    ResponseEntity<ManufacturerDto> getManufacturer(@PathVariable("manufacturerId") Long id) {
        return manufacturerGateway.getManufacturer(id);
    }

    @PostMapping
    ResponseEntity<ManufacturerDto> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.handlePost(manufacturerDto);
    }

    @PutMapping({"/{manufacturerId}"})
    ResponseEntity<ManufacturerDto> handleUpdate(@PathVariable("manufacturerId") Long id,
                                                 @Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.handleUpdate(id, manufacturerDto);
    }

    @DeleteMapping({"/{manufacturerId}"})
    void deleteById(@PathVariable("manufacturerId") Long id) {
        manufacturerGateway.deleteById(id);
    }
}
