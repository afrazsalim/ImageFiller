import java.util.ArrayList;

public class SelectExcelFilePath extends FileReaderClass {
   public SelectExcelFilePath() {
      ArrayList<String> list = new ArrayList();
      list.add("xlsx");
      list.add("xlx");
      super.setExtensions(list);
   }
}