package common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {

	/**
	 * abc.txt 파일저장싱
	 * yyyyMMdd_HHmmssSSS_123(난수).txt 로 파일명 저장
	 */
	@Override
	public File rename(File oldFile) {
		File newFile = null;
		
		do {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			int rnd = (int)(Math.random()*1000); //0~999
			
			//확장자 처리
			String oldName = oldFile.getName(); //abc.txt
			String ext = "";
			int dot = oldName.lastIndexOf(".");
			
			if(dot > -1) ext = oldName.substring(dot);
			
			String newName = rnd + "_" + sdf.format(new Date()) + ext;
			
			newFile = new File(oldFile.getParent(), newName);
		} while(!createNewFile(newFile));
		
//		System.out.println("newFile@mvcRenamePolicy = " + newFile);
		
		return newFile;
	}

	private boolean createNewFile(File f) {
	   try {
	     return f.createNewFile();
	   }
	   catch (IOException ignored) {
	     return false;
	   }
	}

}
