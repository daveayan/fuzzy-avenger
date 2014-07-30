package com.daveayan.fuzzyavenger.impl


import akka.actor.UntypedActor

class ActorLevel0_SystemShutdowner extends UntypedActor {
	public void onReceive(Object message) {
		if (message instanceof Message_AL1_to_AL0) {
			println "${this} - I will print the final results when I have them. Shutting down for now."
			getContext().system().shutdown();
		} else {
			unhandled(message);
		}
	}
}
