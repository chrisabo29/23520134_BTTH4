import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

public class ShoesShop extends JFrame {
    private static final long serialVersionUID = 1L;

    private ProductDetailPanel detailPanel;
    private Timer fadeTimer;
    private boolean isFadingOut = false;
    private transient Shoes nextProductToShow = null;

    public ShoesShop() {
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        List<Shoes> products = loadProductsFromDatabase();
        if (products.isEmpty()) {
            System.out.println("Khong co san pham trong database.");
            return;
        }

        detailPanel = new ProductDetailPanel(this::scaleImage);
        detailPanel.updateProduct(products.get(0));

        ProductListPanel productListPanel = new ProductListPanel(
            products,
            this::scaleImage,
            this::startFadeAnimation
        );

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(detailPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        setupFadeAnimation();
    }

    private List<Shoes> loadProductsFromDatabase() {
        List<Shoes> products = new ArrayList<>();
        String sql = "SELECT name, price, brand, description, image_path FROM shoes";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Shoes shoe = new Shoes(
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("brand"),
                    rs.getString("description"),
                    rs.getString("image_path")
                );
                products.add(shoe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    private ImageIcon scaleImage(String path, int width) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage();
                int height = (width * icon.getIconHeight()) / icon.getIconWidth();
                Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(newImg);
            }
        } catch (Exception e) {
        }

        return new ImageIcon();
    }

    private void setupFadeAnimation() {
        fadeTimer = new Timer(15, e -> {
            if (isFadingOut) {
                detailPanel.setAlpha(detailPanel.getAlpha() - 0.1f);
                if (detailPanel.getAlpha() <= 0.0f) {
                    detailPanel.setAlpha(0.0f);
                    isFadingOut = false;
                    detailPanel.updateProduct(nextProductToShow);
                }
            } else {
                detailPanel.setAlpha(detailPanel.getAlpha() + 0.1f);
                if (detailPanel.getAlpha() >= 1.0f) {
                    detailPanel.setAlpha(1.0f);
                    fadeTimer.stop();
                }
            }
        });
    }

    private void startFadeAnimation(Shoes shoes) {
        if (fadeTimer.isRunning()) {
            return;
        }

        nextProductToShow = shoes;
        isFadingOut = true;
        fadeTimer.start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> new ShoesShop().setVisible(true));
    }
}
