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
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.mitsako1926.contactManager.service.ContactService;

public final class ContactManagerTopPanel extends JPanel{

	
	private final JTextField searchField = new JTextField();
		
	private final ImageIcon[] buttons = {new ImageIcon(getClass().getResource("/images/icons/edit.png")),
										 new ImageIcon(getClass().getResource("/images/icons/bin.png")),
										 new ImageIcon(getClass().getResource("/images/icons/star.png"))
										 };
	
	JMenuItem exportCsvItem, importCsvItem;
	
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
		
		
		
		//PANEL FOR FILE BUTTONS
		JPanel panelFileButtons = new JPanel(new BorderLayout());
		panelFileButtons.setPreferredSize(new Dimension(150,50));
		panelFileButtons.setOpaque(true);
		panelFileButtons.setBackground(Color.decode("#F7F9FC"));
		panelFileButtons.setBackground(Color.RED);
		
		JButton fileButton = new JButton(new ImageIcon(getClass().getResource("/images/icons/list.png")));
		customizeFileButton(fileButton);


		JPopupMenu fileMenu = new JPopupMenu();
		fileMenu.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#D6DEE8")));

		importCsvItem = new JMenuItem("Import from CSV");
		exportCsvItem = new JMenuItem("Export to CSV");

		fileMenu.add(importCsvItem);
		fileMenu.add(exportCsvItem);

		fileButton.addActionListener(e -> {
			fileMenu.show(fileButton, fileButton.getWidth(), 0);
		});
		
		importCsvItem.addActionListener(e -> press(e));
		exportCsvItem.addActionListener(e -> press(e));
		
		panelFileButtons.add(fileButton,BorderLayout.WEST);
		
		
		//ADD COMPONENTS TO THE MAIN PANEL
		add(panelFileButtons,BorderLayout.WEST);
		add(panelSearch,BorderLayout.CENTER);
		add(panelButtons,BorderLayout.EAST);
		
	}
	
	
	
	private void press(ActionEvent e) {
		
		//FILE BUTTONS
		if(e.getSource()==importCsvItem) {
			service.importContactsFromCSV();
		}else if(e.getSource()==exportCsvItem) {
			service.exportContactsToCSV();
		}
		
		//EDIT,DELETE,FAVORITE BUTTONS
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
	    
	    button.setIcon(new ImageIcon(img));

	    buttonsList.add(button);
	    
	}
	
	
	
	private void customizeFileButton(JButton button) {
		button.setPreferredSize(new Dimension(35, 28));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFont(new Font("Arial", Font.BOLD, 18));
		
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
		
		ImageIcon originalIcon = (ImageIcon) button.getIcon();
	    Image img = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    
	    button.setIcon(new ImageIcon(img));

	}

	
	
}
