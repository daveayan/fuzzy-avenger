package com.daveayan.fuzzyavenger.providers.akka

import com.daveayan.fuzzyavenger.Function

class Message_Runner_to_AL1 {
	Function functionToApply
	List<Object> parameters
	
	public Message_Runner_to_AL1(parameters, functionToApply) {
		this.functionToApply = functionToApply
		this.parameters = parameters
	}
}
