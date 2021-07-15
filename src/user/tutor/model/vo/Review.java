package user.tutor.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class Review implements Serializable{
	
	private String userId;
	private String tutorId;
	private int classNo;
	private String reviewContent;
	private int reviewScore;
	private Date reviewDate;
	
	public Review() {}

	public Review(String userId, String tutorId, int classNo, String reviewContent, int reviewScore, Date reviewDate) {
		super();
		this.userId = userId;
		this.tutorId = tutorId;
		this.classNo = classNo;
		this.reviewContent = reviewContent;
		this.reviewScore = reviewScore;
		this.reviewDate = reviewDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTutorId() {
		return tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

	public int getClassNo() {
		return classNo;
	}

	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public int getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(int reviewScore) {
		this.reviewScore = reviewScore;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Override
	public String toString() {
		return "Review [userId=" + userId + ", tutorId=" + tutorId + ", classNo=" + classNo + ", reviewContent="
				+ reviewContent + ", reviewScore=" + reviewScore + ", reviewDate=" + reviewDate + "]";
	}
	
	
}
