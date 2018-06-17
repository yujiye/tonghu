package com.tonghu.pub.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Description: 操作目录工具类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午11:00:18
 */
public class FolderUtils {

	public static Logger logger = LoggerFactory.getLogger(FolderUtils.class.getName());
	
	/**
	 * @Description: 根据指定的路径创建目录，指定的路径必须是绝对路径
	 * @param folderPhysicalPath
	 * @return void
	 * @author liangyongjian
	 * @date 2017年9月28日 下午11:06:33
	 * @version V1.0
	 */
	public static void createFolder(String folderPhysicalPath) {
		File folder = new File(folderPhysicalPath);
		try {
			if (!folder.exists()) {
				if (folder.mkdirs())
					logger.debug("目录：" + folderPhysicalPath + "不存在，创建之！");
				else
					logger.error("目录：" + folderPhysicalPath + "不存在，创建之！但创建出错！");
			} else {
				logger.debug("目录：" + folderPhysicalPath + "已存在，不必创建！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据指定的路径创建目录的操作失败，需要创建的目录：" + folderPhysicalPath);
		}
	}
	
	/**
	 * @Description: 根据指定的文件路径获取到该文件所在的目录，指定的文件路径应该是绝对路径
	 * @param filePhysicalPath
	 * @return String
	 * @author liangyongjian
	 * @date 2017年9月28日 下午11:06:47
	 * @version V1.0
	 */
	public static String getFolderPhysicalPath(String filePhysicalPath) {
		filePhysicalPath = filePhysicalPath.trim();
		if (filePhysicalPath.equals(""))
			return "";
		if (filePhysicalPath.lastIndexOf(File.separator) < 0)
			return File.separator;
		String folderPhysicalPath = filePhysicalPath.substring(0, filePhysicalPath.lastIndexOf(File.separator));
		if (folderPhysicalPath.equals(""))
			return File.separator;
		else
			return folderPhysicalPath;
	}
	
	public static void main(String[] args) {
		String path = "D:\\work\\eclipse\\merchant-images\\12.txt";
		FolderUtils.createFolder(FolderUtils.getFolderPhysicalPath(path));
	}
}
