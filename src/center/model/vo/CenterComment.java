package center.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class CenterComment implements Serializable{

	private int csNo;
	private String adminId;
	private String csaContent;
	private Date csaDate;
	
	public CenterComment() {}

	public CenterComment(int csNo, String adminId, String csaContent, Date csaDate) {
		super();
		this.csNo = csNo;
		this.adminId = adminId;
		this.csaContent = csaContent;
		this.csaDate = csaDate;
	}

	public int getCsNo() {
		return csNo;
	}

	public void setCsNo(int csNo) {
		this.csNo = csNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getCsaContent() {
		return csaContent;
	}

	public void setCsaContent(String csaContent) {
		this.csaContent = csaContent;
	}

	public Date getCsaDate() {
		return csaDate;
	}

	public void setCsaDate(Date csaDate) {
		this.csaDate = csaDate;
	}

	@Override
	public String toString() {
		return "CenterComment [csNo=" + csNo + ", adminId=" + adminId + ", csaContent=" + csaContent + ", csaDate="
				+ csaDate + "]";
	}
	
	
	

	
	
}
