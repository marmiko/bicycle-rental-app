package database.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * interface implemented by classes representing database objects
 * provides functionality for update statements management
 */
public interface Statementable<T> {
    PreparedStatement prepareInsertStatement(PreparedStatement stmt) throws SQLException;

    PreparedStatement prepareIdentifiedStatement(PreparedStatement stmt) throws SQLException;

    PreparedStatement prepareModifyStatement(PreparedStatement stmt, T t) throws SQLException;

    PreparedStatement addIdentificationToStatement(PreparedStatement stmt, int insertIndex) throws SQLException;

    PreparedStatement prepareSearchStatement(PreparedStatement stmt) throws SQLException;
}
