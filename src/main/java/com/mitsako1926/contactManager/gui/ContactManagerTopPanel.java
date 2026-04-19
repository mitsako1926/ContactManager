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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class ContactManagerTopPanel extends JPanel{

	private final JPanel panelSearch = new JPanel();
	private final JPanel panelButtons = new JPanel();
	
	private final JTextField searchField = new JTextField();
	
	private final JLabel label = new JLabel();
	
	private final ImageIcon[] buttons = {new ImageIcon(getClass().getResource("/images/icons/edit.png")),
										 new ImageIcon(getClass().getResource("/images/icons/bin.png")),
										 new ImageIcon(getClass().getResource("/images/icons/star.png"))
										 };
	
	private final List<JButton> buttonsList = new ArrayList<JButton>();
	
	public ContactManagerTopPanel() {
		
		setPreferredSize(new Dimension(700,50));
		setLayout(new BorderLayout());
		
		//PANEL SEARCH
		panelSearch.setBackground(Color.PINK);
		panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 7));
		
		customizeTextField(searchField);
		panelSearch.add(searchField);
		
		//PANEL BUTTONS
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		panelButtons.setPreferredSize(new Dimension(250,50));
		panelButtons.setBackground(Color.RED);
		
		Arrays.stream(buttons).map(JButton::new).forEach(button -> {customizeButton(button); panelButtons.add(button);});
		
		//LABEL
		label.setPreferredSize(new Dimension(150,50));
		label.setOpaque(true);
		label.setBackground(Color.CYAN);
		
		//ADD COMPONENTS TO THE MAIN PANEL
		add(label,BorderLayout.WEST);
		add(panelSearch,BorderLayout.CENTER);
		add(panelButtons,BorderLayout.EAST);
	}
	
	
	
	private void customizeTextField(JTextField textField) {
		textField.setPreferredSize(new Dimension(200,35));
		textField.setText("Search...");
		textField.setFont(new Font("Arial",Font.ITALIC,12));
		textField.setForeground(Color.DARK_GRAY);
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
	    
	    ImageIcon originalIcon = (ImageIcon) button.getIcon();
	    Image img = originalIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
	    ImageIcon scaledIcon = new ImageIcon(img);

	    button.setIcon(scaledIcon);
	    

	    button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            press(e);
	        }
	    });

	    buttonsList.add(button);
	}
	
	
	
	private void press(ActionEvent e) {
		
		if(e.getSource()==buttonsList.get(0)) {
			System.out.println(0);
		}else if(e.getSource()==buttonsList.get(1)) {
			System.out.println(1);
		}else if(e.getSource()==buttonsList.get(2)){
			System.out.println(2);
		}
	}
	
	
	
}
