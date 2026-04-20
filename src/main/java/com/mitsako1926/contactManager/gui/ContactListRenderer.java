package com.mitsako1926.contactManager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
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

    
    
    private ImageIcon loadIcon(Contact contact) {
        ImageIcon icon;

        
        String path = contact.getImagePath();

        if (path != null && !path.isBlank() && path.startsWith("/images")) {
            icon = new ImageIcon(getClass().getResource(contact.getImagePath()));
        
        }else if (path.startsWith("user-images")) {
            Path p = Path.of("user-images/").resolve(path.replace("user-images/", ""));
            icon = new ImageIcon(p.toString());
        } 
        
        else {
            icon = new ImageIcon(getClass().getResource("/images/users/user.png"));
        }

        Image scaled = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        
        return new ImageIcon(scaled);
    }
    
    
    
}
