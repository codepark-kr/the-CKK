package user.tutor.model.vo;

import java.io.Serializable;

public class TutorResume implements Serializable{

	private String tutorId;
	private String awardRecord;
	private String career;
	private String profileOrg;
	private String profileRen;
	private String certN1;
	private String certC1;
	private String certN2;
	private String certC2;
	private String certN3;
	private String certC3;
	
	public TutorResume() {}

	public TutorResume(String tutorId, String awardRecord, String career, String profileOrg, String profileRen,
			String certN1, String certC1, String certN2, String certC2, String certN3, String certC3) {
		super();
		this.tutorId = tutorId;
		this.awardRecord = awardRecord;
		this.career = career;
		this.profileOrg = profileOrg;
		this.profileRen = profileRen;
		this.certN1 = certN1;
		this.certC1 = certC1;
		this.certN2 = certN2;
		this.certC2 = certC2;
		this.certN3 = certN3;
		this.certC3 = certC3;
	}

	public String getTutorId() {
		return tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

	public String getAwardRecord() {
		return awardRecord;
	}

	public void setAwardRecord(String awardRecord) {
		this.awardRecord = awardRecord;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getProfileOrg() {
		return profileOrg;
	}

	public void setProfileOrg(String profileOrg) {
		this.profileOrg = profileOrg;
	}

	public String getProfileRen() {
		return profileRen;
	}

	public void setProfileRen(String profileRen) {
		this.profileRen = profileRen;
	}

	public String getCertN1() {
		return certN1;
	}

	public void setCertN1(String certN1) {
		this.certN1 = certN1;
	}

	public String getCertC1() {
		return certC1;
	}

	public void setCertC1(String certC1) {
		this.certC1 = certC1;
	}

	public String getCertN2() {
		return certN2;
	}

	public void setCertN2(String certN2) {
		this.certN2 = certN2;
	}

	public String getCertC2() {
		return certC2;
	}

	public void setCertC2(String certC2) {
		this.certC2 = certC2;
	}

	public String getCertN3() {
		return certN3;
	}

	public void setCertN3(String certN3) {
		this.certN3 = certN3;
	}

	public String getCertC3() {
		return certC3;
	}

	public void setCertC3(String certC3) {
		this.certC3 = certC3;
	}

	@Override
	public String toString() {
		return "TutorResume [tutorId=" + tutorId + ", awardRecord=" + awardRecord + ", career=" + career
				+ ", profileOrg=" + profileOrg + ", profileRen=" + profileRen + ", certN1=" + certN1 + ", certC1="
				+ certC1 + ", certN2=" + certN2 + ", certC2=" + certC2 + ", certN3=" + certN3 + ", certC3=" + certC3
				+ "]";
	}
	
	
	
}
