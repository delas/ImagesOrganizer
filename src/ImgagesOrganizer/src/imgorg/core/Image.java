package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.Sanselan;

public abstract class Image {

	protected File file;
	
	public static Image createFromFile(File file) {
		if (Sanselan.hasImageFileExtension(file)) {
			return new JpegImage(file);
		} else {
			try {
				Image i = new RawImage(file);
				return i;
			} catch (IOException e) {
				return null;
			}
		}
	}
	
	protected Image(File file) {
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
