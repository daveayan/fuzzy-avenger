package com.daveayan.fuzzyavenger.impl

import akka.actor.UntypedActor

class ActorLevel2 extends UntypedActor {
	public void onReceive(Object message) {
		println "${this} - Got Message ${message}"
		Message_AL1_to_AL2 m = (Message_AL1_to_AL2) message
		println "${this} - Making Service Call, will run for 5 seconds - ${m.id}"
		
		Thread.sleep(5*1000)
		
		println "${this} - Done with Service Call - ${m.id}"
		getSender().tell(new Message_AL2_to_AL1(m.id), getSelf());
		println "${this} - Sent Message of Completion - ${m.id}"
	}
}
