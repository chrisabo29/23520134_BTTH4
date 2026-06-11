import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseSeeder {
    private static final String INSERT_SQL = ""
        + "INSERT INTO shoes (name, price, brand, description, image_path) "
        + "VALUES (?, ?, ?, ?, ?)";

    private static final Object[][] SAMPLE_PRODUCTS = {
        {"4DFWD PULSE SHOES", 160.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img1.png"},
        {"FORUM MID SHOES", 100.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img2.png"},
        {"SUPERNOVA SHOES", 150.00, "Adidas", "NMD City Stock 2", "/images/img3.png"},
        {"Adidas", 160.00, "Adidas", "NMD City Stock 2", "/images/img4.png"},
        {"Adidas", 120.00, "Adidas", "NMD City Stock 2", "/images/img5.png"},
        {"4DFWD PULSE SHOES", 160.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img6.png"},
        {"4DFWD PULSE SHOES", 160.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img1.png"},
        {"FORUM MID SHOES", 100.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img2.png"},
        {"SUPERNOVA SHOES", 150.00, "Adidas", "NMD City Stock 2", "/images/img3.png"},
        {"Adidas", 160.00, "Adidas", "NMD City Stock 2", "/images/img4.png"},
        {"Adidas", 120.00, "Adidas", "NMD City Stock 2", "/images/img5.png"},
        {"4DFWD PULSE SHOES", 160.00, "Adidas", "This product is excluded from all promotional discounts and offers.", "/images/img6.png"}
    };

    public static void main(String[] args) {
        try {
            seed();
            System.out.println("Sample data seeded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void seed() throws Exception {
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL)
        ) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM shoes");
            }

            for (Object[] product : SAMPLE_PRODUCTS) {
                ps.setString(1, (String) product[0]);
                ps.setDouble(2, (double) product[1]);
                ps.setString(3, (String) product[2]);
                ps.setString(4, (String) product[3]);
                ps.setString(5, (String) product[4]);
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
}
