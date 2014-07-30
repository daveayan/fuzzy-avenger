package com.daveayan.fuzzyavenger.providers.akka

import org.apache.commons.collections.ListUtils

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.UntypedActor
import akka.routing.RoundRobinRouter

class ActorLevel1 extends UntypedActor {
	private final ActorRef shutdownCommandListener;
	private final ActorRef routerActor;
	private final List<Object> data
	private final List<Object> newValues
	private final nrOfWorkers
	private int numberOfResultsGot = 0
		
	public ActorLevel1(List<Object> data, int nrOfWorkers) {
		this.data = data;
		this.nrOfWorkers = nrOfWorkers
		this.newValues = ListUtils.fixedSizeList(data)
		routerActor = this.getContext().actorOf(
			new Props(ActorLevel2.class)
			.withRouter(new RoundRobinRouter(nrOfWorkers)), "roundRobinRouterActor");
	}
	
	public void onReceive(Object message) {
		println "${this} - Got Message ${message}"
		if(message instanceof Message_AL2_to_AL1) {
			println "${this} - Got results from ${message}"
			numberOfResultsGot++
			newValues.set(message.sequenceNumber, message.newValue)
			if(numberOfResultsGot == data.size()) {
				println "${this} - All data elements processed"
				println "${newValues}"
				getContext().stop(getSelf());
			}
		}
		if(message instanceof Message_Runner_to_AL1) {
			for (int i = 0; i < data.size(); i++) {
				routerActor.tell(new Message_AL1_to_AL2(i, data.get(i), message.functionToApply, message.parameters), getSelf());
			}
		}
		println "${this} - Done Processing Message ${message}"
	}
}
