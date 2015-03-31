package edu.asu.cse360.team25.client.patient;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.asu.cse360.team25.client.Client;
import edu.asu.cse360.team25.protocol.PatientInfo;
import edu.asu.cse360.team25.protocol.exception.ProtocolErrorException;

public class PatientClient extends Client {

	protected PatientMainFrame pmf;
	
	protected PatientServerConnection psc;
	
	// for login
	protected int patientID = -1;
	protected String password = null;
	protected boolean login;
	protected boolean loginAck = false;
	
	
	// for signup
	protected boolean signup;
	protected String usernameSU;
	protected String passwordSU;
	protected String genderSU;
	protected String heightSU;
	protected String weightSU;
	protected String birthdaySU;
	
	protected int signupID = -1;
	
	protected PatientInfo pi;
	
	public PatientClient() throws IOException {
		super();
		
		psc = new PatientServerConnection(this);
	}


	public void doLogin() throws IOException, ProtocolErrorException, InterruptedException {
		// Login
		
		do {
		
			PatientLoginDialog ld = new PatientLoginDialog(null, this, patientID, password);
			ld.setLocationRelativeTo(null);
			ld.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			ld.setVisible(true);
	
			if(!login || signup)
				break;
			
			psc.sendLogin(patientID, password);
			// wait for login ack
			
			synchronized(this) {
				wait();
			}
			
			if(!loginAck) {
				JOptionPane.showMessageDialog(null, "User ID and password does not match!",
						"Error", JOptionPane.ERROR_MESSAGE);				
			}

		
		} while(!loginAck);

	}
	
	public void doSignUp() throws IOException, InterruptedException {
		if(signup) {
			signup = false;
			
			PatientSignUpDialog sd = new PatientSignUpDialog(null, this);
			sd.setLocationRelativeTo(null);
			sd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			sd.setVisible(true);
			
			
			if(signup) {
				
				psc.sendRegister(passwordSU, usernameSU, genderSU, heightSU, weightSU, birthdaySU);
				
				synchronized(this) {
					wait();
				}
				
				JOptionPane.showMessageDialog(null,
					    "Register succeeded. Your ID is " + patientID + ".");
				
			} else {
				return;
			}
		}
	}
	
	public void start() throws InterruptedException, IOException, ProtocolErrorException {
		
		psc.startReceiving();
		
		
		doLogin();
		
		if(!login && !signup) {
			return;
		}
		
		while(signup) {
			doSignUp();
			if(signup) {
				doLogin();
			}
		}
		
		if(!login && !signup) {
			return;
		}
		
		
		
		// Query profile
		
		psc.sendQueryPatientProfile();
		synchronized(this) {
			wait();
		}
		
		
		
		pmf = new PatientMainFrame(pi, psc);
		pmf.setVisible(true);
		
		psc.setPatientMainFrame(pmf);
		
		System.out.println("Patient client start successfully~~~");
		
		psc.waitForReceivingThread();
		
		
	}

	public void stop() throws IOException {
		
		psc.disconnect();
	}
	
	public void setIDnPW(int patientID, String password, boolean login) {
		
		this.patientID = patientID;
		this.password = password;
		this.login = login;
	}

	public void setLoginAck(boolean loginAck) {
		this.loginAck = loginAck;
	}
	
	public void setPatientInfo(PatientInfo pi) {
		
		this.pi = pi;
	}
	
	
	public static void main(String[] argv) {
		
        /* Set the Aqua look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Aqua".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
//            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PatientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		try {

			
			PatientClient pc = new PatientClient();
			
			pc.start();

			
			
			pc.stop();
			
			pc.psc.waitForReceivingThread();

			
//			psc.sendQueryDoctor("*", "*");
//			
//			
//			psc.sendQueryPatientProfile();
//			
//			
//			psc.sendUpdatePatientProfile("Rabbit Lin", "Male", "160", "100", "X");
//			
//			
//			psc.sendLogout();
//			
//			
//			psc.disconnect();
//			
//			
//			psc.waitForReceivingThread();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
