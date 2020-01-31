package tourisDep;

import client.ClientAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class TourisDepContainer {
	
	public static void main(String[] args){
        try{
        	Runtime runtime=Runtime.instance();
    		Profile profile=new ProfileImpl(false);
    		profile.setParameter(Profile.MAIN_HOST,"localhost");
    		AgentContainer agentContainer=runtime.createAgentContainer(profile);
    		AgentController agentController=agentContainer.createNewAgent("TourisAgent",TourisAgent.class.getName(),new Object[]{});
    		agentController.start();
        }
        
        catch(Exception e){
        	e.printStackTrace(); 
            
        }
    }


}
