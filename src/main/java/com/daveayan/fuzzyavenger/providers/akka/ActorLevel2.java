package com.daveayan.fuzzyavenger.providers.akka;

import akka.actor.UntypedActor;

public class ActorLevel2 extends UntypedActor {
	public void onReceive(Object message) {
		if(message instanceof Message_AL1_to_AL2) {
			Message_AL1_to_AL2 m = (Message_AL1_to_AL2) message;
			
			Object newValue = m.functionToApply.apply(m.data, m.parameters);
			
			getSender().tell(new Message_AL2_to_AL1(m.sequenceNumber, m.data, newValue), getSelf());
		}
	}
}
