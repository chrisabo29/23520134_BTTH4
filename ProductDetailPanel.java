import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.BiFunction;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ProductDetailPanel extends FadePanel {
    private static final long serialVersionUID = 1L;

    private final transient BiFunction<String, Integer, ImageIcon> imageScaler;

    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel brandLabel;
    private JTextArea descriptionLabel;

    public ProductDetailPanel(BiFunction<String, Integer, ImageIcon> imageScaler) {
        this.imageScaler = imageScaler;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(40, 40, 40, 0));
        setPreferredSize(new Dimension(400, 500));

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        nameLabel = createLabel(Font.BOLD, 20, new Color(68, 72, 68));
        priceLabel = createLabel(Font.BOLD, 20, new Color(68, 72, 68));
        brandLabel = createLabel(Font.PLAIN, 14, new Color(68, 72, 68));

        descriptionLabel = new JTextArea();
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descriptionLabel.setForeground(new Color(180, 180, 180));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setEditable(false);
        descriptionLabel.setFocusable(false);
        descriptionLabel.setOpaque(false);
        descriptionLabel.setMaximumSize(new Dimension(360, 1000));

        add(imageLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(nameLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(priceLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(brandLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(descriptionLabel);
    }

    private JLabel createLabel(int style, int size, Color color) {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public void updateProduct(Shoes shoes) {
        imageLabel.setIcon(imageScaler.apply(shoes.getImagePath(), 350));
        nameLabel.setText(shoes.getName());
        priceLabel.setText("$" + shoes.getPrice() + "0");
        brandLabel.setText(shoes.getBrand());
        descriptionLabel.setText(shoes.getDescription());
    }
}
