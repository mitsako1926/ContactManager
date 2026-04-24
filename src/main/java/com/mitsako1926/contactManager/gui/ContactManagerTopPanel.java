package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerTopPanel extends JPanel{

	
	private final JTextField searchField = new JTextField();
		
	private final ImageIcon[] buttons = {new ImageIcon(getClass().getResource("/images/icons/edit.png")),
										 new ImageIcon(getClass().getResource("/images/icons/bin.png")),
										 new ImageIcon(getClass().getResource("/images/icons/star.png"))
										 };
	
	private final List<JButton> buttonsList = new ArrayList<JButton>();
	
	private final ContactService service;
	
	
	
	public ContactManagerTopPanel(ContactService service) {
		
		setPreferredSize(new Dimension(700,50));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#D6DEE8")));
		
		
		this.service = service;
		
		
		
		//PANEL SEARCH
		JPanel panelSearch = new JPanel();
		panelSearch.setBackground(Color.decode("#F7F9FC"));
		panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 7));
		
		customizeTextField(searchField);
		panelSearch.add(searchField);
		
		
		
		//PANEL BUTTONS
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER,15,5));
		panelButtons.setPreferredSize(new Dimension(250,50));
		panelButtons.setBackground(Color.decode("#F7F9FC"));
		
		
		Arrays.stream(buttons).map(JButton::new).forEach(button -> {customizeButton(button); panelButtons.add(button);});
		
		
		
		//LABEL
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(150,50));
		label.setOpaque(true);
		label.setBackground(Color.decode("#F7F9FC"));
		
		
		
		//ADD COMPONENTS TO THE MAIN PANEL
		add(label,BorderLayout.WEST);
		add(panelSearch,BorderLayout.CENTER);
		add(panelButtons,BorderLayout.EAST);
		
	}
	
	
	
	private void press(ActionEvent e) {
		
		if(e.getSource()==buttonsList.get(0)) {
			service.updateContact();
		}else if(e.getSource()==buttonsList.get(1)) {
			service.deleteContact();
		}else if(e.getSource()==buttonsList.get(2)){
			service.setFavorite();
		}
		
	}
	
	
	
	private void customizeTextField(JTextField textField) {
		textField.setPreferredSize(new Dimension(200,35));
		textField.setText("Search...");
		textField.setFont(new Font("Arial",Font.ITALIC,12));
		textField.setForeground(Color.DARK_GRAY);
		
		textField.addFocusListener(new java.awt.event.FocusAdapter() {
		   
			@Override
		    public void focusGained(java.awt.event.FocusEvent e) {
		        if (textField.getText().equals("Search...")) {
		        	textField.setText("");
		        	textField.setFont(new Font("Arial", Font.PLAIN, 12));
		        	textField.setForeground(Color.BLACK);
		        }
		    }

		    @Override
		    public void focusLost(java.awt.event.FocusEvent e) {
		        if (textField.getText().isEmpty()) {
		        	textField.setText("Search...");
		        	textField.setFont(new Font("Arial", Font.ITALIC, 12));
		        	textField.setForeground(Color.DARK_GRAY);
		        }
		    }
		    
		});
		
		textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

		    @Override
		    public void insertUpdate(javax.swing.event.DocumentEvent e) {
		        onSearchChanged();
		    }

		    @Override
		    public void removeUpdate(javax.swing.event.DocumentEvent e) {
		        onSearchChanged();
		    }

		    @Override
		    public void changedUpdate(javax.swing.event.DocumentEvent e) {
		        onSearchChanged();
		    }
		});
		
		
	}
	
	
	
	private void onSearchChanged() {
	    String text = searchField.getText();
	    
	    if(text.equals("Search...")) return;
	    	
	    service.searchContacts(text);
	    
	}
	
	
	
	private void customizeButton(JButton button) {
	    button.setPreferredSize(new Dimension(40, 40));
	    button.setFocusable(false);
	    button.setBorderPainted(false);
	    button.setContentAreaFilled(false);
	    button.setFocusPainted(false);
	    button.setOpaque(false);
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    button.setBorderPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(240,240,240));
	    
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            press(e);
	        }
	    });
	    
		ImageIcon originalIcon = (ImageIcon) button.getIcon();
	    Image img = originalIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(img);

	    
	    button.setIcon(scaledIcon);

	    buttonsList.add(button);
	    
	}
	
	

	
	
}
