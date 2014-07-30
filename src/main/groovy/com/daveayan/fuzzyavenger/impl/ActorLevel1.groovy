package com.daveayan.fuzzyavenger.impl

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.UntypedActor
import akka.routing.RoundRobinRouter

class ActorLevel1 extends UntypedActor {
	private final ActorRef shutdownCommandListener;
	private final ActorRef routerActor;
	private final List<String> ids
	private final nrOfWorkers
	private int numberOfResultsGot = 0
		
	public ActorLevel1(List<String> ids, int nrOfWorkers, ActorRef shutdownCommandListener) {
		this.shutdownCommandListener = shutdownCommandListener;
		this.ids = ids;
		this.nrOfWorkers = nrOfWorkers
		routerActor = this.getContext().actorOf(
			new Props(ActorLevel2.class)
			.withRouter(new RoundRobinRouter(nrOfWorkers)), "roundRobinRouterActor");
	}
	
	public void onReceive(Object message) {
		println "${this} - Got Message ${message}"
		if(message instanceof Message_AL2_to_AL1) {
			println "${this} - Got results from ${message.id}"
			numberOfResultsGot++
			if(numberOfResultsGot == ids.size()) {
				println "${this} - All ID's processed"
				shutdownCommandListener.tell(new Message_AL1_to_AL0(), getSelf())
				getContext().stop(getSelf());
			}
		}
		if(message instanceof Message_Runner_to_AL1) {
			for (int i = 0; i < ids.size(); i++) {
				routerActor.tell(new Message_AL1_to_AL2(ids.get(i)), getSelf());
			}
		}
		println "${this} - Done Processing Message ${message}"
	}
}
