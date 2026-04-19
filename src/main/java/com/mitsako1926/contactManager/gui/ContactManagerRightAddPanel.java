package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class ContactManagerRightAddPanel extends JPanel{
	
	private final JButton imageButton;
	
	public ContactManagerRightAddPanel() {
		setPreferredSize(new Dimension(250,500));		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		//USER IMAGE
		ImageIcon iconUser = new ImageIcon(getClass().getResource("/images/users/user.png"));
		Image imgUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.decode("#F7F9FC"));
		imagePanel.setPreferredSize(new Dimension(100,80));
		
		imageButton = new JButton(new ImageIcon(imgUser));
		imageButton.setFocusPainted(false);
		imageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		imageButton.setBorderPainted(false);
		imageButton.setContentAreaFilled(false);
		imageButton.setOpaque(false);
		imageButton.setBackground(new Color(240,240,240));
		
		imageButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	imageButton.setBackground(new Color(220,220,220));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	imageButton.setBackground(new Color(240,240,240));
		    }
		});
		
		imageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				press(e);
			}
			
		});
		
		imagePanel.add(imageButton, BorderLayout.CENTER);
		
		
		//ALL INFORMATION		
		
		JPanel infoPanel = new JPanel();
		infoPanel.setOpaque(true);
		infoPanel.setBackground(Color.decode("#F7F9FC"));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(createRow("First Name"));
		infoPanel.add(createRow("Last Name"));
		infoPanel.add(createRow("Phone"));
		infoPanel.add(createRow("Email"));
		infoPanel.add(createRow("Company"));
		infoPanel.add(createRow("Favorite"));
		infoPanel.add(createRow("Notes"));
		
		//NOTES AREA 
		
		JTextArea notesArea = new JTextArea(5, 20);
		notesArea.setLineWrap(true);
		notesArea.setText("");
		notesArea.setWrapStyleWord(true);
		notesArea.setFont(new Font("Arial", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane(notesArea);
		scrollPane.setPreferredSize(new Dimension(250,110));
		scrollPane.setBorder(
			    BorderFactory.createCompoundBorder(
			        BorderFactory.createMatteBorder(1, 0, 1, 1, Color.decode("#D6DEE8")),
			        BorderFactory.createEmptyBorder(10, 10, 10, 10)
			    )
			);
		
		//ADD EVERYTHING TO THE MAIN PANEL
		
		add(imagePanel,BorderLayout.NORTH);
		add(infoPanel,BorderLayout.CENTER);
		add(scrollPane,BorderLayout.SOUTH);
		
		
	}
	
	
	
	private void press(ActionEvent e) {
		if(e.getSource()==imageButton) {
			System.out.println(0);
		}
	}
	
	
	
	private JPanel createRow(String text) {
	    
		
		JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
	    row.setBackground(Color.decode("#F7F9FC"));

	    JLabel label = new JLabel(text + " :");
	    label.setOpaque(true);
	    label.setBackground(Color.decode("#F7F9FC"));
	    
	    if(text.equals("Notes")) {
	    	row.add(label);

		    return row;
	    }

	    JTextField field = new JTextField(15);

	    row.add(label);
	    row.add(field);

	    return row;
	}


	
	
}
