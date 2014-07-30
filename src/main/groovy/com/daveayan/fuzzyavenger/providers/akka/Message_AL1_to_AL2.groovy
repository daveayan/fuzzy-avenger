package com.daveayan.fuzzyavenger.providers.akka

import com.daveayan.fuzzyavenger.Function


class Message_AL1_to_AL2 {
	public int sequenceNumber
	public Object data
	Function functionToApply
	List<Object> parameters

	public Message_AL1_to_AL2(sequenceNumber, data, functionToApply, parameters) {
		this.sequenceNumber = sequenceNumber
		this.data = data
		this.functionToApply = functionToApply
		this.parameters = parameters
	}
	
	public String toString() {
		return "#${sequenceNumber}, ${data} ${parameters}"
	}
}
