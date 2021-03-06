package com.daveayan.fuzzyavenger.providers.akka;


import akka.actor.UntypedActor;

public class ActorLevel0_SystemShutdowner extends UntypedActor {
	public void onReceive(Object message) {
		if (message instanceof Message_AL1_to_AL0) {
			getContext().system().shutdown();
		} else {
			unhandled(message);
		}
	}
}
