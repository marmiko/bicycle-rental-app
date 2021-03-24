package database.objects;

import gui.controllers.modification.ModifyRodzajController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RodzajAkcesorium extends Checkable implements Statementable<RodzajAkcesorium>, Modifiable {
    private String nazwa;
    private double cenaZaDzien;
    private double cenaZaMiesiac;
    private double kaucja;

    private int wolne;

    public RodzajAkcesorium(String nazwa, double cenaZaDzien, double cenaZaMiesiac, double kaucja){
        this.nazwa = nazwa;
        this.cenaZaDzien = cenaZaDzien;
        this.cenaZaMiesiac = cenaZaMiesiac;
        this.kaucja = kaucja;
    }

    public String getNazwa() {
        return nazwa;
    }

    public double getCenaZaDzien() {
        return cenaZaDzien;
    }

    public double getCenaZaMiesiac() {
        return cenaZaMiesiac;
    }

    public double getKaucja() {
        return kaucja;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setString(i++, nazwa);
        if(cenaZaMiesiac > 0) stmt.setDouble(i++, cenaZaDzien);
        if(cenaZaMiesiac > 0) stmt.setDouble(i++, cenaZaMiesiac);
        if(kaucja > 0) stmt.setDouble(i, kaucja);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, RodzajAkcesorium old) throws SQLException {
        stmt.setString(1, nazwa);
        stmt.setDouble(2, cenaZaDzien);
        stmt.setDouble(3, cenaZaMiesiac);
        stmt.setDouble(4, kaucja);
        return old.addIdentificationToStatement(stmt, 5);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setString(insertIndex, nazwa);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyRodzajController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyRodzajController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(nazwa != null){
            stmt.setString(i++, nazwa);
        }
        if(cenaZaDzien > 0){
            stmt.setDouble(i++, cenaZaDzien);
        }
        if(cenaZaMiesiac > 0){
            stmt.setDouble(i++, cenaZaMiesiac);
        }
        if(kaucja > 0){
            stmt.setDouble(i, kaucja);
        }
        return stmt;
    }

    public int getWolne() {
        return wolne;
    }

    public void setWolne(int wolne) {
        this.wolne = wolne;
    }
}
