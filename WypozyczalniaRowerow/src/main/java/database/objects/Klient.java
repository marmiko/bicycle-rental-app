package database.objects;

import gui.controllers.modification.ModifyKlientController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Klient extends Checkable implements Statementable<Klient>, Modifiable {

    private String nr_dowodu;
    private String imie;
    private String nazwisko;
    private int nr_telefonu;

    public Klient(String nr_dowodu, String imie, String nazwisko, int nr_telefonu){
        this.nr_dowodu = nr_dowodu;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_telefonu = nr_telefonu;
    }

    public String getNr_dowodu() {
        return nr_dowodu;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getNr_telefonu() {
        return nr_telefonu;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, nr_dowodu);
        stmt.setString(2, imie);
        stmt.setString(3, nazwisko);
        stmt.setInt(4, nr_telefonu);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Klient old) throws SQLException {
        stmt.setString(1, nr_dowodu);
        stmt.setString(2, imie);
        stmt.setString(3, nazwisko);
        stmt.setInt(4, nr_telefonu);
        return old.addIdentificationToStatement(stmt, 5);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setString(insertIndex, nr_dowodu);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyKlientController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyKlientController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(nr_dowodu != null){
            stmt.setString(i++, nr_dowodu);
        }
        if(imie != null){
            stmt.setString(i++, imie);
        }
        if(nazwisko != null){
            stmt.setString(i++, nazwisko);
        }
        if(nr_telefonu > 0){
            stmt.setInt(i, nr_telefonu);
        }
        return stmt;
    }
}
