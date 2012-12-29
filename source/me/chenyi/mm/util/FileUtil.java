/**
 * @(#)FileUtil.java
 *
 * Copyright (2003) Bro3
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Boston, MA 02111.
 * 
 * Contact: bro3@users.sourceforge.net
 **/

package me.chenyi.mm.util;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

public class FileUtil {
    
	static Logger log = Logger.getLogger(FileUtil.class);
 
    public static StringBuffer readFileToStringBuffer(String filePath) throws FileNotFoundException, IOException {
        return readFileToStringBuffer(new File(filePath));
    }
    
    
    
    public static StringBuffer readFileToStringBuffer(File file) throws FileNotFoundException, IOException {

    	if (!file.isFile()) {
    		if (!FileUtil.getFile(file.toString()).isFile())
    			throw new FileNotFoundException("File does not exist:" + file);
 
   			file = FileUtil.getFile(file.toString());
    	}

    	StringBuffer buf = new StringBuffer();

    	BufferedReader reader = new BufferedReader(new FileReader(file));
    	String tmp;

    	while ((tmp = reader.readLine()) != null)
    		buf.append(tmp).append("\n");

    	reader.close();
    			
    	return buf;
    }
    
    
   
    public static ArrayList<String> readFileToArrayList(File file) throws FileNotFoundException, IOException {

    	if (!file.isFile()) {
    		if (!FileUtil.getFile(file.toString()).isFile())
    			throw new FileNotFoundException("File does not exist:" + file);
 
    		file = FileUtil.getFile(file.toString());
    	}
    	
    	if (file.isFile())
    		return readArrayList(new FileReader(file));
    	
    	return null;
    }

    	
    	
    public static ArrayList<String> readArrayList(Reader input) throws FileNotFoundException, IOException {
    	ArrayList<String> ret = new ArrayList<String>();
    	
    	BufferedReader reader = new BufferedReader(input);
    	String tmp;

    	while ((tmp = reader.readLine()) != null)
    		ret.add(tmp);

    	reader.close();
    			
    	return ret;
    }
    
    /**
     * Returns a resource as a Stream or null if not found.
     *
     * @param name A resource name.
     **/
    public static InputStream getResourceAsStream(String name) {
        return FileUtil.class.getResourceAsStream(name);
    }
    
    public static byte[] getResourceAsByteArray(File file) {
    	return getResourceAsByteArray(file.getAbsolutePath());
    }
    
