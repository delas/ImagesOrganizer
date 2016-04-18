package imgorg.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;

public class JpegImage extends Image {

	protected JpegImageMetadata metadata;
	
	protected JpegImage(File file) {
		super(file);
		
		if (Sanselan.hasImageFileExtension(file)) {
			try {
				this.file = file;
				IImageMetadata metadata = Sanselan.getMetadata(file);
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
