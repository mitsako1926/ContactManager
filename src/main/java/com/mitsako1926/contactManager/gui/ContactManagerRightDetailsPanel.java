package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public final class ContactManagerRightDetailsPanel extends JPanel{

	
	private final String[] labels = {"First Name : Panagiotis","Last Name : Dimitrakopoulos","Phone : 6965748392",
								     "Email : mitsako2006@gmail.com","Company : Razer","Favorite : yes","Notes :"
									};

	
	public ContactManagerRightDetailsPanel() {
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		//USER IMAGE
		ImageIcon iconUser = new ImageIcon(getClass().getResource("/images/users/user.png"));
		Image imgUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.CYAN);
		imagePanel.setPreferredSize(new Dimension(100,80));
		
		JLabel imageLabel = new JLabel(new ImageIcon(imgUser));
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		
		
		//ALL INFORMATION
		JPanel labelsPanel = new JPanel(new GridLayout(labels.length, 1, 5, 2));
		labelsPanel.setBackground(Color.white);
		labelsPanel.setOpaque(true);
		Arrays.stream(labels).map(JLabel::new).forEach(label -> {customizeLabel(label); labelsPanel.add(label);});
		
		
		//NOTES AREA 
		
		JTextArea notesArea = new JTextArea(5, 20);
		notesArea.setLineWrap(true);
		notesArea.setText("We have a meeting at 3 today after we get lunch and then we have sexyyyy");
		notesArea.setWrapStyleWord(true);
		notesArea.setFont(new Font("Arial", Font.PLAIN, 12));
		notesArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(notesArea);
		scrollPane.setPreferredSize(new Dimension(250,110));
		
		add(imagePanel,BorderLayout.NORTH);
		add(labelsPanel,BorderLayout.CENTER);
		add(scrollPane,BorderLayout.SOUTH);
		
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setPreferredSize(new Dimension(250,30));
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		label.setForeground(Color.black);
		label.setBackground(Color.red);
		label.setOpaque(true);
		if(label.getText().equals("Notes :")) {
			label.setHorizontalAlignment(JLabel.CENTER);
		}
	}
	
	
	
}
