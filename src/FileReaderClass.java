import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public abstract class FileReaderClass {
   private ArrayList<JTextField> listener;
   private File selectedFile;
   private ArrayList<String> extensions;

   public FileReaderClass() {
      this.setListener(new ArrayList());
      this.setExtensions(new ArrayList());
   }

   public boolean hasReadCorrectFile() {
      return this.getSelectedFile() != null && this.getSelectedFile().getName().length() > 0;
   }

   public void addListener(JTextField field) {
      this.getListener().add(field);
   }

   protected File getSelectedFile() {
      return this.selectedFile;
   }

   protected void setSelectedFiel(File selectedFile) {
      this.selectedFile = selectedFile;
   }

   public ArrayList<JTextField> getListener() {
      return this.listener;
   }

   public void setListener(ArrayList<JTextField> listener) {
      this.listener = listener;
   }

   public void createDialogBox() {
      JFileChooser chooser = new JFileChooser();
      int returnVal = chooser.showOpenDialog((Component)null);
      if (returnVal == 0) {
         String extension = "";
         int i = chooser.getSelectedFile().getName().lastIndexOf(46);
         if (i > 0) {
            extension = chooser.getSelectedFile().getName().substring(i + 1);
         }

         System.out.println("extension is " + extension);
         if (!this.isValidExtension(extension)) {
            this.showDialogBox();
         } else {
            this.setSelectedFiel(chooser.getSelectedFile());
            this.getListener().stream().forEach((e) -> {
               e.setText(this.getSelectedFile().getAbsolutePath());
            });
         }
      }

   }

   private void showDialogBox() {
      StringBuilder builder = new StringBuilder();
      this.getExtensions().stream().forEach((e) -> {
         builder.append(e + " , ");
      });
      String temp = builder.toString();
      builder.append('.');
      JOptionPane.showMessageDialog((Component)null, "Selected file is not a valid file: Valid Formats are " + builder.toString());
      JOptionPane.showMessageDialog((Component)null, "You are being redirected to chose the file with these formats " + temp + " again.");
      this.createDialogBox();
   }

   private boolean isValidExtension(String extension) {
      return this.getExtensions().contains(extension);
   }

   public ArrayList<String> getExtensions() {
      return this.extensions;
   }

   public void setExtensions(ArrayList<String> extensions) {
      this.extensions = extensions;
   }
}