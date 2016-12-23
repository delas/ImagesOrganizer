package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;

public class VideoMP4 extends Media {

	protected Date creationTime = null;

	protected VideoMP4(File file) {
		super(file);
		
		try {
			IsoFile isoFile = new IsoFile(file.getAbsolutePath());
			MovieBox moov = isoFile.getMovieBox();
			for(Box b : moov.getBoxes()) {
				if (b instanceof MovieHeaderBox) {
					MovieHeaderBox metadata = (MovieHeaderBox) b;
					creationTime = metadata.getCreationTime();
				}
			}
			isoFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Date getShootDate() {
		if (creationTime != null) {
			return creationTime;
		}
		return new Date(file.lastModified());
	}

}
