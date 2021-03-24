package database.objects;

import gui.controllers.modification.ModifyPrzegladController;

import java.sql.*;

public class Przeglad extends Checkable implements Statementable<Przeglad>, Modifiable {

    private Date dataWykonania;
    private String opis;
    private long idRoweru;
    private long pracownik;

    public Przeglad(Date dataWykonania, String opis, long idRoweru, long pracownik){
        this.dataWykonania = dataWykonania;
        this.opis = opis;
        this. idRoweru = idRoweru;
        this.pracownik = pracownik;
    }

    public Date getDataWykonania() {
        return dataWykonania;
    }

    public String getOpis() {
        return opis;
    }

    public long getIdRoweru() {
        return idRoweru;
    }

    public long getPracownik() {
        return pracownik;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setDate(i++, dataWykonania);
        if(opis!=null) stmt.setString(i++, opis);
        stmt.setLong(i++, idRoweru);
        stmt.setLong(i, pracownik);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Przeglad old) throws SQLException {
        stmt.setDate(1, dataWykonania);
        if(opis!=null) stmt.setString(2, opis);
        else stmt.setNull(2, Types.VARCHAR);
        stmt.setLong(3, idRoweru);
        stmt.setLong(4, pracownik);
        return old.addIdentificationToStatement(stmt, 5);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setDate(insertIndex, dataWykonania);
        stmt.setLong(insertIndex+1, idRoweru);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyPrzegladController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyPrzegladController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(dataWykonania != null){
            stmt.setDate(i++, dataWykonania);
        }
        if(opis != null){
            stmt.setString(i++, opis);
        }
        if(idRoweru > 0){
            stmt.setLong(i++, idRoweru);
        }
        if(pracownik > 0){
            stmt.setLong(i, pracownik);
        }
        return stmt;
    }
}
