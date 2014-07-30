package com.daveayan.fuzzyavenger.providers.akka

import akka.actor.UntypedActor

class ActorLevel2 extends UntypedActor {
	public void onReceive(Object message) {
		println "${this} - Got Message ${message}"
		if(message instanceof Message_AL1_to_AL2) {
			Message_AL1_to_AL2 m = (Message_AL1_to_AL2) message
			
			message.functionToApply.apply(message.data)
			
			getSender().tell(new Message_AL2_to_AL1(message.data), getSelf());
			println "${this} - Sent Message of Completion - ${message.data}"
		}
	}
}
