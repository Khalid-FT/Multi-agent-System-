package tourisDep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class TourisAgent extends Agent {
	
	private AID[] listResto ;
    private Map<String  , Integer> propos = new HashMap<String , Integer>() ;
    
	@Override
	protected void setup() {

		System.out.println("Touris agent starting... "+this.getAID().getName());
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		
		// chercher list restos 
        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,6000) {
			@Override
			protected void onTick() {
				// TODO Auto-generated method stub
				DFAgentDescription description=new DFAgentDescription();
				ServiceDescription serviceDescription=new ServiceDescription();
				serviceDescription.setType("capacite");
				description.addServices(serviceDescription);
				try {
					DFAgentDescription[] agentDescriptions=DFService.search(myAgent , description);
					listResto= new AID[agentDescriptions.length];
					for (int i =0 ; i<agentDescriptions.length ; i++) {
						listResto[i] = agentDescriptions[i].getName() ;
					}
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		

		// recieve request(client) 
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {	
			@Override
			public void action() {
				
				ACLMessage aclMessage=receive();
				if(aclMessage!=null) {
					String send = aclMessage.getSender().getName() ;
					String[] sendername = send.split("@");
					switch (aclMessage.getPerformative()) {
					// recieve request from client and send cfp
					case ACLMessage.REQUEST:
						 String nbplaces=aclMessage.getContent();	
						 System.out.println("request recieved from " + sendername[0] );
						 ACLMessage CFPMessage=new ACLMessage(ACLMessage.CFP);
						 // add recievers
						 for (AID aid:listResto) {
							 CFPMessage.addReceiver(aid);
						 }
						 CFPMessage.setContent(nbplaces);
						 CFPMessage.setOntology("CFP");
						 send(CFPMessage);
						break;
						
					// recieve propose from restau
					case ACLMessage.PROPOSE:
						System.out.println("waiting for " + listResto.length +" restaurant proposition");
						int cap = Integer.parseInt( aclMessage.getContent() ) ;
						propos.put( sendername[0] , cap);
						System.out.println("propos recieved from " + sendername[0] + " --> "+cap);

						if (listResto.length == propos.size())
						{
							// choose best propos
							LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
							//Use Comparator.reverseOrder() for reverse ordering
							propos.entrySet()
							    .stream()
							    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
							    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
							 
							for (Map.Entry<String,Integer> entry : reverseSortedMap.entrySet())
							{
						            Set<String> keys = reverseSortedMap.keySet();
							        List<String> listKeys = new ArrayList<String>( keys );
							        String sender = entry.getKey();
									int capacit = entry.getValue();
							        int idx = listKeys.indexOf(entry.getKey()) ;
							        if(idx == 0) {
							        	System.out.println("best prop : " + capacit + " from " + sender);
							        	// send accept propos to sender
										ACLMessage apMessage=new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
										apMessage.addReceiver(new AID(sender,AID.ISLOCALNAME));
										apMessage.setContent(String.valueOf(capacit));
										apMessage.setOntology("accept");
										send(apMessage);
							        }
							        else {
							        	System.out.println("refuse prop : " + capacit + " from " + sender);
							        	// send reject propos to sender
										ACLMessage apMessage=new ACLMessage(ACLMessage.REJECT_PROPOSAL);
										apMessage.addReceiver(new AID(sender,AID.ISLOCALNAME));
										apMessage.setContent(String.valueOf(capacit));
										apMessage.setOntology("reject");
										send(apMessage);
							        }
							}

						}

						
						break;
					case ACLMessage.AGREE:
						System.out.println("recieve accept from " + sendername[0] );		
						ACLMessage informMessage=new ACLMessage(ACLMessage.INFORM);
						informMessage.addReceiver(new AID("ClientAgent",AID.ISLOCALNAME));
						informMessage.setContent(("rest : "+sendername[0] + " & nb places : "+aclMessage.getContent()));
						informMessage.setOntology("inform");
						send(informMessage); 
						
						break;
					default:
						break;
					}
					
					 aclMessage = null ;
					
				}
				else {
					block();
				}
				
			}
		});
		
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
