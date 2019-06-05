
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;

public class UserInterface {
   private JFrame currentFrame;

   public UserInterface(int width, int height) {
      this.setCurrentFrame(this.createFrame(width, height));
      JPanel panel = new JPanel();
      GroupLayout layout = new GroupLayout(panel);
      panel.setLayout(layout);
      JButton ExlButton = this.addButtons(this.getCurrentFrame(), "Select Excel File");
      ExlButton.setBackground(Color.BLUE);
      ExlButton.setBounds(panel.getX() + 950, panel.getY() + 100, 200, 70);
      this.addlabel("Excel Selector", panel, ExlButton.getX(), ExlButton.getY() - (ExlButton.getHeight() / 2 + ExlButton.getHeight() / 4), ExlButton.getWidth(), 50);
      this.createlayOutOfButton(ExlButton);
      panel.add(ExlButton);
      JButton selectLayOutButton = this.addButtons(this.getCurrentFrame(), "Powerpoint Layout");
      selectLayOutButton.setBackground(Color.GREEN);
      selectLayOutButton.setBounds(ExlButton.getX(), ExlButton.getY() + ExlButton.getHeight() + 100, 200, 70);
      this.addlabel("PowerPoint selector", panel, ExlButton.getX(), ExlButton.getY() + 2 * ExlButton.getHeight() - 20, ExlButton.getWidth(), 50);
      JTextField powerPoint = new JTextField();
      powerPoint.setBounds(panel.getX() + 50, selectLayOutButton.getY() + 10, 850, 50);
      powerPoint.setEditable(false);
      powerPoint.setBackground(Color.WHITE);
      powerPoint.setFont(new Font("Arial", 1, 30));
      SelectPowerPointFile file = new SelectPowerPointFile();
      this.addFunctionToLayOutButton(selectLayOutButton, powerPoint, file);
      this.addlabel("Currently selected LayOut:", panel, powerPoint.getX(), powerPoint.getY() - 50, powerPoint.getWidth() / 2, 50);
      panel.add(powerPoint);
      JTextField field = new JTextField();
      field.setBounds(panel.getX() + 50, ExlButton.getY() + 10, 850, 50);
      field.setEditable(false);
      field.setBackground(Color.WHITE);
      field.setFont(new Font("Arial", 1, 30));
      SelectExcelFilePath path = new SelectExcelFilePath();
      this.addFunctionToExlButton(ExlButton, field, path);
      JButton folderButton = this.addButtons(this.getCurrentFrame(), "Image folder");
      folderButton.setBackground(Color.cyan);
      folderButton.setBounds(selectLayOutButton.getX(), selectLayOutButton.getY() + selectLayOutButton.getHeight() + 100, 200, 70);
      JTextField folderField = new JTextField();
      folderField.setBounds(panel.getX() + 50, folderButton.getY() + 10, 850, 50);
      folderField.setEditable(false);
      folderField.setBackground(Color.WHITE);
      folderField.setFont(new Font("Arial", 1, 30));
      this.addlabel("Folder which contains images:", panel, powerPoint.getX(), folderField.getY() - 50, folderField.getWidth() / 2, 50);
      SelectDirectoryClass DirectoryFile = new SelectDirectoryClass();
      this.addFunctionalityToFolderButton(folderButton, folderField, DirectoryFile);
      JButton startButton = this.addButtons(this.getCurrentFrame(), "Start filling slide");
      startButton.setBackground(Color.cyan);
      startButton.setBounds(folderButton.getX() - 800, folderButton.getY()+100, 200, 70);
      panel.add(startButton);
      panel.add(folderField);
      panel.add(folderButton);
      panel.add(field);
      panel.add(selectLayOutButton);
      
      this.addlabel("Currently selected file:", panel, field.getX(), field.getY() - 50, field.getWidth() / 2, 50);
      TextArea textField = new TextArea();
      textField.setBounds(startButton.getX()+startButton.getWidth()+200,folderButton.getY()+100,600,startButton.getHeight()*3);         
      textField.setBackground(Color.BLACK);
      textField.setFont(new Font("TimesRoman", Font.BOLD, 15));
      textField.setForeground(Color.BLUE);
      textField.setEditable(false);
      this.addFunctionalityToStartButton(startButton, file, path, DirectoryFile,textField);
      panel.add(textField);

      this.getCurrentFrame().add(panel);
      this.getCurrentFrame().setBackground(Color.MAGENTA);
      this.getCurrentFrame().setResizable(false);
      this.getCurrentFrame().setDefaultCloseOperation(3);
      
   }

   private void addFunctionalityToStartButton(JButton startButton, final SelectPowerPointFile file, final SelectExcelFilePath path, final SelectDirectoryClass directoryFile,TextArea textField) {
      startButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            ImageFiller filler = new ImageFiller();
            filler.startFilling(file, path, directoryFile,textField);
         }
      });
   }

   private void addFunctionalityToFolderButton(JButton folderButton, final JTextField folderField, final SelectDirectoryClass file) {
      folderButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            file.addListener(folderField);
            file.createDialogBox();
         }
      });
   }

   private void addFunctionToLayOutButton(JButton layOutButton, final JTextField powerPoint, final SelectPowerPointFile file) {
      layOutButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            file.addListener(powerPoint);
            file.createDialogBox();
         }
      });
   }

   private void addFunctionToExlButton(JButton exlButton, final JTextField field, final SelectExcelFilePath path) {
      exlButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            path.addListener(field);
            path.createDialogBox();
         }
      });
   }

   private void addlabel(String string, JPanel panel, int x, int y, int width, int height) {
      JLabel label = new JLabel();
      label.setText(string);
      label.setBounds(x, y, width, height);
      label.setFont(new Font("Arial", 2, 20));
      panel.add(label);
   }

   private void createlayOutOfButton(JButton ExlButton) {
      ExlButton.setVisible(true);
      ExlButton.setFocusPainted(false);
   }

   private JButton addButtons(JFrame frame, String text) {
      JButton selectExFileButton = new JButton();
      selectExFileButton.setText(text);
      selectExFileButton.setFont(new Font("Arial", 0, 20));
      return selectExFileButton;
   }

   private JFrame createFrame(int width, int height) {
      JFrame frame = new JFrame();
      frame.setBounds(10, 10, width, height);
      return frame;
   }

   public JFrame getCurrentFrame() {
      return this.currentFrame;
   }

   public void setCurrentFrame(JFrame currentFrame) {
      this.currentFrame = currentFrame;
   }
}