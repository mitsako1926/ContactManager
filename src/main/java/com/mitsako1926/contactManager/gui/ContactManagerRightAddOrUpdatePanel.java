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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mitsako1926.contactManager.model.Contact;
import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerRightAddOrUpdatePanel extends JPanel{
	
	
	private final JButton imageButton, addOrUpdateButton;
	
	private final JTextArea notesArea;

	private ContactService service;
	
	private ImageIcon iconAddUser,iconUpdateUser,iconAdd;
	
	private Image imgAddUser,imgUpdateUser,imgAdd;

	private final List<JTextField> textFieldList = new ArrayList<JTextField>();
	
	
	
	public ContactManagerRightAddOrUpdatePanel() {
		setPreferredSize(new Dimension(250,500));		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		
		
		//UPDATE USER IMAGE
		iconUpdateUser = new ImageIcon(getClass().getResource("/images/icons/update.png"));
		imgUpdateUser = iconUpdateUser.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		
		
		
		//ADD USER ICON IMAGE
		iconAddUser = new ImageIcon(getClass().getResource("/images/icons/add-user-icon.png"));
		imgAddUser = iconAddUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		imagePanel.setOpaque(true);
		imagePanel.setBackground(Color.decode("#F7F9FC"));
		imagePanel.setPreferredSize(new Dimension(100,80));
		
		
		imageButton = new JButton(new ImageIcon(imgAddUser));
		customizeImageButton(imageButton);
		
		
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
			
		
		notesArea = new JTextArea(5, 20);
		customizeTextArea(notesArea);
		
		
		JScrollPane scrollPane = new JScrollPane(notesArea);
		scrollPane.setPreferredSize(new Dimension(250,110));
		scrollPane.setBorder(
			    BorderFactory.createCompoundBorder(
			        BorderFactory.createMatteBorder(1, 0, 1, 1, Color.decode("#D6DEE8")),
			        BorderFactory.createEmptyBorder(10, 10, 10, 10)
			    )
			);
		
		
		infoPanel.add(scrollPane);
		
		
		
		//PANEL ADD/UPDATE BUTTON
		JPanel panelAdd = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelAdd.setPreferredSize(new Dimension(250,50));
		panelAdd.setBackground(Color.decode("#F7F9FC"));
		
		
		iconAdd = new ImageIcon(getClass().getResource("/images/icons/add.png"));
		imgAdd = iconAdd.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		
		
		addOrUpdateButton = new JButton("Add",new ImageIcon(imgAdd));
		customizeAddButton(addOrUpdateButton);
		
		
		panelAdd.add(addOrUpdateButton);
		
		
		
		//ADD EVERYTHING TO THE MAIN PANEL
		add(imagePanel,BorderLayout.NORTH);
		add(infoPanel,BorderLayout.CENTER);
		add(panelAdd,BorderLayout.SOUTH);
		
		
	}
	
	
	
	public void setService(ContactService service) {
		this.service = service;
	}
	
	
	
	private void press(ActionEvent e) {
		if(e.getSource()==imageButton) {
			ImageIcon iconUser = service.setUserIcon();
			
			if(iconUser!=null) {
				Image imgUser = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				
				imageButton.setIcon(new ImageIcon(imgUser));
			}
			
		}else if(e.getSource()==addOrUpdateButton) {
			service.addOrUpdateContactToDB(new ArrayList<JTextField>(textFieldList), notesArea.getText(), addOrUpdateButton.getText());
		}
		
	}
	
	
	
	public void setDefaultState() {
		imageButton.setIcon(new ImageIcon(imgAddUser));
		
		textFieldList.forEach((tf)->tf.setText(""));
		
		notesArea.setText("");
		
		addOrUpdateButton.setText("Add");
		addOrUpdateButton.setIcon(new ImageIcon(imgAdd));
		
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

	    textFieldList.add(field);
	    
	    row.add(label);
	    row.add(field);

	    return row;
	    
	}

	
	
	private void customizeTextArea(JTextArea text) {
		text.setLineWrap(true);
		text.setText("");
		text.setWrapStyleWord(true);
		text.setFont(new Font("Arial", Font.BOLD, 12));
	}
	
	
	
	private void customizeAddButton(JButton button) {
		button.setHorizontalAlignment(JButton.LEFT);
		button.setHorizontalTextPosition(JButton.RIGHT);
		button.setIconTextGap(10);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(80,35));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		button.setBorder(
			    BorderFactory.createCompoundBorder(
			        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#D6DEE8")),
			        BorderFactory.createEmptyBorder(0, 10, 0, 0)
			    )
			);
		
		button.setBorderPainted(true);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(240,240,240));
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				press(e);
			}

			
			
		});
		
		button.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	button.setBackground(new Color(220,220,220));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	button.setBackground(new Color(240,240,240));
		    }
		});
		
	}
	
	
	
	private void customizeImageButton(JButton button) {
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBackground(new Color(240,240,240));
		
		button.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	button.setBackground(new Color(220,220,220));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	button.setBackground(new Color(240,240,240));
		    }
		});
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				press(e);
			}
			
		});
		
	}

	
	
	public void setContactState(Contact contact) {
		ImageIcon iconUser = null;
		
		iconUser = new ContactListRenderer(1).helperLoadIcon(contact, iconUser);

        Image scaled = iconUser.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		
		imageButton.setIcon(new ImageIcon(scaled));
		
		textFieldList.get(0).setText(contact.getFirstName());
		textFieldList.get(1).setText(contact.getLastName());
		textFieldList.get(2).setText(contact.getPhone());
		textFieldList.get(3).setText(contact.getEmail());
		textFieldList.get(4).setText(contact.getCompany());
		textFieldList.get(5).setText(String.valueOf(contact.isFavorite()));
		
		notesArea.setText(contact.getNotes());
		
		addOrUpdateButton.setText("Update");
		addOrUpdateButton.setIcon(new ImageIcon(imgUpdateUser));
		
	}
	
	
	
	
}
