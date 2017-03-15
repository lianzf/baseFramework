/**
 * 
 */
package com.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author suntao
 * 
 */
public class FileUtil {

	/**
	 * 获取视频存储的路径文件夹
	 * 
	 * @param File:
	 *            要拷贝的源文件
	 * @param id:
	 *            要拷贝的源文件在表中的id
	 * @param String:
	 *            要改变的名称
	 * @throws Exception
	 * @return 存储的相对位置和名称
	 */
	public static String getVideoSavePath(int videoId) throws Exception {
		if (videoId < 1) {
			throw new Exception("参数异常错误");
		}

		String saveDir = String.valueOf(videoId / 1000);
		return saveDir;
	}

	/**
	 * 
	 * @param systemPath
	 *            系统配置路径
	 * @return
	 * @throws Exception
	 */
	public static String createFilePath(String systemPath) throws Exception {
		if (StringUtils.isBlank(systemPath)) {
			throw new Exception("未配置系统路径");
		}
		String extPrefix = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
		File file = new File(systemPath + File.separator + extPrefix);

		if (file != null && (!file.exists())) {
			file.mkdirs();
		}
		return extPrefix;
	}

	/**
	 * 方法描述: 获取文件夹下的所有文件（不含文件夹及子文件夹）
	 * 
	 * @param filepath
	 * @return 文件名称列表；
	 * @throws 当路径指向一个文件而非文件夹时，抛出Exception
	 *             作者：suntao 日期：2015 2015年8月3日
	 */
	static public List<String> getfileList(String filepath) throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		try {
			File file = new File(filepath);

			if (!file.isDirectory()) {
				throw new Exception(filepath + "!");
			} else {
				String[] filelist = file.list();

				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + File.separator + filelist[i]);
					if (!readfile.isDirectory()) {
						fileNameList.add(readfile.getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取文件夹文件列表失败：getfileList", e);
		}
		return fileNameList;
	}

	/**
	 * 根据指定的目录和后缀判断该文件夹下存在多少个以制定后缀结尾的文件
	 * 
	 * @param path
	 *            文件目录
	 * @param reg
	 *            后缀
	 * @return List 返回以制定reg结尾的文件list
	 */
	public static List<String> findEndPosFile(String path, String reg) {
		List<String> endPosFile = new ArrayList<String>();
		Pattern pat = Pattern.compile(reg);
		File file = new File(path);
		File[] arr = file.listFiles();
		for (int i = 0; i < arr.length; i++) {
			// 判断是否是文件夹，如果是的话，再调用一下find方法
			if (arr[i].isDirectory()) {
				findEndPosFile(arr[i].getAbsolutePath(), reg);
			}
			Matcher mat = pat.matcher(arr[i].getAbsolutePath());
			// 根据正则表达式，寻找匹配的文件
			if (mat.matches()) {
				// 这个getAbsolutePath()方法返回一个String的文件绝对路径
				endPosFile.add(arr[i].getAbsolutePath());
			}
		}
		return endPosFile;
	}

	/**
	 * 根据指定的目录创建一个文件夹
	 * 
	 * @param folderPath
	 *            String
	 * @return boolean
	 */
	static public boolean newFolder(String folderPath) throws Exception {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("创建文件夹失败", e);
		}
	}

	/**
	 * 拷贝一个文件到新位置
	 * 
	 * @param oldPath
	 *            String
	 * @param newPath
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	static public boolean copyFile(String oldPath, String newPath) throws Exception {

		int byteread = 0;
		File oldfile = new File(oldPath);
		InputStream inStream = new FileInputStream(oldPath);
		FileOutputStream fs = new FileOutputStream(newPath);

		try {
			if (oldfile.exists()) {
				byte[] buffer = new byte[2048];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}

				inStream.close();
				fs.flush();
				fs.close();
				return true;
			}
		} catch (Exception e) {
			throw new Exception("");
		} finally {
			if (inStream != null) {
				inStream.close();
			}

			if (fs != null) {
				fs.flush();
				fs.close();
			}
		}
		return false;
	}

	/**
	 * 移动一个文件（非文件夹）到新位置
	 * 
	 * @param oldPath
	 *            String
	 * @param newPath
	 *            String
	 * @throws Exception
	 */
	static public void moveFile(String oldPath, String newPath) throws Exception {
		copyFile(oldPath, newPath);
		deleteFile(oldPath);
	}

	/**
	 * 删除指定的文件或文件夹
	 * 
	 * @param filePathAndName
	 *            String
	 * @param fileContent
	 *            String
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean deleteFolderOrFile(String sPath) {
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return false;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(file);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(file);
			}
		}
	}

	/**
	 * 
	 * deleteOldDateFile 方法描述: 达到最大文件数，删除最早的文件 逻辑描述:
	 * 
	 * @param sPath
	 * @return
	 * @since Ver 1.00
	 */
	public static boolean deleteOldDateFile(String sPath, int maxNum) {
		try {

			List<String> files = getfileList(sPath);
			Collections.sort(files);
			if (files != null && files.size() >= maxNum) {
				return deleteDirectory(files.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 返回该目录的路径（如果该目录不存在，则新建目录，并返回目录路径）
	 * </p>
	 * 
	 * @param directoryName
	 * @return
	 * @throws IOException
	 */
	public static String getDirctoryName(File directoryName) throws IOException {
		if (!directoryName.isDirectory()) {
			FileUtils.forceMkdir(directoryName);
		}

		return directoryName.getAbsolutePath();
	}

	/**
	 * 删除单个文件
	 * 
	 * @param file
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	private static boolean deleteFile(String path) {
		File file = new File(path);
		return deleteFile(file);
	}

	/**
	 * 删除单个文件
	 * 
	 * @param file
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	private static boolean deleteFile(File file) {
		// 路径为文件且不为空则进行删除
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	private static boolean deleteDirectory(String path) {
		File folder = new File(path);
		return deleteDirectory(folder);
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	private static boolean deleteDirectory(File folder) {
		if (!folder.exists() || !folder.isDirectory()) {
			return false;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = folder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {// 删除子文件
				deleteFile(files[i].getAbsolutePath());
			} else {// 删除子目录
				deleteDirectory(files[i].getAbsolutePath());
			}
		}
		return folder.delete();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		List<String> list = getfileList("/usr/local/tomcat/test");
		Collections.sort(list);
		for (String str : list) {
			System.out.println(str);
		}

	}
}
