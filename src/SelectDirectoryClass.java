import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JFileChooser;

public class SelectDirectoryClass extends FileReaderClass {
   public void createDialogBox() {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileSelectionMode(1);
      chooser.setAcceptAllFileFilterUsed(false);
      int returnVal = chooser.showOpenDialog((Component)null);
      if (returnVal == 0) {
         super.setSelectedFiel(chooser.getCurrentDirectory());
         this.getListener().stream().forEach((e) -> {
            e.setText(chooser.getCurrentDirectory().toString());
         });
      }

   }
}