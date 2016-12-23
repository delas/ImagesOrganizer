package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.io.FilenameUtils;

public abstract class Media {

	protected File file;
	
	public static Media createFromFile(File file) {
		String extension = FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase();
		if (Imaging.hasImageFileExtension(file)) {
			return new ImageJpeg(file);
		} else if (extension.equals("mp4")) {
			return new VideoMP4(file);
		} else if (extension.equals("3gp")) {
			return new Video3GP(file);
		} else {
			try {
				Media i = new ImageRaw(file);
				return i;
			} catch (IOException e) {
				return null;
			}
		}
	}
	
	protected Media(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public File getTargetFile() {
		File path = getTargetPath();
		if (path != null) {
			String filename = FilenameUtils.getName(file.getAbsolutePath());
			return new File(path.getAbsolutePath() + File.separator + filename);
		}
		return null;
	}
	
	public File getTargetPath() {
		Date date = getShootDate();
		String path = FilenameUtils.getFullPath(file.getAbsolutePath());
		if (date != null) {
			return new File(
					path + File.separator +
					new SimpleDateFormat("yyyy" + File.separator + "yyyy-MM" + File.separator + "yyyy-MM-dd").format(date) + File.separator);
		}
		return null;
	}
	
	public abstract Date getShootDate();
	
	public String toString() {
		return file.getName();
	}
}
