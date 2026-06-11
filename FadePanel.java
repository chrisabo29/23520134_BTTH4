import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

// Lop JPanel tuy chinh de ho tro hieu ung fade.
public class FadePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private float alpha = 1.0f;

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float value) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, value));
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2d);
        g2d.dispose();
    }
}
