package accounts;
import java.sql.PreparedStatement;

/**
 *
 * @author Krish Khatri
 */

public abstract class Account {
    String first_name;
    String last_name;
    String user_name;
    String password;
    String id;
    PreparedStatement prepared_statement;

    abstract boolean InDatabase();
}
