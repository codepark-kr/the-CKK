package point.model.vo;

import java.io.Serializable;
import java.sql.Date;

import user.vo.User;

public class Point extends User implements Serializable{
	
	private int logNum;
	private String userId;
	private String pointIO;
	private String pointContent;
	private int pointAmount;
	private Date pointIODate;
	
	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Point(String userId, String password, String commGrade, String userName, String gender, Date birthday,
			String email, String address, String phone, Date enrollDate, int pointSum, String resumeYNP,
			Date quitDate) {
		super(userId, password, commGrade, userName, gender, birthday, email, address, phone, enrollDate, pointSum, resumeYNP,
				quitDate);
		// TODO Auto-generated constructor stub
	}
	
	public Point(int logNum, String userId, String pointIO, String pointContent, int pointAmount, Date pointIODate) {
		super();
		this.logNum = logNum;
		this.userId = userId;
		this.pointIO = pointIO;
		this.pointContent = pointContent;
		this.pointAmount = pointAmount;
		this.pointIODate = pointIODate;
	}
	
	public int getLogNum() {
		return logNum;
	}
	public void setLogNum(int logNum) {
		this.logNum = logNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPointIO() {
		return pointIO;
	}
	public void setPointIO(String pointIO) {
		this.pointIO = pointIO;
	}
	public String getPointContent() {
		return pointContent;
	}
	public void setPointContent(String pointContent) {
		this.pointContent = pointContent;
	}
	public int getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(int pointAmount) {
		this.pointAmount = pointAmount;
	}
	public Date getPointIODate() {
		return pointIODate;
	}
	public void setPointIODate(Date pointIODate) {
		this.pointIODate = pointIODate;
	}
	
	@Override
	public String toString() {
		return "Point [logNum=" + logNum + ", userId=" + userId + ", pointIO=" + pointIO + ", pointContent="
				+ pointContent + ", pointAmount=" + pointAmount + ", pointIODate=" + pointIODate + "]";
	}
	
	
}
