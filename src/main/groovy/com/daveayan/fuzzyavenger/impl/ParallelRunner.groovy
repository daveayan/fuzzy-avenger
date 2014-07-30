package com.daveayan.fuzzyavenger.impl

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.actor.UntypedActorFactory

class ParallelRunner {
	public static void main (String[] args) {
		ParallelRunner at = new ParallelRunner()
		at.runme()
	}
	
	def runme() {
		def ids = ["1", "2", "3"]
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
