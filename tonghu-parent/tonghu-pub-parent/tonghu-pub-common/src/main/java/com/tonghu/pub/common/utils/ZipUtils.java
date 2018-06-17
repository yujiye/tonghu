package com.tonghu.pub.common.utils;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @Description: ZIP文件处理工具类
 * @author liangyongjian
 * @Version V1.0
 * @date 2017年9月28日 下午10:52:38
 */
public class ZipUtils {

	 /**
	 * @Description: 压缩文件
	 * @param srcfile
	 * @param zipfile
	 * @return void
	 * @author liangyongjian
	 * @date 2017年9月28日 下午10:53:51
	 * @version V1.0
	 */
	public static void ZipFiles(File[] srcfile, File zipfile) {
       byte[] buf = new byte[1024];
       try {
           ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
           for (int i = 0; i < srcfile.length; i++) {
               FileInputStream in = new FileInputStream(srcfile[i]);
               out.putNextEntry(new ZipEntry(srcfile[i].getName()));
               int len;
               while ((len = in.read(buf)) > 0) {
                   out.write(buf, 0, len);
               }
               out.closeEntry();
               in.close();
           }
           out.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

	/**
	 * @Description: zip解压缩
	 * @param zipfile
	 * @param descDir
	 * @return void
	 * @author liangyongjian
	 * @date 2017年9月28日 下午10:54:32
	 * @version V1.0
	 */
	public static void unZipFiles(File zipfile, String descDir) {
		ZipFile zf = null;
       try {
    	   zf = new ZipFile(zipfile);
           for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {
               ZipEntry entry = ((ZipEntry) entries.nextElement());
               String zipEntryName = entry.getName();
               InputStream in = zf.getInputStream(entry);
               OutputStream out = new FileOutputStream(descDir + zipEntryName);
               byte[] buf1 = new byte[1024];
               int len;
               while ((len = in.read(buf1)) > 0) {
                   out.write(buf1, 0, len);
               }
               in.close();
               out.close();
           }
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
    	   if (zf != null) {
    		   try {
    			   zf.close();
    		   } catch(Exception e) {
    			   e.printStackTrace();
    		   }
    	   }
       }
   }
	
	/**
	 * @Description: 根据原始rar路径，解压到指定文件夹下.
	 * @param srcRarPath
	 * @param dstDirectoryPath
	 * @return void
	 * @author liangyongjian
	 * @date 2017年9月28日 下午10:56:18
	 * @version V1.0
	 */
	public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			return;
		}
		File dstDiretory = new File(dstDirectoryPath);
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive a = null;
		try {
			a = new Archive(new File(srcRarPath));
			if (a != null) {
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {
					if (fh.isDirectory()) { // 文件夹
						File fol = new File(dstDirectoryPath + File.separator + fh.getFileNameString());
						fol.mkdirs();
					} else { // 文件
						File out = new File(dstDirectoryPath + File.separator + fh.getFileNameString().trim());
						try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);
							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}
				a.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
