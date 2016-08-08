package fr.evercraft.everapi.services.signs;

import java.util.Set;

public interface SignService {
	public boolean add(SignSubject sign);
	public boolean remove(SignSubject sign);
	
	public Set<SignSubject> getAll();
}
