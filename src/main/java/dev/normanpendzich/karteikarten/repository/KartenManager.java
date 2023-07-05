package dev.normanpendzich.karteikarten.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KartenManager {
	private Connection con;

	public KartenManager(String sqliteDbFileName) throws Exception {
		String url = "jdbc:sqlite:" + sqliteDbFileName;
		con = (DriverManager.getConnection(url));
	}

	public KartenManager(Connection con) {
		this.con = con;
	}

	public KartenManager() throws Exception {
		this("D:\\Github\\05.07.23\\karteikarten.db");
	}

	public List<Karteikarte> getAll(Optional<String> username) throws Exception {
		List<Karteikarte> ergebnis = new ArrayList<Karteikarte>();
		PreparedStatement stmt = con.prepareStatement("""
				SELECT karte.id, frage, antwort, kategorie_id, name as kategorie_name
					FROM karte
					INNER JOIN kategorie on karte.kategorie_id=kategorie.id
				""");

		PreparedStatement stmtFlag = con.prepareStatement("""
				Select *
					from flag
					INNER JOIN user on flag.user_id=user.id
				where karte_id=? and name=?
								""");

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Karteikarte kk = new Karteikarte();
			kk.setId(rs.getInt("id"));
			kk.setFrage(rs.getString("frage"));
			kk.setAntwort(rs.getString("antwort"));
			kk.setKategorie(rs.getString("kategorie_name"));
			if (username!=null &&username.isPresent()) {
				stmtFlag.setInt(1, kk.getId());
				stmtFlag.setString(2, username.get());
				ResultSet rsFlag=stmtFlag.executeQuery();
				if (rsFlag.next()) {
					kk.setGeaendert(rsFlag.getString("geaendert"));
					kk.setFlag(rsFlag.getInt("flag")==0?false:true);
					kk.setUser(rsFlag.getString("name"));
				}
			}
			ergebnis.add(kk);
		}
		return ergebnis;
	}
	
	
	
	public void insert(Karteikarte neu) throws Exception {
		var stmt = con.prepareStatement("insert into karte (frage,antwort,kategorie_id) values(?,?,?)");
		stmt.setString(1, neu.getFrage());
		stmt.setString(2, neu.getAntwort());
		stmt.setInt(3, getKategorieId(neu.getKategorie()));
		stmt.execute();
		con.close();
	}
	
	private int getKategorieId(String kategoriename) throws Exception{
		var stmtKategorie = con.prepareStatement("select * from kategorie where name = ?");
		stmtKategorie.setString(1, kategoriename);
		ResultSet rsKategorie = stmtKategorie.executeQuery();
		if (rsKategorie.next()) {	// Wenn ja, dann existiert Kategorie schon
			return rsKategorie.getInt("id");
		}
		else {
			con.createStatement().execute("insert into kategorie (name) values ('%s')".formatted(kategoriename));
			return getKategorieId(kategoriename); // Rekursion
		}
		
	}
	
	public List<String> getKategorie() throws Exception {
	    List<String> ergebnis = new ArrayList<>();
	    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM kategorie");
	    while (rs.next()) {
	        ergebnis.add(rs.getString("name"));
	    }
	    return ergebnis;
	}

	


}
