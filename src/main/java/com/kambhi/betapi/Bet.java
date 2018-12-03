package com.kambhi.betapi;

public class Bet {
	long customerId;
	long betOfferId;
	long stake;

	public Bet() {

	}

	public Bet(long customerId, long betOfferId, long stake) {
		super();
		this.customerId = customerId;
		this.betOfferId = betOfferId;
		this.stake = stake;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getBetOfferId() {
		return betOfferId;
	}

	public void setBetOfferId(long betOfferId) {
		this.betOfferId = betOfferId;
	}

	public long getStake() {
		return stake;
	}

	public void setStake(long stake) {
		this.stake = stake;
	}

	@Override
	public String toString() {
		return "Bet [customerId=" + customerId + ", betOfferId=" + betOfferId + ", stake=" + stake + "]";
	}

}
