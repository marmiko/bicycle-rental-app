package database.objects;

import gui.controllers.modification.ModifyPracownikController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PracownikWypozyczalni extends Checkable implements Statementable<PracownikWypozyczalni>, Modifiable{
    private String imie;
    private String nazwisko;
    private long id;
    private Date dataZatrudnienia;
    private Date dataZwolnienia;

    public PracownikWypozyczalni(String imie, String nazwisko, long id, Date dataZatrudnienia, Date dataZwolnienia){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.id = id;
        this.dataZatrudnienia = dataZatrudnienia;
        this.dataZwolnienia = dataZwolnienia;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public long getId() {
        return id;
    }

    public Date getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public Date getDataZwolnienia() {
        return dataZwolnienia;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        stmt.setString(1, imie);
        stmt.setString(2, nazwisko);
        if(dataZatrudnienia!=null) stmt.setDate(3, dataZatrudnienia);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, PracownikWypozyczalni old) throws SQLException {
        stmt.setString(1, imie);
        stmt.setString(2, nazwisko);
        stmt.setLong(3, id);
        stmt.setDate(4, dataZatrudnienia);
        stmt.setDate(5, dataZwolnienia);
        return old.addIdentificationToStatement(stmt, 6);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, id);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyPracownikController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyPracownikController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(imie != null){
            stmt.setString(i++, imie);
        }
        if(nazwisko != null){
            stmt.setString(i++, nazwisko);
        }
        if(id > 0){
            stmt.setLong(i++, id);
        }
        if(dataZatrudnienia != null){
            stmt.setDate(i++, dataZatrudnienia);
        }
        if(dataZwolnienia != null){
            stmt.setDate(i, dataZwolnienia);
        }
        return stmt;
    }
}
