package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.BorderFactory;

import com.mitsako1926.contactManager.model.Contact;

public final class ContactListRenderer extends JPanel implements ListCellRenderer<Contact> {

    private final JLabel iconLabel = new JLabel();
    private final JLabel nameLabel = new JLabel();

    
    public ContactListRenderer() {
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(iconLabel, BorderLayout.WEST);
        add(nameLabel, BorderLayout.CENTER);
    }
    
    

    @Override
    public Component getListCellRendererComponent(JList<? extends Contact> list, Contact contact, int index, boolean isSelected, boolean cellHasFocus) {

        nameLabel.setText(contact.toString());

        ImageIcon icon = loadIcon(contact);
        iconLabel.setIcon(icon);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            nameLabel.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            nameLabel.setForeground(list.getForeground());
        }

        setOpaque(true);
        
        return this;
    }

    
    
    private final Path USER_IMAGES_DIR = Path.of(System.getProperty("user.home"), "ContactManager", "user-images");
    
    
    
    private ImageIcon loadIcon(Contact contact) {
        ImageIcon icon;

        
        String path = contact.getImagePath();

        if (path != null && !path.isBlank() && path.startsWith("/images")) {
            icon = new ImageIcon(getClass().getResource(contact.getImagePath()));
        
        }else if (path != null && path.startsWith("user-images")) {
            String fileName = path.substring("user-images/".length());
            Path p = USER_IMAGES_DIR.resolve(fileName);
            icon = new ImageIcon(p.toString());
        }
        
        else {
            icon = new ImageIcon(getClass().getResource("/images/users/user.png"));
        }

        Image scaled = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        
        
        return new ImageIcon(scaled);
    }
    
    
   
    
    
}
