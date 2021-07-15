package center.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class Center implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	private int csNo; //게시글번호
	private String userId; //작성자
	private String csTitle; //게시글제목
	private String csContent; //게시글내용
	private String csFileOrg; //첨부파일원본
	private String csFileRen; //바뀐이름
	private Date csWriteDate; //게시글 작성일자
	private String csAnswerYN;
	private String csSecretYN;
	
	public Center() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Center(int csNo, String userId, String csTitle, String csContent, String csFileOrg, String csFileRen,
			Date csWriteDate, String csAnswerYN, String csSecretYN) {
		super();
		this.csNo = csNo;
		this.userId = userId;
		this.csTitle = csTitle;
		this.csContent = csContent;
		this.csFileOrg = csFileOrg;
		this.csFileRen = csFileRen;
		this.csWriteDate = csWriteDate;
		this.csAnswerYN = csAnswerYN;
		this.csSecretYN = csSecretYN;
	}
	public int getCsNo() {
		return csNo;
	}
	public void setCsNo(int csNo) {
		this.csNo = csNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCsTitle() {
		return csTitle;
	}
	public void setCsTitle(String csTitle) {
		this.csTitle = csTitle;
	}
	public String getCsContent() {
		return csContent;
	}
	public void setCsContent(String csContent) {
		this.csContent = csContent;
	}
	public String getCsFileOrg() {
		return csFileOrg;
	}
	public void setCsFileOrg(String csFileOrg) {
		this.csFileOrg = csFileOrg;
	}
	public String getCsFileRen() {
		return csFileRen;
	}
	public void setCsFileRen(String csFileRen) {
		this.csFileRen = csFileRen;
	}
	public Date getCsWriteDate() {
		return csWriteDate;
	}
	public void setCsWriteDate(Date csWriteDate) {
		this.csWriteDate = csWriteDate;
	}
	public String getCsAnswerYN() {
		return csAnswerYN;
	}
	public void setCsAnswerYN(String csAnswerYN) {
		this.csAnswerYN = csAnswerYN;
	}
	public String getCsSecretYN() {
		return csSecretYN;
	}
	public void setCsSecretYN(String csSecretYN) {
		this.csSecretYN = csSecretYN;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Center [csNo=" + csNo + ", userId=" + userId + ", csTitle=" + csTitle + ", csContent=" + csContent
				+ ", csFileOrg=" + csFileOrg + ", csFileRen=" + csFileRen + ", csWriteDate=" + csWriteDate
				+ ", csAnswerYN=" + csAnswerYN + ", csSecretYN=" + csSecretYN + "]";
	}

	
}