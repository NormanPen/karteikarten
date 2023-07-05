package dev.normanpendzich.karteikarten.test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import org.junit.jupiter.api.Test;

import dev.normanpendzich.karteikarten.repository.Karteikarte;
import dev.normanpendzich.karteikarten.repository.KartenManager;

class KartenManagerTest {

	@Test
	void testGetAll() {
		try {
			var mgr = new KartenManager();
			//assertTrue(mgr.getAll(null).size()>=1);
			for (var item : mgr.getAll(Optional.of("Kay"))) {
				System.out.println(item.getUser());
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void testInsert() {
		try {
			Karteikarte neu = new Karteikarte();
			neu.setFrage("Wie gehts dir=");
			neu.setAntwort("So lala");
			neu.setKategorie("Weiß der Geier");
			var mgr = new KartenManager();
			mgr.insert(neu);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
