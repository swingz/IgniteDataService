import cn.swingz.entities.T1;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.lang.IgniteCallable;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IgniteTest {

    @Test
    public void createTable() throws Exception{
        // Register JDBC driver.
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
        // Open JDBC connection.
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://localhost/");
        // Create database tables.
        try (Statement stmt = conn.createStatement()) {
            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");
            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        }
        conn.close();
    }

    @Test
    public void insert() throws Exception{
        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
// Open JDBC connection
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
// Populate City table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "Forest Hill");
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Denver");
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "St. Petersburg");
            stmt.executeUpdate();
        }
// Populate Person table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "John Doe");
            stmt.setLong(3, 3L);
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Jane Roe");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "Mary Major");
            stmt.setLong(3, 1L);
            stmt.executeUpdate();
            stmt.setLong(1, 4L);
            stmt.setString(2, "Richard Miles");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
        }
        conn.close();
    }

    @Test
    public void query() throws Exception{
        // Register JDBC driver
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
// Open JDBC connection
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
// Get data
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs =
                         stmt.executeQuery("SELECT p.name, c.name " +
                                 " FROM Person p, City c " +
                                 " WHERE p.city_id = c.id")) {
                while (rs.next())
                    System.out.println(rs.getString(1) + ", " + rs.getString(2));
            }
        }
        conn.close();
    }

    @Test
    public void caculate(){
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
            // Iterate through all the words in the sentence and create Callable jobs.
            for (final String word : "Count characters using callable".split(" "))
                calls.add(word::length);
            // Execute collection of Callables on the grid.
            Collection<Integer> res = ignite.compute().call(calls);
            // Add up all the results.
            int sum = res.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total number of characters is '" + sum + "'.");
        }
    }

    @Test
    public void write(){
        try (Ignite ignite = Ignition.start("ignite-service-config.xml")) {
            IgniteCache<Integer, T1> t1cache = ignite.getOrCreateCache("t1Cache");
            t1cache.loadCache(null);
            T1 t1 = new T1(10,"年少的");
            t1cache.put(t1.getId(),t1);
            System.out.println("ok");
        }
    }

    @Test
    public void remove(){
        try (Ignite ignite = Ignition.start("ignite-service-config.xml")) {
            IgniteCache<Integer, T1> t1cache = ignite.getOrCreateCache("t1Cache");
            t1cache.remove(10);
        }
    }

    @Test
    public void removet3(){
        try (Ignite ignite = Ignition.start("ignite-service-config.xml")) {
            IgniteCache<Integer, T1> t1cache = ignite.getOrCreateCache("t3Cache");
            t1cache.remove(2);
        }
    }

    @Test
    public void get(){
        try (Ignite ignite = Ignition.start("ignite-service-config.xml")) {
            IgniteCache<Integer, T1> t1cache = ignite.getOrCreateCache("t1Cache");
            SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select * from t1");
            QueryCursor<List<?>> t1cursor = t1cache.query(sqlFieldsQuery);
            System.out.println(t1cursor.getAll());
        }
    }

    @Test
    public void gett3(){
        try (Ignite ignite = Ignition.start("ignite-service-config.xml")) {
            IgniteCache<Integer, T1> t3cache = ignite.getOrCreateCache("t3Cache");
            SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery("select * from t3");
            QueryCursor<List<?>> t3cursor = t3cache.query(sqlFieldsQuery);
            System.out.println(t3cursor.getAll());
        }
    }

}
