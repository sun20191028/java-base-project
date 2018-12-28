package zip.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class ZipUtil {
	private static final int BUFFER_SIZE = 2 * 1024;
	
	/**
	 * 压缩成ZIP 方法 1 适合压缩整个文件和文件夹中的数据
	 * @param srcDir  压缩文件夹路径
	 * @param out  压缩文件输出流
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 				true:保留目录结构;
	 *            	false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out,
			boolean KeepDirStructure) throws RuntimeException {
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			close(zos);
			close(out);
		}
	}

	/**
	 * 把所有的 文件压缩到一个压缩文件中.
	 * 压缩成ZIP 方法2 ,
	 * @param srcFiles  需要压缩的文件列表
	 * @param out  压缩文件输出流
	 * @throws RuntimeException  压缩失败会抛出运行时异常
	 */
	public static void toZip(List<File> srcFiles, OutputStream out)
			throws RuntimeException {
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				int len = 0;
				FileInputStream in = null;
				try{
					in = new FileInputStream(srcFile);
					zos.putNextEntry(new ZipEntry(srcFile.getName()));
					while ((len = in.read(buf)) != -1) {
						zos.write(buf, 0, len);
					}
				}finally{
					zos.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			close(zos);
		}
	}

	/**
	 * 递归压缩方法
	 * @param sourceFile  源文件
	 * @param zos  zip输出流
	 * @param name  压缩后的名称
	 * @param KeepDirStructure 是否保留原来的目录结构,
	 * 				true:保留目录结构;
	 *            	false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos,
			String name, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			// copy文件到zip输出流中
			int len;
			FileInputStream in = null;
			try{
				zos.putNextEntry(new ZipEntry(name));// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
				in = new FileInputStream(sourceFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
			}finally{
				if(null != zos){
					zos.closeEntry();
				}
				close(in);
			}
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (KeepDirStructure) {
					// 空文件夹的处理
					try{
						zos.putNextEntry(new ZipEntry(name + "/"));
						// 没有文件，不需要文件的copy
					}finally{
						if(null != zos){
							zos.closeEntry();
						}
					}
				}
			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}
				}
			}
		}
	}
	
	public static void close(Closeable closeable){
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void transfer2ZipFile(File sourceFile,File zipFile) throws IOException {
		BufferedInputStream bis = null;
		GzipCompressorOutputStream gcos = null;
		try{
			bis = new BufferedInputStream(new FileInputStream(sourceFile));
			gcos = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = bis.read(buffer)) != -1) {
				gcos.write(buffer, 0, read);
			}
		}finally{
			if(gcos!=null)
				gcos.close();
			if(bis!=null)
				bis.close();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
//		/** 测试压缩方法1 */
//		FileOutputStream fos1 = new FileOutputStream(new File("c:/mytest01.zip"));
//		ZipUtils.toZip("D:/log", fos1, true);
//		/** 测试压缩方法2 */
//		List<File> fileList = new ArrayList<>();
//		fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/jar.exe"));
//		fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/java.exe"));
//		FileOutputStream fos2 = new FileOutputStream(new File("c:/mytest02.zip"));
//		ZipUtils.toZip(fileList, fos2);
	}
}
