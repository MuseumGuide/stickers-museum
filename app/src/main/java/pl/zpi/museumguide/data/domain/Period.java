package pl.zpi.museumguide.data.domain;

import java.util.Date;

public class Period {

	private Date startDate; //nullable
	private Date endDate; //nullable
	private int genesisCentury; //nullable

	public Period() {
	}

	public Period(long start, long end) {
		//todo implement method
	}

	public Period(Date startDate, Date endDate, int century) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.genesisCentury = century;
	}

	public int getCentury() {
		return genesisCentury;
	}

	public void setCentury(int century) {
		this.genesisCentury = century;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}