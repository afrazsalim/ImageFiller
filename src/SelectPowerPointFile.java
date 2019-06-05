import java.util.ArrayList;

public class SelectPowerPointFile extends FileReaderClass {
   public SelectPowerPointFile() {
      ArrayList<String> list = new ArrayList();
      list.add("ppt");
      list.add("pptx");
      super.setExtensions(list);
   }
}