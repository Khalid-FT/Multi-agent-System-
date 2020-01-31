package restaurant;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class RestaurantContainer {
	
	 public static void main(String[] args){
	        try{
	            Runtime runtime = Runtime.instance();
	            ProfileImpl profileImpl = new ProfileImpl(false);
	            profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
	            AgentContainer agentContainer = runtime.createAgentContainer(profileImpl); 
	            
	            Object[] o1 = new Object[1];
	            o1[0]=1;
	            
	            Object[] o2 = new Object[1];
	            o2[0]=2;
	            
	            Object[] o3 = new Object[1];
	            o3[0]=3;
	            
	            Object[] o4 = new Object[1];
	            o4[0]=5;
	            
	            AgentController agentController1 = agentContainer.createNewAgent("Restaurant 1",RestaurantAgent.class.getName(), o1);
	            agentController1.start();
	            AgentController agentController2 = agentContainer.createNewAgent("Restaurant 2",RestaurantAgent.class.getName(), o2);
	            agentController2.start();
	           /* AgentController agentController3 = agentContainer.createNewAgent("Restaurant 3",RestaurantAgent.class.getName(), o3);
	            agentController3.start();
	            AgentController agentController4 = agentContainer.createNewAgent("Restaurant 4",RestaurantAgent.class.getName(), o4);
	            agentController4.start();
	            */
	            
	            
	        }
	        catch(Exception e){
	            
	        }
	    }
	    
}
