package com.daveayan.fuzzyavenger.providers.akka

import com.google.common.base.Function;


class Message_AL1_to_AL2 {
	public int sequenceNumber
	public Object data
	Function functionToApply

	public Message_AL1_to_AL2(sequenceNumber, data, functionToApply) {
		this.sequenceNumber = sequenceNumber
		this.data = data
		this.functionToApply = functionToApply
	}
	
	public String toString() {
		return "#${sequenceNumber}, ${data}"
	}
}
