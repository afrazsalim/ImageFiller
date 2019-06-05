import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImageFiller {
   private File powerPointFile;
   private File excelFile;
   private File directory;
   private int currentX;
   private int currentY;
   private int width;
   private int height;
   private int counter;
   private int initialY = 125;
   private final int initialX = 15;

   public void startFilling(SelectPowerPointFile file, SelectExcelFilePath path, SelectDirectoryClass directoryFile, TextArea textField) {
      this.setPowerPointFile(file.getSelectedFile());
      this.setExcelFile(path.getSelectedFile());
      this.setDirectory(directoryFile.getSelectedFile());
      if (file.hasReadCorrectFile() && path.hasReadCorrectFile() && directoryFile.hasReadCorrectFile()) {
         JOptionPane.showMessageDialog((Component)null, "Starting Procedure");
         try {
            this.setCurrentX(15);
            this.setCurrentY(125);
            this.setWidth(80);
            this.setHeight(105);
            this.setCounter(0);
            this.read(textField);
         } catch (IOException var5) {
            JOptionPane.showMessageDialog((Component)null, var5.toString() + " Please try again after closing all the programs which are using these file");
         }
      }else {
          JOptionPane.showMessageDialog((Component)null, " There is a missing file. Give the path of all files before starting.");
        }

   }
   
   


   public void read(TextArea textField) throws IOException {
      //int previous = -1;
      double current = 0;
      XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(this.getExcelFile()));
      XSSFSheet sheet = workbook.getSheetAt(0);
      XSSFRow row = sheet.getRow(0);
      Iterator<Row> iterator = sheet.iterator();
      XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(this.getPowerPointFile()));
      List<XSLFSlide> slides = ppt.getSlides();
      XSLFSlide slide = (XSLFSlide)slides.get(0);
      XSLFSlide temp = this.getOriginalFile();
      
      label42:
      while(iterator.hasNext()) {
         Row currentRow = (Row)iterator.next();
         Iterator cellIterator = currentRow.iterator();
         int index = 0;
         while(true) {
       	  XSLFTextBox shape = slide.createTextBox();
       	  XSLFTextBox textBox = slide.createTextBox();
         	textBox.setAnchor(new Rectangle(this.getCurrentX()+(this.getWidth()/4), this.getCurrentY()-50, this.getWidth(), this.getHeight()));
            shape.setAnchor(new Rectangle(this.getCurrentX()+(this.getWidth()/4), this.getCurrentY()+this.getHeight()-5, this.getWidth(), this.getHeight()));
            while(true) {
               if (!cellIterator.hasNext()) {
                  continue label42;
               }
               Cell currentCell = (Cell)cellIterator.next();
               if (currentCell.getCellTypeEnum() == CellType.STRING && this.doesExistFile(currentCell.getStringCellValue()) != null) {
                  File[] pictureData = this.doesExistFile(currentCell.getStringCellValue());
                  byte[] picture = Files.readAllBytes(pictureData[0].toPath());
                  XSLFPictureData pd = ppt.addPicture(picture, PictureType.PNG);
                  XSLFPictureShape pic = slide.createPicture(pd);
                  pic.setAnchor(new Rectangle(this.getCurrentX(), this.getCurrentY(), this.getWidth(), this.getHeight()));
                  this.setCurrentX(this.getCurrentX() + this.getWidth() + 15);
                  this.setCounter(this.getCounter() + 1);
                  this.checkForYCo();
                  textField.append("Filling image :" +currentCell.getStringCellValue() + "\n");
                  if (this.getCounter() == 20) {
                     slide = this.createSlideLayOut(temp, ppt);
                     this.setCounter(0);
                     this.setCurrentX(this.getInitialX());
                     this.setCurrentY(this.getInitialY());
                  }
               } else if (currentCell.getCellTypeEnum() == CellType.STRING && this.doesExistFile(currentCell.getStringCellValue()) == null ) {
                  this.setCurrentX(this.getCurrentX() + this.getWidth() + 15);
                  this.setCounter(this.getCounter() + 1);
                  textField.setForeground(Color.RED);
                  textField.append("Image Not Found " +currentCell.getStringCellValue() + "\n");
                  textField.setForeground(Color.BLUE);
                  this.checkForYCo();
               } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                   current = (double)currentCell.getNumericCellValue();
            	 	XSLFTextParagraph p = shape.addNewTextParagraph();
                    XSLFTextRun r1 = p.addNewTextRun();
                    r1.setText(Double.toString(current));
                    r1.setFontColor(Color.BLUE);
                    r1.setFontSize(15.);
                    
                    System.out.println("Writing ");
                    //
                    XSLFTextParagraph box = textBox.addNewTextParagraph();
                    XSLFTextRun insert = box.addNewTextRun();
                    if(this.getCounter() == 0) 
                       insert.setText("N° " + 20);
                    else
                       insert.setText("N° " + this.getCounter());
                    insert.setFontColor(Color.BLUE);
                    insert.setFontSize(15.);
               }
               index = index +1; //Extra guard.
            }

         }
      }
      textField.append("Finished!");
      FileOutputStream out = new FileOutputStream(this.getPowerPointFile()+"-filled.pptx");
      ppt.write(out);
      out.close();
   }

   private XSLFSlide getOriginalFile() {
      XMLSlideShow pptwice = null;

      try {
         pptwice = new XMLSlideShow(new FileInputStream(this.getPowerPointFile()));
      } catch (FileNotFoundException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      List<XSLFSlide> slideList = pptwice.getSlides();
      return (XSLFSlide)slideList.get(0);
   }

   private XSLFSlide createSlideLayOut(XSLFSlide slide, XMLSlideShow ppt) {
      XSLFSlide newSlide = ppt.createSlide();
      newSlide.setFollowMasterGraphics(false);
      XSLFSlideLayout src_sl = slide.getSlideLayout();
      XSLFSlideMaster src_sm = slide.getSlideMaster();
      XSLFSlideLayout new_sl = newSlide.getSlideLayout();
      XSLFSlideMaster new_sm = newSlide.getSlideMaster();
      new_sl.importContent(src_sl);
      newSlide.importContent(slide);
      return newSlide;
   }

   private void checkForYCo() {
      if (this.getCounter() == 10) {
         this.setCurrentY(this.getInitialY() + 233);
         this.setCurrentX(this.getInitialX());
      }

   }

   private File[] doesExistFile(final String stringCellValue) {
      File dir = this.getDirectory();
      File[] matches = dir.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return name.startsWith(stringCellValue);
         }
      });
      return matches != null && matches.length > 0 ? matches : null;
   }

   public File getPowerPointFile() {
      return this.powerPointFile;
   }

   public void setPowerPointFile(File powerPointFile) {
      this.powerPointFile = powerPointFile;
   }

   public File getDirectory() {
      return this.directory;
   }

   public void setDirectory(File directory) {
      this.directory = directory;
   }

   public File getExcelFile() {
      return this.excelFile;
   }

   public void setExcelFile(File excelFile) {
      this.excelFile = excelFile;
   }

   public int getCurrentX() {
      return this.currentX;
   }

   public void setCurrentX(int currentX) {
      this.currentX = currentX;
   }

   public int getCurrentY() {
      return this.currentY;
   }

   public void setCurrentY(int currentY) {
      this.currentY = currentY;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getCounter() {
      return this.counter;
   }

   public void setCounter(int counter) {
      this.counter = counter;
   }

   public int getInitialX() {
      return 15;
   }

   public int getInitialY() {
      return this.initialY;
   }

   public void setInitialY(int initialY) {
      this.initialY = initialY;
   }
}