import java.sql.*;

public class JDBC
{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/JDBC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ahmedabad52";

    public static void main(String[] args)
    {
        try
        {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            createTable(connection);
            System.out.println("Table created successfully.");

            insertData(connection, "John", 25);
            insertData(connection, "Alice", 30);
            displayData(connection);
            updateData(connection, 1, "Robert", 28);
            displayData(connection);
            deleteData(connection, 2);
            displayData(connection);

            connection.close();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException
    {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" + "id INT AUTO_INCREMENT PRIMARY KEY," + "name VARCHAR(100)," + "age INT)";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createTableSQL);
        statement.close();
    }

    private static void insertData(Connection connection, String name, int age) throws SQLException
    {
        String insertSQL = "INSERT INTO users (name, age) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void displayData(Connection connection) throws SQLException
    {
        String selectSQL = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);
        System.out.println("User ID\t\tName\t\tAge");
        while(resultSet.next())
        {
            System.out.println(resultSet.getInt("id") + "\t\t" + resultSet.getString("name") + "\t\t" + resultSet.getInt("age"));
        }
        resultSet.close();
        statement.close();
    }

    private static void updateData(Connection connection, int id, String name, int age) throws SQLException
    {
        String updateSQL = "UPDATE users SET name = ?, age = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void deleteData(Connection connection, int id) throws SQLException
    {
        String deleteSQL = "DELETE FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}