package database.objects;

import gui.controllers.modification.ModifyUsterkaController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Usterka extends Checkable implements Statementable<Usterka>, Modifiable {
    private Date dataZgloszenia;
    private Date dataNaprawy;
    private long pracownikWpisujacy;
    private long idRoweru;
    private String opis;
    private long pracownikNaprawiajacy;

    public Date getDataZgloszenia() {
        return dataZgloszenia;
    }

    public Date getDataNaprawy() {
        return dataNaprawy;
    }

    public long getPracownikWpisujacy() {
        return pracownikWpisujacy;
    }

    public long getIdRoweru() {
        return idRoweru;
    }

    public String getOpis() {
        return opis;
    }

    public long getPracownikNaprawiajacy() {
        return pracownikNaprawiajacy;
    }


    public Usterka(Date dataZgloszenia, Date dataNaprawy, long pracownikWpisujacy, long idRoweru, String opis,
                   long pracownikNaprawiajacy){
        this.dataZgloszenia = dataZgloszenia;
        this.dataNaprawy = dataNaprawy;
        this.pracownikWpisujacy = pracownikWpisujacy;
        this.idRoweru = idRoweru;
        this.opis = opis;
        this.pracownikNaprawiajacy = pracownikNaprawiajacy;
    }

    @Override
    public void setOrigin() {
        ModifyUsterkaController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyUsterkaController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setDate(i++, dataZgloszenia);
        stmt.setLong(i++, pracownikWpisujacy);
        stmt.setLong(i++, idRoweru);
        if(opis!=null) stmt.setString(i, opis);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Usterka old) throws SQLException {
        stmt.setDate(1, dataZgloszenia);
        if(dataNaprawy!=null) stmt.setDate(2, dataNaprawy);
        else stmt.setNull(2, Types.DATE);
        stmt.setLong(3, pracownikWpisujacy);
        stmt.setLong(4, idRoweru);
        if(opis!=null) stmt.setString(5, opis);
        else stmt.setNull(5, Types.VARCHAR);
        if(pracownikNaprawiajacy>0) stmt.setLong(6, pracownikNaprawiajacy);
        else stmt.setLong(6, Types.NUMERIC);
        return old.addIdentificationToStatement(stmt, 7);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, idRoweru);
        stmt.setLong(insertIndex+1, pracownikWpisujacy);
        stmt.setDate(insertIndex+2, dataZgloszenia);
        return stmt;
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(dataZgloszenia != null){
            stmt.setDate(i++, dataZgloszenia);
        }
        if(dataNaprawy != null){
            stmt.setDate(i++, dataNaprawy);
        }
        if(pracownikWpisujacy > 0){
            stmt.setLong(i++, pracownikWpisujacy);
        }
        if(idRoweru > 0){
            stmt.setLong(i++, idRoweru);
        }
        if(opis != null){
            stmt.setString(i++, opis);
        }
        if(pracownikNaprawiajacy > 0){
            stmt.setLong(i, pracownikNaprawiajacy);
        }
        return stmt;
    }
}
