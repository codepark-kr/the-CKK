package board.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable{

	private int classNo;			//클래스 고유 번호
	private String tutorId;			//클래스 담당 튜터아이디
	private int price;				//클래스 가격
	private String title;			//클래스 제목
	private String classContent;	//클래스 내용
	private Date lastApplyDate;		//클래스 모집 마감 날짜
	private int capacity;			//클래스 모집 마감 인원
	private String classAddress;	//실제 수업 주소
	private String applyExpireYn;	//클래스 마감 여부
	private String classLocation;	//클래스 지역
	private int startTime;			//수업 시작 시간
	private int endTime;			//수업 종료 시간
	private Date classDate;			//실제 수업 날짜
	private String classEndYn;		//수업 종료 여부
	private String category;
	
	private String classPic1Org;	//클래스 사진 1 본 파일명
	private String classPic1Ren;	//클래스 사진 1 실 파일명
	private String classPic2Org;	//클래스 사진 2 본 파일명
	private String classPic2Ren;	//클래스 사진 2 실 파일명
	private String classPic3Org;	//클래스 사진 3 본 파일명
	private String classPic3Ren;	//클래스 사진 3 실 파일명
	
	private String tutorName;
	
	public Board() {}

	public Board(int classNo, String tutorId, int price, String title, String classContent, Date lastApplyDate,
			int capacity, String classAddress, String applyExpireYn, String classLocation, int startTime, int endTime,
			Date classDate, String classEndYn, String category, String classPic1Org, String classPic1Ren, String classPic2Org,
			String classPic2Ren, String classPic3Org, String classPic3Ren, String tutorName) {
		super();
		this.classNo = classNo;
		this.tutorId = tutorId;
		this.price = price;
		this.title = title;
		this.classContent = classContent;
		this.lastApplyDate = lastApplyDate;
		this.capacity = capacity;
		this.classAddress = classAddress;
		this.applyExpireYn = applyExpireYn;
		this.classLocation = classLocation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.classDate = classDate;
		this.classEndYn = classEndYn;
		this.category = category;
		this.classPic1Org = classPic1Org;
		this.classPic1Ren = classPic1Ren;
		this.classPic2Org = classPic2Org;
		this.classPic2Ren = classPic2Ren;
		this.classPic3Org = classPic3Org;
		this.classPic3Ren = classPic3Ren;
		this.tutorName = tutorName;
	}

	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public String getTutorId() {
		return tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClassContent() {
		return classContent;
	}

	public void setClassContent(String classContent) {
		this.classContent = classContent;
	}

	public Date getLastApplyDate() {
		return lastApplyDate;
	}

	public void setLastApplyDate(Date lastApplyDate) {
		this.lastApplyDate = lastApplyDate;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getClassAddress() {
		return classAddress;
	}

	public void setClassAddress(String classAddress) {
		this.classAddress = classAddress;
	}

	public String getApplyExpireYn() {
		return applyExpireYn;
	}

	public void setApplyExpireYn(String applyExpireYn) {
		this.applyExpireYn = applyExpireYn;
	}

	public String getClassLocation() {
		return classLocation;
	}

	public void setClassLocation(String classLocation) {
		this.classLocation = classLocation;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public Date getClassDate() {
		return classDate;
	}

	public void setClassDate(Date classDate) {
		this.classDate = classDate;
	}

	public String getClassEndYn() {
		return classEndYn;
	}

	public void setClassEndYn(String classEndYn) {
		this.classEndYn = classEndYn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassPic1Org() {
		return classPic1Org;
	}

	public void setClassPic1Org(String classPic1Org) {
		this.classPic1Org = classPic1Org;
	}

	public String getClassPic1Ren() {
		return classPic1Ren;
	}

	public void setClassPic1Ren(String classPic1Ren) {
		this.classPic1Ren = classPic1Ren;
	}

	public String getClassPic2Org() {
		return classPic2Org;
	}

	public void setClassPic2Org(String classPic2Org) {
		this.classPic2Org = classPic2Org;
	}

	public String getClassPic2Ren() {
		return classPic2Ren;
	}

	public void setClassPic2Ren(String classPic2Ren) {
		this.classPic2Ren = classPic2Ren;
	}

	public String getClassPic3Org() {
		return classPic3Org;
	}

	public void setClassPic3Org(String classPic3Org) {
		this.classPic3Org = classPic3Org;
	}

	public String getClassPic3Ren() {
		return classPic3Ren;
	}

	public void setClassPic3Ren(String classPic3Ren) {
		this.classPic3Ren = classPic3Ren;
	}
	
	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	@Override
	public String toString() {
		return "Board [classNo=" + classNo + ", tutorId=" + tutorId + ", price=" + price + ", title=" + title
				+ ", classContent=" + classContent + ", lastApplyDate=" + lastApplyDate + ", capacity=" + capacity
				+ ", classAddress=" + classAddress + ", applyExpireYn=" + applyExpireYn + ", classLocation="
				+ classLocation + ", startTime=" + startTime + ", endTime=" + endTime + ", classDate=" + classDate
				+ ", classEndYn=" + classEndYn + ", category=" + category + ", classPic1Org=" + classPic1Org
				+ ", classPic1Ren=" + classPic1Ren + ", classPic2Org=" + classPic2Org + ", classPic2Ren=" + classPic2Ren
				+ ", classPic3Org=" + classPic3Org + ", classPic3Ren=" + classPic3Ren + ", tutorName=" + tutorName
				+ "]";
	}

	
}
