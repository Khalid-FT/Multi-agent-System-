package restaurant;

import java.util.HashMap;
import java.util.Map;

import client.ClientGUI;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class RestaurantAgent extends GuiAgent {
	
	private int capacite ;
	private RestauGUI guiRestau ;
	private static int nb_places ;
	 @Override
	    protected void setup() {
	        
		    System.out.println("Restaurant agent starting... "+this.getAID().getName());
		    guiRestau = new RestauGUI();
		    guiRestau.setRestauAgent(this);
			
		    
			ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
			addBehaviour(parallelBehaviour);

			// publier services
			parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
				
				@Override
				public void action() {
					
					// registe services to df
					DFAgentDescription dfa= new DFAgentDescription();
					dfa.setName(getAID());
					ServiceDescription sd=new ServiceDescription();
					sd.setType("capacite");
					sd.setName("restaurant reservation");
					dfa.addServices(sd);
					try {
						DFService.register(myAgent, dfa);
						System.out.println("service shared");
					} catch (FIPAException e) {
						
						e.printStackTrace();
					}
				}
			});
			
		
			// recieve CFP messgae
			parallelBehaviour.addSubBehaviour(new CyclicBehaviour( ) {
				
				@Override
				public void action() {
					
					ACLMessage aclMessage=receive();
					if(aclMessage!=null) {
						String sender = aclMessage.getSender().getName();
						String[] sendername = sender.split("@");
						String nbplaces = aclMessage.getContent();
						nb_places = Integer.parseInt(nbplaces) ;
						switch (aclMessage.getPerformative()) {
						case ACLMessage.CFP:
							 // send proposion							 
							 System.out.println("CFP RECIEVED FROM "+ sendername[0]);
							 guiRestau.showMessage("nb places required : " + nbplaces + " ! send a proposition..");	
							break;
						case ACLMessage.ACCEPT_PROPOSAL:
							System.out.println("propos accepted !" + aclMessage.getContent());
							guiRestau.showMessage(" propos accepted ! sending confirmation.."+"\n done!");							
							// send accept  to sender 
							ACLMessage apMessage=new ACLMessage(ACLMessage.AGREE);
							apMessage.addReceiver(new AID(sendername[0],AID.ISLOCALNAME));
							apMessage.setContent(nbplaces);
							apMessage.setOntology("agree");
							send(apMessage);
							
							break;
						case ACLMessage.REJECT_PROPOSAL:
							System.out.println("propos rejected !" + aclMessage.getContent());
							guiRestau.showMessage("propos rejected !");							
							break;
						default:
							break;
						}
						
					}
					else {
						block();
					}
				}
			});
		  
		
	       
	 }

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
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
		
		System.out.println("take down services called!");
		// deregister services

			try {
				DFService.deregister(this);
			} catch (FIPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		
	}

	@Override
	protected void onGuiEvent(GuiEvent guiEvent) {
		
		
		if (guiEvent.getType()==1) {
			String capacite=guiEvent.getParameter(0).toString() ;
			if( Integer.parseInt(capacite) > nb_places ) {
				 guiRestau.showMessage("capacite must be less than nb places --> " + nb_places );
			}
			else {
				// send propose to tourisAgent
				 ACLMessage propose=new ACLMessage(ACLMessage.PROPOSE);
				 propose.addReceiver(new AID("TourisAgent",AID.ISLOCALNAME));
				 propose.setContent(capacite);
				 propose.setOntology("propose");
				 send(propose);
				 guiRestau.showMessage("propose sent ! wait for response...");
				
			}
			
		}
		
	}
	
}
