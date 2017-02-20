package com.wdpr.cups.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Lock LOCK = new ReentrantLock();
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private FileUtil() {
	}
	public static Date getLastAccessDate(final String fileName) {
		try {
			if (isFileValid(fileName)) {
				final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				final String datStr = sdf.format(new File(fileName).lastModified());
				return sdf.parse(datStr);
			}
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	private static void wiatForLock(final String fileName) {
		final File lockFile = new File(fileName + ".lock");
		if(lockFile.exists()) {
			try {
				do {
					logger.error("Lock file exists : " + fileName + ".lock time : " + System.currentTimeMillis());
					Thread.sleep(1000);
				} while(lockFile.exists());
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isFileValid(final String fileName) {
		wiatForLock(fileName);
		final File file = new File(fileName);
		return (file.exists() && file.isFile());
			
	}
	
	public static void validate(final String fileName) {
		if (!isFileValid(fileName)) {
			throw new IllegalStateException("Invalid file name or file does not exists : " + fileName);
		}
	}
	
	public static boolean copy(final File fromFile, final File toFile, final File tempFile) throws IOException {
		return copy(fromFile.toPath(), toFile.toPath(), tempFile.toPath());
	}
	
	public static boolean copy(final Path source, final Path dest, final Path temp) throws IOException {
		LOCK.lock();
		Files.copy(source, temp, StandardCopyOption.REPLACE_EXISTING);
		logger.error("Copyed from " + source.getFileName() + " to " + temp.getFileName());
		Files.move(temp, dest, StandardCopyOption.REPLACE_EXISTING);
		logger.error("File renamed from " + temp.getFileName() + " to " + dest.getFileName());
		LOCK.unlock();
		return true;
	}
	
	public static boolean copy(final String fromFileName, final String toFileName) throws IOException {
		return copy(new File(fromFileName), new File(toFileName), new File(toFileName + ".lock"));
	}
}
