package user.vo;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable{
	
	private String userId;
	private String password;
    private String commGrade;
    private String userName;
    private String gender;
    private Date birthday;
    private String email;
    private String address;
    private String phone;
    private Date enrollDate;
    private int pointSum;
    private String resumeYNP;
    private Date quitDate;
    
    private double commission;
	
    public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String password, String commGrade, String userName, String gender, Date birthday,
			String email, String address, String phone, Date enrollDate, int pointSum, String resumeYNP,
			Date quitDate) {
		super();
		this.userId = userId;
		this.password = password;
		this.commGrade = commGrade;
		this.userName = userName;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.enrollDate = enrollDate;
		this.pointSum = pointSum;
		this.resumeYNP = resumeYNP;
		this.quitDate = quitDate;
	}

	public User(double commission) {
		super();
		this.commission = commission;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommGrade() {
		return commGrade;
	}

	public void setCommGrade(String commGrade) {
		this.commGrade = commGrade;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public int getPointSum() {
		return pointSum;
	}

	public void setPointSum(int pointSum) {
		this.pointSum = pointSum;
	}

	public String getResumeYNP() {
		return resumeYNP;
	}

	public void setResumeYNP(String resumeYNP) {
		this.resumeYNP = resumeYNP;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", commGrade=" + commGrade + ", userName="
				+ userName + ", gender=" + gender + ", birthday=" + birthday + ", email=" + email + ", address="
				+ address + ", phone=" + phone + ", enrollDate=" + enrollDate + ", pointSum=" + pointSum
				+ ", resumeYNP=" + resumeYNP + ", quitDate=" + quitDate + ", commission=" + commission + "]";
	}

    
}
