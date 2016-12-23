package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

public class ImageJpeg extends Media {

	protected JpegImageMetadata metadata = null;
	
	protected ImageJpeg(File file) {
		super(file);
		
		if (Imaging.hasImageFileExtension(file)) {
			try {
				this.file = file;
				ImageMetadata metadata = Imaging.getMetadata(file);
				if (metadata instanceof JpegImageMetadata) {
					this.metadata = (JpegImageMetadata) metadata;
				}
			} catch (ImageReadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Date getShootDate() {
		if (metadata != null) {
			try {
				TiffField dateTime = metadata.findEXIFValue(TiffTagConstants.TIFF_TAG_DATE_TIME);
				if (dateTime == null) {
					dateTime = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
				}
				return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").parse(dateTime.getStringValue());
			} catch (ImageReadException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return new Date(file.lastModified());
	}

}
