package com.mitsako1926.contactManager;

import javax.swing.SwingUtilities;

import com.mitsako1926.contactManager.gui.ContactManagerFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(ContactManagerFrame::new);
	}

}
