package DoItem;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class InitXML {
	public static void initxml(String filename,String filepath) {
		// TODO Auto-generated method stub
		String path = filepath + filename;
		OutputStream os = null;
		String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + "<root>" + "\n" + "</root> ";
	    try {
	        os = new FileOutputStream(path);
	        OutputStreamWriter write = new OutputStreamWriter(os,"UTF-8");
	        BufferedWriter writer=new BufferedWriter(write);
	        writer.write(text);
	        writer.close();
	        write.close();
	     } catch (IOException e) {
	         e.printStackTrace();
	     } catch (Exception e) {
	         e.printStackTrace();
	     } finally {
	         // 完毕，关闭所有链接
	         try {
	             os.close();
	         } catch (IOException e) {
	        	 e.printStackTrace();
	         }
	     }
	}
}
