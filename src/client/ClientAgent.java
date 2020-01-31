package client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ClientAgent extends GuiAgent {

	private ClientGUI guiClient ;
	
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		System.out.println("Client agent starting... "+this.getAID().getName());
		guiClient = new ClientGUI();
		guiClient.setClientAgent(this);
		
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		// recieve  inform message
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {		
			@Override
			public void action() {
				 
				MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchOntology("inform"));
				ACLMessage aclMessage = receive(messageTemplate);
				
				 if (aclMessage!=null) {
					 System.out.println("inform message recieved from  " + aclMessage.getSender() );
					 guiClient.showMessage("reservation done ! "+aclMessage.getContent());
				 }
				 else {
					 block();
				 }
			}
		});
	
	}
	
	
	@Override
	protected void onGuiEvent(GuiEvent guiEvent) {
		
		if (guiEvent.getType()==1) {
			String nbplaces=guiEvent.getParameter(0).toString();
			ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(new AID("TourisAgent",AID.ISLOCALNAME));
			aclMessage.setContent(nbplaces);
			aclMessage.setOntology("reservation");
			send(aclMessage);
			guiClient.showMessage("request sent ! wait for response...");
			System.out.println("reservation sent to  " + aclMessage.getAllReceiver().next().toString());
		}
		
		
	}
	
	@Override
	protected void beforeMove() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void afterMove() {
		
	}
	@Override
	protected void takeDown() {
		System.out.println("Destruction de l'agent");
		
	}
	

}
