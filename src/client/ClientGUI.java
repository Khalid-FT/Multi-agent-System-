
package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import jade.gui.GuiEvent;

import javax.swing.BoxLayout;

//import client.AgentClient;


public class ClientGUI extends JFrame implements ActionListener   {
	Container container=getContentPane();
    JLabel client=new JLabel("Client GUI");
    JLabel nbplaces=new JLabel("nb places:");
    JTextField txtNbPlaces=new JTextField();
    JButton btnReserver=new JButton("Reserver");
    JLabel txtMsg = new JLabel("");
    
    
    
    private ClientAgent clientAgent;

	public ClientGUI(){
    	getContentPane().setForeground(SystemColor.control);
    	
    	this.setTitle("Client Gui");
    	this.setVisible(true);
    	this.setBounds(10,10,400,200);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	
    	/*btnReserver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	String nbplaces = txtNbPlaces.getText() ;
            	GuiEvent gev = new GuiEvent(this , 1 );
            	gev.addParameter(nbplaces);
            	clientAgent.onGuiEvent(gev);
            	System.out.println("button clicked!");
            }
        	
        });
    	*/
    	setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
 
	public ClientAgent getClientAgent() {
		return clientAgent;
	}

	public void setClientAgent(ClientAgent clientAgent) {
		this.clientAgent = clientAgent;
	}
	
    public void showMessage(String msg ) {
		txtMsg.setText(msg);
	 }
    
    public String getNBplaces() {
    	return this.txtNbPlaces.getText() ;
    }
    
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        client.setFont(new Font("Tahoma", Font.PLAIN, 18));
        client.setHorizontalAlignment(SwingConstants.CENTER);
        //Setting location and Size of each components using setBounds() method.
        client.setBounds(136,11,114,37);
        nbplaces.setBounds(10,70,114,30);
        txtNbPlaces.setBounds(112,70,130,30);
        btnReserver.setBounds(252,70,100,30);
        txtMsg.setBounds(20, 119, 360, 30);
        
        txtMsg.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtMsg.setForeground(Color.RED);
      
    }
    public void addComponentsToContainer()
    {
       //Adding each components to the Container
        container.add(client);
        container.add(nbplaces);
        container.add(txtNbPlaces);
        container.add(btnReserver);
        container.add(txtMsg);
       
    }
    
   
    public void addActionEvent() {
        btnReserver.addActionListener(this);
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {

		// TODO Auto-generated method stub
		if(arg0.getSource() == btnReserver) {
			String nbplaces = txtNbPlaces.getText();
			if(nbplaces.isEmpty()) {
				this.showMessage(" add nb placess ");
			}
			else {
				
            	GuiEvent gev = new GuiEvent(this , 1 );
            	gev.addParameter(nbplaces);
            	clientAgent.onGuiEvent(gev);    
				
			}
			
		}
		
		
	}
	
	
}
