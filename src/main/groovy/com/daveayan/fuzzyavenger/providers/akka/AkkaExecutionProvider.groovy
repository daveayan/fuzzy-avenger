package com.daveayan.fuzzyavenger.providers.akka

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.actor.UntypedActorFactory

import com.daveayan.fuzzyavenger.ExecutionProvider
import com.daveayan.fuzzyavenger.Function

class AkkaExecutionProvider implements ExecutionProvider {
	def system, shutdownCommandListener
	
	public static AkkaExecutionProvider newInstance() {
		ExecutionProvider ep = new AkkaExecutionProvider()
		ep.initialize()
		return ep
	}
	
	public void initialize() {
		system = ActorSystem.create("fuzzy-avenger-" + System.currentTimeMillis());
		shutdownCommandListener = system.actorOf(new Props(ActorLevel0_SystemShutdowner.class), "shutdownCommandListener");
	}
	
	public void run(List<Object> data, List<Object> parameters, Function<?, ?> functionToApply, int numberOfWorkers) {
		ActorRef actorLevel1 = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;
			public UntypedActor create() {
				return new ActorLevel1(data, numberOfWorkers);
			}
		}), "ActorLevel1-" + System.currentTimeMillis());
		
		actorLevel1.tell(new Message_Runner_to_AL1(parameters, functionToApply), ActorRef.noSender());	
		System.out.println("${this} - OUT OF HERE");
	}
	
	public void shutdown() {
		shutdownCommandListener.tell(new Message_AL1_to_AL0(), ActorRef.noSender())
	}
}
