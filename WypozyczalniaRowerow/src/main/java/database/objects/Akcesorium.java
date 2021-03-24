package database.objects;

import gui.controllers.modification.ModifyAkcesoriumController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Akcesorium extends Checkable implements Statementable<Akcesorium>, Modifiable {
    private long id;
    private Date dataZakupu;
    private String rodzaj;
    private String status;

    public Akcesorium(long id, Date dataZakupu, String rodzaj, String status){
        this.id = id;
        this.dataZakupu = dataZakupu;
        this.rodzaj = rodzaj;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Date getDataZakupu() {
        return dataZakupu;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(dataZakupu != null) stmt.setDate(i++, dataZakupu);
        stmt.setString(i, rodzaj);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Akcesorium old) throws SQLException {
        stmt.setLong(1, id);
        stmt.setDate(2, dataZakupu);
        stmt.setString(3, rodzaj);
        stmt.setString(4, status);
        return old.addIdentificationToStatement(stmt, 5);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, id);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyAkcesoriumController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyAkcesoriumController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(id>0){
            stmt.setLong(i++, id);
        }
        if(dataZakupu != null){
            stmt.setDate(i++, dataZakupu);
        }
        if(rodzaj != null) {
            stmt.setString(i++, rodzaj);
        }
        if(status != null){
            stmt.setString(i, status);
        }
        return stmt;
    }
}
