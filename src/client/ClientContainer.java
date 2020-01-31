package client;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class ClientContainer  {
	
	 public static void main(String[] args){
	        try{
	        	Runtime runtime=Runtime.instance();
	    		Profile profile=new ProfileImpl(false);
	    		profile.setParameter(Profile.MAIN_HOST,"localhost");
	    		AgentContainer agentContainer=runtime.createAgentContainer(profile);
	    		AgentController agentController=agentContainer.createNewAgent("ClientAgent",ClientAgent.class.getName(),new Object[]{});
	    		agentController.start();
	        }
	        
	        catch(Exception e){
	        	e.printStackTrace(); 
	            
	        }
	    }

}
