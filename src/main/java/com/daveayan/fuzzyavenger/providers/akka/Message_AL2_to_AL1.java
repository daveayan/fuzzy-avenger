package com.daveayan.fuzzyavenger.providers.akka;

class Message_AL2_to_AL1 {
	public int sequenceNumber;
	public Object data;
	public Object newValue;
	
	public Message_AL2_to_AL1(int sequenceNumber, Object data, Object newValue) {
		this.sequenceNumber = sequenceNumber;
		this.data = data;
		this.newValue = newValue;
	}
}
