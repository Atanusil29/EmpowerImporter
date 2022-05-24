package com.gantrex.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final String busDocIdRegExp = "^[\\p{L}\\p{M}\\p{N}_\\-\\.\\s]*$";
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static boolean isValidBussinessDocID(String busDocID) {
		Pattern pat = Pattern.compile(busDocIdRegExp);
		return pat.matcher(busDocID).matches();

	}

	public static boolean isCompletelyWritten(File file) {
		RandomAccessFile stream = null;
		try {
			stream = new RandomAccessFile(file, "rw");
			return true;
		} catch (Exception e) {
			log.info("File " + file.getName() + " is not completely written");
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					log.error("Exception during closing file " + file.getName());
				}
			}
		}
		return false;
	}

	public static <T> ArrayList<List<T>> toSublists(List<T> list, int n) {
		ArrayList<List<T>> sublists = new ArrayList<>();
		for (int i = 0; i < list.size(); i += n) {
			List<T> sublist = list.subList(i, Math.min(list.size(), i + n));
			sublists.add(sublist);
		}
		return sublists;
	}

	public static Date dateWithShift(long shift) {
		LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(shift, ChronoUnit.MINUTES));
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static void main(String[] args) {
		// isValidBussinessDocID("WelcomLetter8");
	}
}
