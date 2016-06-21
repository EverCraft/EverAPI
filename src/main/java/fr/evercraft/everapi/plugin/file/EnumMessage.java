package fr.evercraft.everapi.plugin.file;

import java.util.List;

public interface EnumMessage{
	
	public String getName();

	public String getPath();
	
	public Object getFrench();

	public Object getEnglish();
	
	public String get();
	
	public List<String> getList();

	public void set(Object string);
}
