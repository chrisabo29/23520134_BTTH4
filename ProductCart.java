import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductCart extends JPanel {
    private static final long serialVersionUID = 1L;

    private Color borderColor = null;
    private int borderThickness = 1;

    public ProductCart(Shoes shoes, ImageIcon productIcon) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(15, 10, 15, 10));
        setPreferredSize(new Dimension(250, 300));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel name = createLabel(shoes.getName(), Font.BOLD, 16, new Color(68, 72, 68));
        name.setMaximumSize(new Dimension(Integer.MAX_VALUE, name.getPreferredSize().height));

        JLabel description = createLabel(shoes.getDescription(), Font.BOLD, 14, new Color(180, 180, 180));
        description.setMaximumSize(new Dimension(Integer.MAX_VALUE, description.getPreferredSize().height));

        JLabel productImg = new JLabel(productIcon);
        productImg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imgWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imgWrapper.setOpaque(false);
        imgWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        imgWrapper.add(productImg);

        JLabel brand = createLabel(shoes.getBrand(), Font.PLAIN, 14, new Color(68, 72, 68));
        brand.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JLabel price = createLabel("$" + shoes.getPrice() + "0", Font.BOLD, 16, new Color(68, 72, 68));
        price.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(new Color(244, 244, 244));
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(brand);
        row.add(Box.createHorizontalGlue());
        row.add(price);

        add(name);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(description);
        add(imgWrapper);
        add(Box.createVerticalGlue());
        add(row);
    }

    private JLabel createLabel(String text, int style, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(244, 244, 244));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderThickness));
            int offset = borderThickness / 2;
            g2d.drawRoundRect(offset, offset, getWidth() - borderThickness, getHeight() - borderThickness, 30, 30);
        }

        g2d.dispose();
    }
}
