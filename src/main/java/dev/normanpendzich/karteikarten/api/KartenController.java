package dev.normanpendzich.karteikarten.api;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.normanpendzich.karteikarten.repository.Karteikarte;
import dev.normanpendzich.karteikarten.repository.KartenManager;



@RestController
@RequestMapping("kk")
public class KartenController {
	
	
	@GetMapping(path = "all",produces = "application/json")
    public ResponseEntity<List<Karteikarte>> getAll(@RequestParam(name = "user", required = false, defaultValue = "") String user){
        try {
            return ResponseEntity.ok(new KartenManager().getAll(Optional.of(user)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
        }
    }
	
    @GetMapping(path = "/{id}",produces = "application/json")
    public ResponseEntity<Karteikarte> getOne(
            @PathVariable int id,
            @RequestParam(name = "user", required = false, defaultValue = "") String user){
        try {
            return ResponseEntity.ok(new KartenManager().getAll(Optional.of(user))
                    .stream()
                    .filter(x->x.getId()==id)
                    .findFirst()
                    .get());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
        }
    }
    
    @PostMapping(path = "/insert", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> insertKarte(@RequestBody Karteikarte neu) {
        try {
            KartenManager kartenManager = new KartenManager();
            kartenManager.insert(neu);
            return ResponseEntity.ok("Frage eingefügt");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
        }
    }
}

