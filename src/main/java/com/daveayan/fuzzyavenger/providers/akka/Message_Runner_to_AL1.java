package com.daveayan.fuzzyavenger.providers.akka;

import java.util.List;

import com.daveayan.fuzzyavenger.Function;

public class Message_Runner_to_AL1 {
	public Function functionToApply;
	public List<Object> parameters;
	
	public Message_Runner_to_AL1(List<Object> parameters, Function functionToApply) {
		this.functionToApply = functionToApply;
		this.parameters = parameters;
	}
}
