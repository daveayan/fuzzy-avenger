package com.daveayan.fuzzyavenger.providers.akka

class Message_AL2_to_AL1 {
	private int sequenceNumber
	public Object data
	public Object newValue
	
	public Message_AL2_to_AL1(sequenceNumber, data, newValue) {
		this.sequenceNumber = sequenceNumber
		this.data = data
		this.newValue = newValue
	}
	
	public String toString() {
		return "#${sequenceNumber}, ${data} -> ${newValue}"
	}
}
