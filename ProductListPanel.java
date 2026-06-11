import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductListPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color HOVER_BORDER_COLOR = new Color(0, 120, 215);
    private static final Color SELECTED_BORDER_COLOR = new Color(80, 80, 80);

    private ProductCart selectedCard;

    public ProductListPanel(
        List<Shoes> products,
        BiFunction<String, Integer, ImageIcon> imageScaler,
        Consumer<Shoes> onProductSelected
    ) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(new EmptyBorder(80, 20, 80, 20));

        for (Shoes product : products) {
            ProductCart card = new ProductCart(product, imageScaler.apply(product.getImagePath(), 200));
            card.addMouseListener(createCardMouseListener(card, product, onProductSelected));
            gridPanel.add(card);
        }

        if (gridPanel.getComponentCount() > 0) {
            selectedCard = (ProductCart) gridPanel.getComponent(0);
            selectedCard.setBorderColor(SELECTED_BORDER_COLOR);
            selectedCard.setBorderThickness(2);
        }

        add(gridPanel);
    }

    private MouseAdapter createCardMouseListener(
        ProductCart card,
        Shoes shoes,
        Consumer<Shoes> onProductSelected
    ) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectCard(card);
                onProductSelected.accept(shoes);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (card != selectedCard) {
                    card.setBorderColor(HOVER_BORDER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (card != selectedCard) {
                    card.setBorderColor(null);
                }
            }
        };
    }

    private void selectCard(ProductCart card) {
        if (selectedCard != null) {
            selectedCard.setBorderColor(null);
            selectedCard.setBorderThickness(1);
        }

        selectedCard = card;
        selectedCard.setBorderColor(SELECTED_BORDER_COLOR);
        selectedCard.setBorderThickness(2);
    }
}
