package database.objects;

import gui.controllers.modification.ModifyModelController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelRoweru extends Checkable implements Statementable<ModelRoweru>, Modifiable {
    private String nazwa;
    private String typ;
    private String rozmiar;
    private double cenaZaDzien;
    private String opis;
    private double cenaZaMiesiac;

    private int wolne = 0;

    public ModelRoweru(String nazwa, String typ, String rozmiar, double cenaZaDzien, String opis, double cenaZaMiesiac){
        this.nazwa = nazwa;
        this.typ = typ;
        this.rozmiar = rozmiar;
        this.cenaZaDzien = cenaZaDzien;
        this.opis = opis;
        this.cenaZaMiesiac = cenaZaMiesiac;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getTyp() {
        return typ;
    }

    public String getRozmiar() {
        return rozmiar;
    }

    public double getCenaZaDzien() {
        return cenaZaDzien;
    }

    public String getOpis() {
        return opis;
    }

    public double getCenaZaMiesiac() {
        return cenaZaMiesiac;
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        stmt.setString(i++, nazwa);
        stmt.setString(i++, typ);
        stmt.setString(i++, rozmiar);
        if (cenaZaDzien>0) stmt.setDouble(i++, cenaZaDzien);
        if(opis!=null) stmt.setString(i++, opis);
        if(cenaZaMiesiac>0) stmt.setDouble(i, cenaZaMiesiac);
        return stmt;
    }

    @Override
    public PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException {
        return addIdentificationToStatement(stmt, 1);
    }

    @Override
    public PreparedStatement prepareModifyStatement(PreparedStatement stmt, ModelRoweru old) throws SQLException {
        stmt.setString(1, nazwa);
        stmt.setString(2, typ);
        stmt.setString(3, rozmiar);
        stmt.setDouble(4, cenaZaDzien);
        stmt.setString(5, opis);
        stmt.setDouble(6, cenaZaMiesiac);
        return old.addIdentificationToStatement(stmt, 7);
    }

    @Override
    public PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException {
        stmt.setString(insertIndex, nazwa);
        return stmt;
    }

    @Override
    public void setOrigin() {
        ModifyModelController.setOrigin(this);
    }

    @Override
    public void resetOrigin() {
        ModifyModelController.resetOrigin();
    }

    @Override
    public PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException {
        int i = 1;
        if(nazwa != null){
            stmt.setString(i++, nazwa);
        }
        if(typ != null){
            stmt.setString(i++, typ);
        }
        if(rozmiar != null){
            stmt.setString(i++, rozmiar);
        }
        if(cenaZaDzien > 0){
            stmt.setDouble(i++, cenaZaDzien);
        }
        if(opis != null){
            stmt.setString(i++, opis);
        }
        if(cenaZaMiesiac > 0){
            stmt.setDouble(i, cenaZaMiesiac);
        }
        return stmt;
    }

    public int getWolne() {
        return wolne;
    }

    public void setWolne(int wolne){
        this.wolne = wolne;
    }

}
