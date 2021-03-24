package database.objects;

import gui.controllers.modification.ModifyRowerController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Rower extends Checkable implements Statementable<Rower>, Modifiable {
    private long id;
    private String kolor;
    private Date dataZakupu;
    private String model;
    private String status;

    public Rower(long id, String kolor, Date dataZakupu, String model, String status){
        this.id = id;
        this.kolor = kolor;
        this.dataZakupu = dataZakupu;
        this.model = model;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getKolor() {
        return kolor;
    }

    public Date getDataZakupu() {
        return dataZakupu;
    }

    public String getModel() {
        return model;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setString(i++, kolor);
        if(dataZakupu != null) stmt.setDate(i++, dataZakupu);
        stmt.setString(i++, model);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, Rower old) throws SQLException {
        stmt.setLong(1, id);
        stmt.setString(2, kolor);
        stmt.setDate(3, dataZakupu);
        stmt.setString(4, model);
        stmt.setString(5, status);
        return old.addIdentificationToStatement(stmt, 6);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setLong(insertIndex, id);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyRowerController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyRowerController.reserOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(id > 0){
            stmt.setLong(i++, id);
        }
        if(kolor != null){
            stmt.setString(i++, kolor);
        }
        if(dataZakupu != null){
            stmt.setDate(i++, dataZakupu);
        }
        if(model != null){
            stmt.setString(i++, model);
        }
        if(status != null){
            stmt.setString(i, status);
        }
        return stmt;
    }
}
