package database.objects;

import gui.controllers.modification.ModifySubskrybcjaController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public class Subskrybcja extends Checkable implements Statementable<Subskrybcja>, Modifiable {

    private Date dataRozpoczecia;
    private String klientNrDowodu;
    private long idPrac;
    private long idRoweru;
    private long idAkcesorium;
    private Date dataZakonczenia;
    private double cenaZaMiesiac = 0;

    public Date getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public String getKlientNrDowodu() {
        return klientNrDowodu;
    }

    public long getIdPrac() {
        return idPrac;
    }

    public long getIdRoweru() {
        return idRoweru;
    }

    public long getIdAkcesorium() {
        return idAkcesorium;
    }

    public Date getDataZakonczenia() {
        return dataZakonczenia;
    }

    public Subskrybcja(Date dataRozpoczecia, String klientNrDowodu, long idPrac, long idRoweru, long idAkcesorium,
                       Date dataZakonczenia) {
        this.dataRozpoczecia = dataRozpoczecia;
        this.klientNrDowodu = klientNrDowodu;
        this.idPrac = idPrac;
        this.idRoweru = idRoweru;
        this.idAkcesorium = idAkcesorium;
        this.dataZakonczenia = dataZakonczenia;
    }

    public String toString(){
        return idRoweru + ";" + dataRozpoczecia + ";" + dataZakonczenia;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(dataRozpoczecia != null) stmt.setDate(i++, dataRozpoczecia);
        stmt.setString(i++, klientNrDowodu);
        stmt.setLong(i++, idPrac);
        stmt.setLong(i++, idRoweru);
        if(idAkcesorium > 0) stmt.setLong(i, idAkcesorium);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Subskrybcja old) throws SQLException {
        stmt.setDate(1, dataRozpoczecia);
        stmt.setString(2, klientNrDowodu);
        stmt.setLong(3, idPrac);
        stmt.setLong(4, idRoweru);
        if(idAkcesorium > 0) stmt.setLong(5, idAkcesorium);
        else stmt.setNull(5, Types.NUMERIC);
        if(dataZakonczenia != null) stmt.setDate(6, dataZakonczenia);
        else stmt.setNull(6, Types.DATE);
        return old.addIdentificationToStatement(stmt, 7);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, idRoweru);
        stmt.setString(insertIndex+1, klientNrDowodu);
        stmt.setLong(insertIndex+2, idPrac);
        stmt.setDate(insertIndex+3, dataRozpoczecia);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifySubskrybcjaController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifySubskrybcjaController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(dataRozpoczecia != null){
            stmt.setDate(i++, dataRozpoczecia);
        }
        if(klientNrDowodu != null){
            stmt.setString(i++, klientNrDowodu);
        }
        if(idPrac > 0){
            stmt.setLong(i++, idPrac);
        }
        if(idRoweru > 0){
            stmt.setLong(i++, idRoweru);
        }
        if(idAkcesorium > 0){
            stmt.setLong(i, idAkcesorium);
        }
        if(dataZakonczenia != null){
            stmt.setDate(i++, dataZakonczenia);
        }
        return stmt;
    }

    public double getCenaZaMiesiac() {
        return cenaZaMiesiac;
    }

    public void setCenaZaMiesiac(double cenaZaMiesiac) {
        this.cenaZaMiesiac = cenaZaMiesiac;
    }
}
