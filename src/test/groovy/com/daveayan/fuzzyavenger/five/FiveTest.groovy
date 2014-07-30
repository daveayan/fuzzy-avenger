package com.daveayan.fuzzyavenger.five

import org.junit.Test

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.actor.UntypedActorFactory
import akka.routing.RoundRobinRouter

class FiveTest {
	@Test public void listening_to_system_shutdown_command() {
		ParallelRunner at = new ParallelRunner()
		at.runme()
	}
}

class ParallelRunner {
	def runme() {
		def ids = ["A", "B", "C", "D", "E", "F"]
		def nrOfWorkers = 4
		
		ActorSystem system = ActorSystem.create("fuzzy-avenger-" + System.currentTimeMillis());
		
		final ActorRef shutdownCommandListener = system.actorOf(new Props(ActorLevel0_SystemShutdowner.class), "shutdownCommandListener");

		ActorRef actorLevel1 = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;
			public UntypedActor create() {
				return new ActorLevel1(ids, nrOfWorkers, shutdownCommandListener);
			}
		}), "ActorLevel1");
		actorLevel1.tell(new Message_Runner_to_AL1(), ActorRef.noSender());
		System.out.println("${this} - OUT OF HERE");
	}
}

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

class Message_AL1_to_AL0 {
}

class Message_AL1_to_AL2 {
	public String id
	public Message_AL1_to_AL2(String id) {
		this.id = id
	}
}

class Message_AL2_to_AL1 {
	public String id
	public Message_AL2_to_AL1(String id) {
		this.id = id
	}
}

class Message_Runner_to_AL1 {

}
