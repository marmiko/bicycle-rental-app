package database.objects;

import gui.controllers.modification.ModifyWypozyczenieController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public class Wypozyczenie extends Checkable implements Statementable<Wypozyczenie>, Modifiable {
    private long id_wypozyczenia;
    private Date data_wypozyczenia;
    private Date data_zwrotu;
    private String klient_nr_dowodu;
    private long id_prac;
    private long id_roweru;
    private long id_akcesoria;
    private double aktualnaCena = 0;


    public Wypozyczenie(long id, Date data_wypozyczenia, Date data_zwrotu, String klient_nr_dowodu, long id_prac, long id_roweru, long id_akcesoria){
        this.id_wypozyczenia = id;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_zwrotu = data_zwrotu;
        this.klient_nr_dowodu = klient_nr_dowodu;
        this.id_prac = id_prac;
        this.id_roweru = id_roweru;
        this.id_akcesoria = id_akcesoria;
    }

    public Wypozyczenie(long id, Date data_wypozyczenia, String klient_nr_dowodu, long id_prac, long id_roweru, long id_akcesoria){
        this.id_wypozyczenia = id;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_zwrotu = null;
        this.klient_nr_dowodu = klient_nr_dowodu;
        this.id_prac = id_prac;
        this.id_roweru = id_roweru;
        this.id_akcesoria = id_akcesoria;
    }

    public Wypozyczenie(long id, Date data_wypozyczenia, Date data_zwrotu, String klient_nr_dowodu, long id_prac, long id_roweru){
        this.id_wypozyczenia = id;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_zwrotu = data_zwrotu;
        this.klient_nr_dowodu = klient_nr_dowodu;
        this.id_prac = id_prac;
        this.id_roweru = id_roweru;
    }

    public Wypozyczenie(long id, Date data_wypozyczenia, String klient_nr_dowodu, long id_prac, long id_roweru){
        this.id_wypozyczenia = id;
        this.data_wypozyczenia = data_wypozyczenia;
        this.klient_nr_dowodu = klient_nr_dowodu;
        this.id_prac = id_prac;
        this.id_roweru = id_roweru;
    }

    public String toString(){
        return id_wypozyczenia + ";" + data_wypozyczenia + ";" + data_zwrotu;
    }

    public long getId_wypozyczenia(){
        return id_wypozyczenia;
    }

    public Date getData_wypozyczenia(){
        return data_wypozyczenia;
    }

    public Date getData_zwrotu(){
        return data_zwrotu;
    }

    public long getId_prac() {
        return id_prac;
    }

    public long getId_roweru() {
        return id_roweru;
    }

    public long getId_akcesoria() {
        return id_akcesoria;
    }

    public String getKlient_nr_dowodu() {
        return klient_nr_dowodu;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(data_wypozyczenia != null) stmt.setDate(i++, data_wypozyczenia);
        stmt.setString(i++, klient_nr_dowodu);
        stmt.setLong(i++, id_prac);
        stmt.setLong(i++, id_roweru);
        if(id_akcesoria > 0) stmt.setLong(i, id_akcesoria);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Wypozyczenie old) throws SQLException {
        stmt.setLong(1, id_wypozyczenia);
        stmt.setDate(2, data_wypozyczenia);
        if(data_zwrotu != null) stmt.setDate(3, data_zwrotu);
        else stmt.setNull(3, Types.DATE);
        stmt.setString(4, klient_nr_dowodu);
        stmt.setLong(5, id_prac);
        stmt.setLong(6, id_roweru);
        if(id_akcesoria > 0) stmt.setLong(7, id_akcesoria);
        else stmt.setNull(7, Types.NUMERIC);
        return old.addIdentificationToStatement(stmt, 8);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, id_wypozyczenia);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyWypozyczenieController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyWypozyczenieController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(id_wypozyczenia > 0){
            stmt.setLong(i++, id_wypozyczenia);
        }
        if(data_wypozyczenia != null){
            stmt.setDate(i++, data_wypozyczenia);
        }
        if(data_zwrotu != null){
            stmt.setDate(i++, data_zwrotu);
        }
        if(klient_nr_dowodu != null){
            stmt.setString(i++, klient_nr_dowodu);
        }
        if(id_prac > 0){
            stmt.setLong(i++, id_prac);
        }
        if(id_roweru > 0){
            stmt.setLong(i++, id_roweru);
        }
        if(id_akcesoria > 0){
            stmt.setLong(i, id_akcesoria);
        }
        return stmt;
    }

    public double getAktualnaCena() {
        return aktualnaCena;
    }

    public void setAktualnaCena(double aktualnaCena) {
        this.aktualnaCena = aktualnaCena;
    }
}
