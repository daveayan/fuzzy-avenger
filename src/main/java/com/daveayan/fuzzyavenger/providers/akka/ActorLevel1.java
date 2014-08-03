package com.daveayan.fuzzyavenger.providers.akka;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class ActorLevel1 extends UntypedActor {
	private final ActorRef shutdownCommandListener = null;
	private final ActorRef routerActor;
	private final List<Object> data;
	private final List<Object> newValues;
	private final int nrOfWorkers;
	private int numberOfResultsGot = 0;
	private ActorRef promiseActor;
		
	public ActorLevel1(List<Object> data, int nrOfWorkers) {
		this.data = data;
		this.nrOfWorkers = nrOfWorkers;
		System.out.println("SIZE OF LIST = " + data.size());
		this.newValues = new ArrayList();
		System.out.println("SIZE OF LIST = " + data.size());
		for(int i = 0; i < data.size() ; i++) {
			newValues.add(new Object());
		}
		System.out.println("SIZE OF NEW LIST = " + newValues.size());
		routerActor = this.getContext().actorOf(
			new Props(ActorLevel2.class)
			.withRouter(new RoundRobinRouter(nrOfWorkers)), "roundRobinRouterActor");
	}
	
	public void onReceive(Object message) {
		if(message instanceof Message_AL2_to_AL1) {
			Message_AL2_to_AL1 m = (Message_AL2_to_AL1) message;
			numberOfResultsGot++;
			newValues.set(m.sequenceNumber, m.newValue);
			if(numberOfResultsGot == data.size()) {
				promiseActor.tell(newValues, getSelf());
				getContext().stop(getSelf());
			}
		}
		if(message instanceof Message_Runner_to_AL1) {
			Message_Runner_to_AL1 m = (Message_Runner_to_AL1) message;
			promiseActor = getSender();
			for (int i = 0; i < data.size(); i++) {
				routerActor.tell(new Message_AL1_to_AL2(i, data.get(i), m.functionToApply, m.parameters), getSelf());
			}
		}
	}
}
