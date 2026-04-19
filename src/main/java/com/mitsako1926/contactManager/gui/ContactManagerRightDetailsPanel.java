package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mitsako1926.contactManager.model.Contact;

public final class ContactManagerRightDetailsPanel extends JPanel{

	
	private final String[] labels = {" First Name : "," Last Name : "," Phone : ",
								     " Email : "," Company : "," Favorite : "," Notes :"
									};

	JLabel imageLabel;
	JPanel imagePanel;
	
	private Contact contact;
	
	private final List<JLabel> labelList = new ArrayList<JLabel>();
	
	private final JTextArea notesArea;
	
	public ContactManagerRightDetailsPanel() {
		setPreferredSize(new Dimension(250,500));
		setOpaque(true);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
		
		//USER IMAGE
		ImageIcon iconUser = new ImageIcon(getClass().getResource("/images/users/user.png"));
		Image imgUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#D6DEE8")));
		imagePanel.setLayout(new BorderLayout());
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.decode("#F7F9FC"));
		imagePanel.setPreferredSize(new Dimension(100,80));
		
		imageLabel = new JLabel(new ImageIcon(imgUser));
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		
		
		//ALL INFORMATION
		JPanel labelsPanel = new JPanel(new GridLayout(labels.length, 1, 5, 2));
		labelsPanel.setBackground(Color.decode("#F7F9FC"));
		labelsPanel.setOpaque(true);
		Arrays.stream(labels).map(JLabel::new).forEach(label -> {customizeLabel(label); labelsPanel.add(label);});
		
		
		//NOTES AREA 
		
		notesArea = new JTextArea(5, 20);
		notesArea.setLineWrap(true);
		notesArea.setText("");
		notesArea.setWrapStyleWord(true);
		notesArea.setFont(new Font("Arial", Font.BOLD, 12));
		notesArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(notesArea);
		scrollPane.setPreferredSize(new Dimension(250,110));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(imagePanel,BorderLayout.NORTH);
		add(labelsPanel,BorderLayout.CENTER);
		add(scrollPane,BorderLayout.SOUTH);
		
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setPreferredSize(new Dimension(250,30));
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(Color.DARK_GRAY);
		label.setBackground(Color.decode("#F7F9FC"));
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode("#D6DEE8")));
		label.setOpaque(true);
		if(label.getText().equals("Notes :")) {
			label.setHorizontalAlignment(JLabel.CENTER);
		}
		
		labelList.add(label);
	}
	
	
	
	public void setContact(Contact contact) {
		this.contact = contact;
		
		ImageIcon iconUser;
		
		if(contact==null)return;
		
		if (contact.getImagePath() != null && !contact.getImagePath().isBlank()) {
			iconUser = new ImageIcon(getClass().getResource(contact.getImagePath()));
        } else {
        	iconUser = new ImageIcon(getClass().getResource("/images/users/user.png"));
        }
		
		Image imgUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		imageLabel.setIcon(new ImageIcon(imgUser));
		
		labelList.get(0).setText(labels[0] + contact.getFirstName());
		labelList.get(1).setText(labels[1] + contact.getLastName());
		labelList.get(2).setText(labels[2] + contact.getPhone());
		labelList.get(3).setText(labels[3] + contact.getEmail());
		labelList.get(4).setText(labels[4] + contact.getCompany());
		labelList.get(5).setText(labels[5] + contact.isFavorite());
		notesArea.setText(contact.getNotes());
		
	}
	
	
	
	public Contact getContact() {
		return contact;
	}
	
}
