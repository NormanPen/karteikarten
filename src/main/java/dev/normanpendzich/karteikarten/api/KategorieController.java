package dev.normanpendzich.karteikarten.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.normanpendzich.karteikarten.repository.Karteikarte;
import dev.normanpendzich.karteikarten.repository.KartenManager;

@RestController
@RequestMapping("kategorie")
public class KategorieController {
	
	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<List<String>> get() {
	    try {
	        
	        return ResponseEntity.ok(new KartenManager().getKategorie());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
	    }
	}
	
	/*
	@GetMapping(path = "/{kategorie}")
	public ResponseEntity<List<Karteikarte>> getKartenByKategorie(@PathVariable String kategoriename) {
	    try {
	        var li = new KartenManager().getAll(null)
	        		.stream()
	        		.filter(x->x.getKategorie().equals(kategoriename))
	        		.toList();
	        return ResponseEntity.ok(li);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
	    }
	}
    
*/
	
    @GetMapping(path = "/{kategorie}", produces = "application/json")
    public ResponseEntity<List<Karteikarte>> getAllInKategorie(
            @PathVariable("kategorie") String kategorie,
            @RequestParam(name = "user", required = false, defaultValue = "") String user) {
        try {
            List<Karteikarte> karteikarten = new KartenManager().getAll(Optional.of(user));
            List<Karteikarte> karteikartenInKategorie = karteikarten.stream()
                    .filter(kk -> kk.getKategorie().equals(kategorie))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(karteikartenInKategorie);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().eTag(e.getMessage()).build();
        }
    }
    

}