    /**
     * Returns a resource in a byte[] or null if not found.
     *
     * @param name A resource name.
     **/
    public static byte[] getResourceAsByteArray(String name) {
        
        try {
            InputStream inputStream;
            
            if (new File(name).exists()) {
                inputStream = new FileInputStream(new File(name));
            }
            else {
                inputStream = FileUtil.class.getResourceAsStream(name);
            }
            
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(bufferedInputStream.available());
            
            int buffer;
            while ((buffer = bufferedInputStream.read()) != -1)
                byteStream.write(buffer);
            
            bufferedInputStream.close();
            byteStream.close();
            
            return byteStream.toByteArray();
            
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e); //$NON-NLS-1$
        }
        return null;
    }
    
    
    /**
     * Returns a file in a byte[] or null if not found.
     *
     * @param file A resource name.
     **/
    public static byte[] readFromFile(File file) {
        
        try {
            InputStream inputStream;
            
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
            else 
            	throw new FileNotFoundException();
                        
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream byteStream = readFromStream(bufferedInputStream);
            
            return byteStream.toByteArray();
            
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e); //$NON-NLS-1$
        }
        return null;
    }
    
    /**
     * Returns a file in a ByteArrayOutputStream or null if not found.
     *
     * @param inputStream BufferedInputStream
     **/
    public static ByteArrayOutputStream readFromStream(BufferedInputStream inputStream) {
        
        try {
        	ByteArrayOutputStream byteStream = new ByteArrayOutputStream(inputStream.available());
            
            int buffer;
            while ((buffer = inputStream.read()) != -1)
                byteStream.write(buffer);
            
            inputStream.close();
            byteStream.close();
            
            return byteStream;
            
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e); //$NON-NLS-1$
        }
        return null;
    }
    
    
    public static String getAsString(InputStream s) {

    	String str = "";

    	try {
    		InputStreamReader inpStrd = new InputStreamReader(s);
    		BufferedReader buffRd = new BufferedReader(inpStrd);
    		String line = null;

    		while((line = buffRd.readLine()) != null) {
    			str += line;
    		}

    		log.debug(line);  
    		buffRd.close();

    	} catch (Exception e) {
    		log.warn("Exception:" + e.getMessage(), e);
    	}
    	return str;	
    }
    
    
    public static File getFile(String fileName) {
        try {
            return new File(new URI(getFileURL(fileName).toString()));
        } catch (URISyntaxException e) {
            log.error(e);
        }
       return null;
    }
    
    public static URL getFileURL(String fileName) {
        try {
            File f;

            if (fileName.startsWith("/")) {
                f = new File(fileName);
            } else {
                f = new File(SysUtil.getUserDir() + fileName);
            }

            return f.toURI().toURL();
        } catch (Exception e) {
            log.error("Exception:" + e.getMessage(), e);
        }
        return null;
    }

    public static URL getImageURL(String imageName) {
        return FileUtil.class.getResource(imageName);
    }

    public static Image getImage(String imageName) {
        Image image = null;

        try {

            try {
                URL url = FileUtil.class.getResource(imageName);

                if (url != null)
                    image = Toolkit.getDefaultToolkit().getImage(url);
            } catch (Exception e) {
                log.error("Exception:" + e.getMessage()); //$NON-NLS-1$
            }

            if (image == null) {
                String path = "";

                if (!new File(imageName).exists()) {
                    path = System.getProperty("user.dir");
                }

                if (new File(path + imageName).exists()) {
                    image = Toolkit.getDefaultToolkit().getImage(path + imageName);
                }
            }
        } catch (Exception e) {
            log.error("Exception:" + e.getMessage(), e); //$NON-NLS-1$
        }

        return image;
    }

    public static Image getImageFromJar(String imageName) {
    	
    	Image image = null;
    	try {
			URL url = FileUtil.class.getResource(imageName);
			image = Toolkit.getDefaultToolkit().getImage(url);
			
			throw new Exception();
		}
		catch (Exception e) {
			log.error("Exception:" + e.getMessage()); //$NON-NLS-1$
		}
		return image;
    }
    
    
    public static String getPath(String fileName) {
        String path = ""; //$NON-NLS-1$
        try {
        	path = URLDecoder.decode(FileUtil.class.getResource(fileName).getPath(), "UTF-8"); //$NON-NLS-1$
        }
        catch (Exception e) {
            log.error("Exception:" + e.getMessage()); //$NON-NLS-1$
        }
        return path;
    }
    
    public static void writeToFile(File file, String dataString) {
    	writeToFile(file.getAbsolutePath(), dataString, null);
    }
    
    public static void writeToFile(String fileName, String dataString) {
    	StringBuffer data = new StringBuffer(dataString);
    	writeToFile(fileName, data, null);
    }
    
   
    public static void writeToFile(String fileName, String dataString, String encoding) {
    	StringBuffer data = new StringBuffer(dataString);
    	writeToFile(fileName, data, encoding);
    }
    
   
    public static void writeToFile(String fileName, StringBuffer data) {
    	writeToFile(fileName, data, null);
    }
    
    
    public static void writeToFile(String fileName, StringBuffer data, String encoding) {
        try {
        	File outputFile = new File(fileName);
        	
        	if (!outputFile.getParentFile().isDirectory()) {
        		if (!outputFile.getParentFile().mkdirs()) {
        			log.warn("Failed to create new file: " + outputFile);
        			return;
        		}
        	}
        	
        	Writer out; 
            FileOutputStream fileStream = new FileOutputStream(outputFile);
            
            if (encoding == null || encoding.equals("")) 
            	out = new BufferedWriter(new OutputStreamWriter(fileStream));
            else
            	out = new BufferedWriter(new OutputStreamWriter(fileStream, encoding));
                                    
            for (int u = 0; u < data.length(); u++)
            	out.write(data.charAt(u));
            
            out.close();
            
        } catch (Exception e) {
            log.error("Exception:"+ e.getMessage());
        }
    }
    
   
    public static boolean writeToFile(File original, File file) throws Exception {
    	return writeToFile(new FileInputStream(original), file);
    }
	
    public static boolean writeToFile(byte [] data, File file) {
    	return writeToFile(new ByteArrayInputStream(data), file);
    }
        
    public static boolean writeToFile(InputStream data, File file) {
    	
        try {
        	if (!file.getParentFile().isDirectory()) {
        		if (!file.getParentFile().mkdirs()) {
        			log.warn("Failed to create new file: " + file);
        			return false;
        		}
        	}
            
        	int bufferSize = 8192;
        	
        	BufferedInputStream inputStream = new BufferedInputStream(data);
        	FileOutputStream fileStream = new FileOutputStream(file);
        	
        	byte buffer [] = new byte[bufferSize];
        	BufferedOutputStream dest = new BufferedOutputStream(fileStream, bufferSize);
        	int count;
        	
        	while ((count = inputStream.read(buffer, 0, bufferSize)) != -1) {
        		dest.write(buffer, 0, count);
        	}	
        	
        	inputStream.close();
        	dest.close();
        	
        } catch (Exception e) {
            log.error("Exception:"+ e.getMessage());
            return false;
        }
        return true;
    }
    

    public static boolean unzip(File zipFile, File dir) {

    	boolean success = true;
    		
    	try {
    		ZipFile zip = new ZipFile(zipFile);
    		Enumeration<? extends ZipEntry> entries = zip.entries();
    			    		
    		while (entries.hasMoreElements()) {
    			ZipEntry entry = (ZipEntry) entries.nextElement();

    			if (entry.isDirectory()) {
    				new File(dir, entry.getName()).mkdirs();
    				continue;
    			}

   				writeToFile(zip.getInputStream(entry), new File(dir, entry.getName()));
    		}

    		zip.close();
    	} catch (IOException ioe) {
    		log.error("Exception:" + ioe.getMessage(), ioe);
    		ioe.printStackTrace();
    		success = false;
    	}

    	if (success)
    		log.debug("File " + zipFile.getName() + " unzipped successfully.");
    	else
    		log.debug("An error occured while unzipping file " + zipFile.getName());
    	
    	return success;
    }
    

    /**
     * Creates a copy the original file in the destionation directory which is deleted on program exit
     * @param original
     * @param destination
     * @return
     */
    public static File createTempCopy(File original, File destination) {

    	File tempFile = null;

    	try {

    		if (destination.isFile())
    			throw new Exception("Destinaion is not a directory!");
    		else if (!destination.isDirectory() && !destination.mkdirs())
    			throw new Exception("Failed to create temporary directory!");
    			
    		do {	    	
    			int rand = (int) (Math.random() * 10000000);
    			tempFile = new File(destination, "" + rand + ".temp");
    		} while (tempFile.exists());

    		FileInputStream input = new FileInputStream(original);
    		
    		writeToFile(input, tempFile);

    		tempFile.deleteOnExit();

    	} catch (Exception e) {
    		log.error("Exception:"+ e.getMessage());
    	}

    	return tempFile;
    }
    
    
    public static String getExtension(File file) {
    	if (file == null)
    		return null;
    	
    	return getExtension(file.getName());
    }
        
    public static String getExtension(String fileName) {
    	if (fileName != null && fileName.indexOf(".") != -1) {
    		
    		if (fileName.lastIndexOf(".") == fileName.length() - 1)
    			return null;
    		
    		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()); 
    	}
    	return null;
    }
    
    public static boolean setExecute(File f, boolean b) {
    	
    	try {
    		Process p = null;
    		String cmd = null;
    		
    		cmd = "chmod " + "u+x" + " " + f.getAbsolutePath();
    		p = Runtime.getRuntime().exec(cmd);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    		
    	return true;
    }


    public static void downloadToFile(URL url, File downloadFile) throws Exception{

        long startTime = System.currentTimeMillis();

        System.out.println(String.format("Begin download from %s...\n", url));

        url.openConnection();
        InputStream reader = url.openStream();

        /*
        * Setup a buffered file writer to write
        * out what we read from the website.
        */

        File parentFile = downloadFile.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdirs();

        FileOutputStream writer = new FileOutputStream(downloadFile);
        byte[] buffer = new byte[153600];
        int totalBytesRead = 0;
        int bytesRead = 0;

        System.out.println("Reading file 150KB blocks at a time.\n");

        while ((bytesRead = reader.read(buffer)) > 0)
        {
            writer.write(buffer, 0, bytesRead);
            buffer = new byte[153600];
            totalBytesRead += bytesRead;
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Done. " + (new Integer(totalBytesRead).toString())
                + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
        writer.close();
        reader.close();
    }
    
    public static void copyToDir(File file, File dir) throws Exception {
    	copyToDir(file, dir, null);
    }
   
    
    public static void copyToDir(File file, File dir, String fileName) throws Exception {
    	
    	if (!file.isFile())
    		throw new Exception("Source file is not a file!:" + file);
    	
    	if (!dir.isDirectory() && !dir.mkdirs())
    		throw new Exception("Failed to create directores:" + dir);
    	
    	if (fileName == null || "".equals(fileName))
    		fileName = file.getName();
    	
        try {	
            FileChannel srcChannel = new FileInputStream(file).getChannel();
            FileChannel dstChannel = new FileOutputStream(new File(dir, fileName)).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
            		
        } catch (IOException e) {
        	log.error("Exception:" + e.getMessage(), e);
        }
    }


    public static void copyToDir(String resourcePath, File dir, String fileName) throws Exception {
        InputStream stream = FileUtil.class.getResourceAsStream(resourcePath);

        if (!dir.isDirectory() && !dir.mkdirs())
            throw new Exception("Failed to create directores:" + dir);

        if (fileName == null || "".equals(fileName))
            throw new Exception("Failed to get file name:" + fileName);

        int readBytes;
        byte[] buffer = new byte[4096];
        try {
            OutputStream outputStream = new FileOutputStream(new File(dir, fileName));
            while ((readBytes = stream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readBytes);
            }
            outputStream.close();
            stream.close();
        } catch (IOException e) {
            log.error("Exception:" + e.getMessage(), e);
        }
    }
    
    /*
     * Calculate size of files in directory and subdirectories
     * If regex is not null, only directories that matches the regex will be included
     */
    public static long getDirectorySize(File file, String regex) {

    	long size = 0;

    	if (file.isFile())
    		return file.length();
    	else if (file.isDirectory()){
    		
    		if (regex != null && !Pattern.matches(regex, file.getName())) {
    			return 0;
    		}

    		File[]	files = file.listFiles();

    		for (int i = 0; i < files.length; i++) {

    			if (files[i].isFile()) {
    				size += files[i].length();
    			}
    			else if (files[i].isDirectory()) {
    				size += getDirectorySize(files[i], regex);
    			}
    		}
    	}
    	return size;
    }

    
    public static boolean deleteDirectoryStructure(File dir) {

    	if (dir.isDirectory()) {
    		
    		String[] children = dir.list();
    		
    		for (int i = 0; i < children.length; i++) {
    		
    			boolean success = deleteDirectoryStructure(new File(dir, children[i]));
    			
    			if (!success) {
    				return false;
    			}
    		}
    	}
    	return dir.delete();
    }

    
    public static boolean canWriteToProgramFiles() {
        try {
        	String programFiles = System.getenv("ProgramFiles");
        
        	if (programFiles == null) {
                programFiles = "C:\\Program Files";
            }
        	return canWriteToDir(new File(programFiles));
        	
        } catch (Exception e) {
			return false;
		}
    }
    
    
    public static boolean canWriteToDir(File dir) throws Exception {
        
    	try {
           
    		if (!dir.isDirectory()) {
    			throw new Exception("Input is not a valid directory:" + dir);
    		}
    		    		
            File temp = new File(dir, "xmm.test");
            
            if (temp.exists())
            	return temp.delete();
            
            if (temp.createNewFile()) {
                temp.delete();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (IOException e)
        {
            return false;
        }
    }
  
} 
