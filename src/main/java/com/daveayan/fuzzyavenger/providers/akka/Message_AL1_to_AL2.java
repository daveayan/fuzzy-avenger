package com.daveayan.fuzzyavenger.providers.akka;

import java.util.List;

import com.daveayan.fuzzyavenger.Function;


class Message_AL1_to_AL2 {
	public int sequenceNumber;
	public Object data;
	Function functionToApply;
	List<Object> parameters;

	public Message_AL1_to_AL2(int sequenceNumber, Object data, Function functionToApply, List<Object> parameters) {
		this.sequenceNumber = sequenceNumber;
		this.data = data;
		this.functionToApply = functionToApply;
		this.parameters = parameters;
	}
}
