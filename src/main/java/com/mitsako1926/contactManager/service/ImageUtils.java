package com.mitsako1926.contactManager.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;

import com.mitsako1926.contactManager.model.Contact;

public final class ImageUtils {


	private static final Path USER_IMAGES_DIR = Path.of(System.getProperty("user.home"), "ContactManager", "user-images");	
	
	
	private ImageUtils() {}
	

	
	public static ImageIcon helperLoadIcon(Contact contact, ImageIcon icon) {
    	String path = contact.getImagePath();

        if (path != null && !path.isBlank() && path.startsWith("/images")) {
            icon = new ImageIcon(ImageUtils.class.getResource(contact.getImagePath()));
        
        }else if (path != null && path.startsWith("user-images")) {
            String fileName = path.substring("user-images/".length());
            Path p = USER_IMAGES_DIR.resolve(fileName);

            if (Files.exists(p)) {
                icon = new ImageIcon(p.toString());
            } else {
                icon = new ImageIcon(ImageUtils.class.getResource("/images/users/user.png"));
            }
        }
        
        else {
            icon = new ImageIcon(ImageUtils.class.getResource("/images/users/user.png"));
        }
        
        return icon;
        
    }
	
	
	
	public static ImageIcon loadImage(String path) {

	    if (path.startsWith("/images/")) {
	        return new ImageIcon(ImageUtils.class.getResource(path));
	    }
	
	    if (path.startsWith("user-images/")) {
	    	String fileName = path.substring("user-images/".length());
	        Path p = USER_IMAGES_DIR.resolve(fileName);
	        return new ImageIcon(p.toString());
	    }
	
	    return null;
	    
	}
	
	
	
	public static String processImage(String fullPath) {
	    File selectedFile = new File(fullPath);

	    String builtIn = checkBuiltIn(selectedFile);
	    
	    if (builtIn != null) {
	        return builtIn;
	    }

	    try {
	        Files.createDirectories(USER_IMAGES_DIR);

	        byte[] bytes = Files.readAllBytes(selectedFile.toPath());
	        
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        
	        byte[] hashBytes = md.digest(bytes);

	        StringBuilder sb = new StringBuilder();
	        
	        for (byte b : hashBytes) {
	            sb.append(String.format("%02x", b));
	        }

	        String extension = getExtension(selectedFile.getName());
	        String newName = sb.toString() + extension;

	        Path target = USER_IMAGES_DIR.resolve(newName);

	        if (!Files.exists(target)) {
	            Files.copy(selectedFile.toPath(), target);
	        }

	        return "user-images/" + newName;

	    } catch (IOException | NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	    
	}
	
	
	
	private static String checkBuiltIn(File file) {
	    try {
	        String selected = file.getCanonicalPath();

	        String[][] builtIns = {
	            {"/images/users/woman1.png", "src/main/resources/images/users/woman1.png"},
	            {"/images/users/woman2.png", "src/main/resources/images/users/woman2.png"},
	            {"/images/users/man1.png", "src/main/resources/images/users/man1.png"},
	            {"/images/users/man2.png", "src/main/resources/images/users/man2.png"},
	            {"/images/users/man3.png", "src/main/resources/images/users/man3.png"},
	            {"/images/users/user.png", "src/main/resources/images/users/user.png"}
	        };

	        for (String[] entry : builtIns) {
	            File f = new File(entry[1]);
	            
	            if (f.exists() && f.getCanonicalPath().equals(selected)) {
	                return entry[0];
	            }
	            
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return null;
	    
	}
	
	
	
	private static String getExtension(String name) {
	    int i = name.lastIndexOf('.');
	    return i > 0 ? name.substring(i) : "";
	    
	}
	
	

	
	
	
}
