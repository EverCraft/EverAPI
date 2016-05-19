package fr.evercraft.everapi.mojang.profiles;

public interface ProfileRepository {
    public Profile[] findProfilesByNames(String... names);
}
