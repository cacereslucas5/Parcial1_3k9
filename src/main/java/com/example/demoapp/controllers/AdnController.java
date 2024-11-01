package com.example.demoapp.controllers;

import com.example.demoapp.dto.StatsDto;
import com.example.demoapp.entities.Adn;
import com.example.demoapp.services.AdnServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/adn")
public class AdnController extends BaseControllerImpl<Adn, AdnServiceImpl> {

    @PostMapping(path = "/mutant/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveAdn(@RequestBody Adn adn){
        try {


            List<String> adnList = adn.getAdnList();


            Optional<Adn> adnBuscado= servicio.findByAdnValue(adn.getAdnListAsString());
            if (adnBuscado.isPresent()) {

                adn = adnBuscado.get();

            } else {
                adn = servicio.saveAdn(adnList);
            }

            if (adn.isMutant()) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Es un mutante\"}");
            } else {
                // Si no es mutante, devolver 403 Forbidden
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\": \"No es un mutante\"}");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \""+ e.getMessage() + "\"}");
        }
    }

    @GetMapping(path = "/stats", produces = "application/json")
    public ResponseEntity<StatsDto> getStats() {
        StatsDto stats = servicio.getStats();
        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
