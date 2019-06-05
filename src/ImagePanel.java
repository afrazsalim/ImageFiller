import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

class ImagePanel extends JComponent {
   private Image image;

   public ImagePanel(Image image) {
      this.image = image;
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(this.image, 0, 0, this);
   }
}