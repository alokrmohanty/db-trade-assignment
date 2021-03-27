package com.db.assignment.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TradeInfoDto {
	private String tradeId;
	private Integer version;
	private String partyId;
	private String bookId;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date maturityDt;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdDt;
	private String expired="N";	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public Date getMaturityDt() {
		return maturityDt;
	}
	public void setMaturityDt(Date maturityDt) {
		this.maturityDt = maturityDt;
	}
	public Date getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	@Override
	public String toString() {
		return "TradeInfoDto [tradeId=" + tradeId + ", version=" + version + ", partyId=" + partyId + ", bookId="
				+ bookId + ", maturityDt=" + maturityDt + ", createdDt=" + createdDt + ", expired=" + expired + "]";
	}
	
}
