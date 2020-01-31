
package restaurant;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;

import javax.swing.BoxLayout;




public class RestauGUI extends JFrame implements ActionListener   {
	Container container=getContentPane();
    JLabel restau=new JLabel("Restaurant GUI");
    JLabel Capacite = new JLabel("Capacite:");
    JButton btnPropose=new JButton("Propose");
    JTextField txtCapacite = new JTextField();
    JLabel txtMsg= new JLabel("");
    
    private AgentContainer agentContainer;
    private RestaurantAgent restauAgent;
   
	

	public RestauGUI(){
		
		try {
			Runtime runtime=Runtime.instance();
			Profile profile=new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST,"localhost");
			agentContainer=runtime.createAgentContainer(profile);
			
				} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	getContentPane().setForeground(SystemColor.control);
    	this.setTitle("Restaurant Gui");
    	this.setVisible(true);
    	this.setBounds(10,10,400,270);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
 
	public RestaurantAgent getRestauAgent() {
		return restauAgent;
	}


	public void setRestauAgent(RestaurantAgent restauAgent) {
		this.restauAgent = restauAgent;
	}
 
    public void showMessage(String msg ) {
    	txtMsg.setText(msg);	
	 }
    
    
  
    
    public String getCapacite() {
    	return this.txtCapacite.getText() ;
    }
    
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        restau.setFont(new Font("Tahoma", Font.PLAIN, 18));
        restau.setHorizontalAlignment(SwingConstants.CENTER);
        //Setting location and Size of each components using setBounds() method.
        restau.setBounds(121,11,150,37);
        btnPropose.setBounds(272,70,100,30);
        Capacite.setBounds(10, 70, 114, 30);
        txtCapacite.setBounds(112, 70, 138, 30);
        txtMsg.setForeground(Color.RED);
        txtMsg.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtMsg.setBounds(10, 128, 362, 30);
    }
    public void addComponentsToContainer() {
       //Adding each components to the Container
        container.add(restau);
        container.add(btnPropose);
        container.add(Capacite);
        container.add(txtCapacite);
        container.add(txtMsg);
    }

    public void addActionEvent() {
        btnPropose.addActionListener(this);
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {

		// TODO Auto-generated method stub
		if(arg0.getSource() == btnPropose) {
			String capacite = txtCapacite.getText();
			if(capacite.isEmpty()) {
				this.showMessage(" add capacite! ");
			}
			else {
            	GuiEvent gev = new GuiEvent(this , 1 );
            	gev.addParameter(capacite);
            	restauAgent.onGuiEvent(gev);    
				
			}
			
		}
		
		
	}
}
