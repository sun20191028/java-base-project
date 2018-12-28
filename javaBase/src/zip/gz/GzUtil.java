package zip.gz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class GzUtil {
	/**
	 * 归档
	 * @param entry
	 * @throws IOException
	 * @return
	 */
	private static void archive(File sourceFile,File tarFile) throws IOException {
		TarArchiveOutputStream tos = null;
		try{
			tos = new TarArchiveOutputStream(new FileOutputStream(tarFile));
			String base = "";//文件夹需要把文件夹也压缩进去，所以要递归拼接，文件不需要
			if (sourceFile.isDirectory()) {
				base = sourceFile.getName();
				archiveDir(sourceFile, tos, base);
			} else {
				archiveHandle(tos, sourceFile, base);
			}
		}finally{
			close(tos);
		}
	}

	/**
	 * 递归处理，准备好路径
	 * @param file
	 * @param tos
	 * @param base
	 * @throws IOException
	 */
	private static void archiveDir(File file, TarArchiveOutputStream tos,
			String basePath) throws IOException {
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveHandle(tos, fi, basePath);
			}
		}
	}

	/**
	 * 具体归档处理（文件）
	 * @param tos
	 * @param fi
	 * @param base
	 * @throws IOException
	 */
	private static void archiveHandle(TarArchiveOutputStream tos, File fi,String basePath) throws IOException {
		TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
		tEntry.setSize(fi.length());
		BufferedInputStream bis = null;
		try{
			tos.putArchiveEntry(tEntry);
			bis = new BufferedInputStream(new FileInputStream(fi));
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = bis.read(buffer)) != -1) {
				tos.write(buffer, 0, read);
			}
		}catch(Exception e){
			throw e;
		}finally{
			close(bis);
			if(null != tos){
				tos.closeArchiveEntry();// 这里必须写，否则会失败
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
			close(gcos);
			close(bis);
		}
	}
	

	
	/**
     * 解压 gz
     * @param archive
     * @throws IOException 
     */
    private static void unCompressArchiveGz(String gzFile,String tarFile) throws IOException {

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(gzFile));

		GzipCompressorInputStream gcis = new GzipCompressorInputStream(
				new BufferedInputStream(new FileInputStream(tarFile)));

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
        gcis.close();
        bos.close();

//        unCompressTar(finalName);
    }
	
    /**
     * 解压tar
     * @param finalName
     * @throws IOException 
     */
    private static void unCompressTar(String finalName) throws IOException {

        File file = new File(finalName);
        String parentPath = file.getParent();
        TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file));

        TarArchiveEntry tarArchiveEntry = null;

        while((tarArchiveEntry = tais.getNextTarEntry()) != null){
            String name = tarArchiveEntry.getName();
            File tarFile = new File(parentPath, name);
            if(!tarFile.getParentFile().exists()){
                tarFile.getParentFile().mkdirs();
            }

            BufferedOutputStream bos = 
            new BufferedOutputStream(new FileOutputStream(tarFile));

            int read = -1;
            byte[] buffer = new byte[1024];
            while((read = tais.read(buffer)) != -1){
                bos.write(buffer, 0, read);
            }
            bos.close();
        }
        tais.close();
        file.delete();//删除tar文件
    }
    
	public static void close(Closeable closeable) throws IOException{
		if(null != closeable){
			closeable.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		//文件归档
//		String entry = "D:/project/git/java-base-project/javaBase/src/zip/gz/fold/test";
//		String zip = "D:/project/git/java-base-project/javaBase/src/zip/gz/fold/test";
//		String tar = "D:/project/git/java-base-project/javaBase/src/zip/gz/fold/tarfile.tar";
//		File sourceFile = new File(entry);
//		File tarFile = new File(tar);
//		
//		if(!tarFile.exists()){
//			tarFile.createNewFile();
//		}
//		archive(sourceFile,tarFile);// 生成tar包
		
		

		String tar = "D:/project/git/java-base-project/javaBase/src/zip/gz/fold/tarfile.tar";
		String gz = "D:/project/git/java-base-project/javaBase/src/zip/gz/fold/gzfile.gz";
		File tarFile = new File(tar);
		File gzFile = new File(gz);
		if(!tarFile.exists()){
			tarFile.createNewFile();
		}
		if(!gzFile.exists()){
			gzFile.createNewFile();
		}
		transfer2ZipFile(tarFile,gzFile);// 生成gz包
		

		// unCompressArchiveGz("C:\\Users\\yutao\\Desktop\\pageage\\test.tar.gz");//解压
	}
}
