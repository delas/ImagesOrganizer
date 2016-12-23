package imgorg.bin;

import imgorg.core.Media;

import static java.nio.file.StandardCopyOption.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

public class CreateFolderStructureHere {

	public static void main(String[] args) throws IOException, TransformerException, XPathExpressionException {

		if (args.length != 1) {
			System.err.println("Use: CreateFolderStructureHere PATH_TO_FILES");
			System.exit(1);
		}
		
		Set<Media> imagesList = new HashSet<Media>();
		System.out.println("Parsing files...");
		File base = new File(args[0]);
		for(File file : base.listFiles()) {
			Media i = Media.createFromFile(file);
			if (i != null) {
				imagesList.add(i);
			}
		}
		
		System.out.println("Moving files...");
		for (Media i : imagesList) {
			System.out.println(i);
			File targetPath = i.getTargetPath();
			if (targetPath != null) {
				if (!targetPath.exists()) {
					System.out.println("Creating folder structure...");
					Files.createDirectories(targetPath.toPath());
				}
				
				System.out.println("Moving file...");
				Files.move(i.getFile().toPath(), i.getTargetFile().toPath(), ATOMIC_MOVE);
			}
		}
		
		System.out.println("Done!");
	}

}
