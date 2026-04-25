package com.mitsako1926.contactManager.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.mitsako1926.contactManager.model.Contact;


public final class DialogUtils {

	
	private DialogUtils() {}
	
	
	
	public static void optionPaneError(String text) {
		
		String message = "<html><body style='font-size:11px;'>"
                         + text + "</body></html>";

		ImageIcon originalIcon = new ImageIcon(DialogUtils.class.getResource("/images/icons/warning-red.png"));
	    Image img = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		
		JOptionPane optionPane = new JOptionPane(
		    message,
		    JOptionPane.PLAIN_MESSAGE,
		    JOptionPane.YES_NO_OPTION,
		    null,
		    new Object[]{"OK"}
		);
		
		JDialog dialog = optionPane.createDialog("Error");
		
		dialog.setIconImage(img);
		
		removeFocus(optionPane);

		dialogLocation(dialog);
		
		dialog.setVisible(true);
		
	}
	
	
	
	public static void optionPaneInvalid(String text) {
		
		String message = "<html><body style='font-size:11px;'>"
                		 + text + "</body></html>";

		ImageIcon originalIcon = new ImageIcon(DialogUtils.class.getResource("/images/icons/warning.png"));
	    Image img = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		
		JOptionPane optionPane = new JOptionPane(
		    message,
		    JOptionPane.PLAIN_MESSAGE,
		    JOptionPane.YES_NO_OPTION,
		    null,
		    new Object[]{"OK"}
		);
		
		JDialog dialog = optionPane.createDialog("Invalid Input");
		
		dialog.setIconImage(img);
		
		removeFocus(optionPane);

		dialogLocation(dialog);
		
		dialog.setVisible(true);
		
	}
    
    
	
    public static Object optionPaneDelete(Contact contact) {
		String fullName = contact.toString();
		
		String message = "<html><body style='font-size:12px;'>"
		        + "Are you sure you want to delete<br><b>" + fullName + "</b>?"
		        + "</body></html>";

		ImageIcon originalIcon = new ImageIcon(DialogUtils.class.getResource("/images/icons/bin.png"));
	    Image img = originalIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
		
		JOptionPane optionPane = new JOptionPane(
		    message,
		    JOptionPane.PLAIN_MESSAGE,
		    JOptionPane.YES_NO_OPTION,
		    null,
		    new Object[]{"Delete", "Cancel"},
		    "Cancel"
		);
		
		JDialog dialog = optionPane.createDialog("Delete Contact");
		dialog.setIconImage(img);
		
		removeFocus(optionPane);

		dialogLocation(dialog);
		
		dialog.setVisible(true);

		return optionPane.getValue();
		
	}
    
    
    
    private static void dialogLocation(JDialog dialog) {
		Frame contactManagerFrame = null;

		for (Frame frame : Frame.getFrames()) {
		    if (frame.isShowing() && frame instanceof ContactManagerFrame) {
		        contactManagerFrame = frame;
		        break;
		    }
		}
		
		if(contactManagerFrame!=null) dialog.setLocationRelativeTo(contactManagerFrame);
		else dialog.setLocationRelativeTo(null);
		
	}
    
    
    
    private static void removeFocus(Component comp) {
	    if (comp instanceof JButton btn) {
	        btn.setFocusable(false);
	    }

	    if (comp instanceof Container container) {
	        for (Component child : container.getComponents()) {
	            removeFocus(child);
	        }
	    }
	    
	}
    
    
    
    
}
