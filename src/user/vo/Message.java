package user.vo;

import java.io.Serializable;
import java.sql.Date;

public class Message implements Serializable{

	private int msgNo;
	private String userId;
	private String msgTitle;
	private String msgContent;
	private Date msgDate;
	private String msgReadYn;
	
	public Message() {}

	public Message(int msgNo, String userId, String msgTitle, String msgContent, Date msgDate, String msgReadYn) {
		super();
		this.msgNo = msgNo;
		this.userId = userId;
		this.msgTitle = msgTitle;
		this.msgContent = msgContent;
		this.msgDate = msgDate;
		this.msgReadYn = msgReadYn;
	}

	public int getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public String getMsgReadYn() {
		return msgReadYn;
	}

	public void setMsgReadYn(String msgReadYn) {
		this.msgReadYn = msgReadYn;
	}

	@Override
	public String toString() {
		return "Message [msgNo=" + msgNo + ", userId=" + userId + ", msgTitle=" + msgTitle + ", msgContent="
				+ msgContent + ", msgDate=" + msgDate + ", msgReadYn=" + msgReadYn + "]";
	}
	
	
}
