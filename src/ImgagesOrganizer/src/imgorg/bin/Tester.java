package imgorg.bin;

import imgorg.core.Media;

import java.io.File;

public class Tester {

	public static void main(String[] args) {
//		File file = new File("C:\\Users\\Andrea\\Desktop\\cell2\\IMAG0275.jpg");
//		File file = new File("C:\\Users\\Andrea\\Desktop\\foto\\VID_20140522_182413831.mp4");
		File file = new File("C:\\Users\\Andrea\\Desktop\\foto\\VID_20160120_131715.3gp");
		Media i = Media.createFromFile(file);
		File targetPath = i.getTargetPath();
		System.out.println(targetPath);
	}

}
