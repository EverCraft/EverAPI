package fr.evercraft.everapi.sponge;

import org.spongepowered.api.data.type.Art;
import org.spongepowered.api.data.type.Arts;

public enum UtilsPainting {
	ALBAN(Arts.ALBAN),
	AZTEC(Arts.AZTEC),
	AZTEC_2(Arts.AZTEC_2),
	BOMB(Arts.BOMB),
	BURNING_SKULL(Arts.BURNING_SKULL),
	BUST(Arts.BUST),
	COURBET(Arts.COURBET),
	CREEBET(Arts.CREEBET),
	DONKEY_KONG(Arts.DONKEY_KONG),
	FIGHTERS(Arts.FIGHTERS),
	GRAHAM(Arts.GRAHAM),
	KEBAB(Arts.KEBAB),
	MATCH(Arts.MATCH),
	PIGSCENE(Arts.PIGSCENE),
	PLANT(Arts.PLANT),
	POINTER(Arts.POINTER),
	POOL(Arts.POOL),
	SEA(Arts.SEA),
	SKELETON(Arts.SKELETON),
	SKULL_AND_ROSES(Arts.SKULL_AND_ROSES),
	STAGE(Arts.STAGE),
	SUNSET(Arts.SUNSET),
	VOID(Arts.VOID),
	WANDERER(Arts.WANDERER),
	WASTELAND(Arts.WASTELAND),
	WITHER(Arts.WITHER);
	
	private final Art art;
	
	UtilsPainting(final Art art) {
		this.art = art;
		
	}

	public Art getArt() {
		return this.art;
	}
	
	public int getNumero() {
		int cpt = 0;
		while(cpt < values().length && values()[cpt].equals(this.art)){
			cpt++;
		}
		return cpt;
	}
	
	public UtilsPainting next() {
		int numero = this.getNumero() + 1;
		if(values().length >= numero) {
			numero = 0;
		}
		return values()[numero];
	}
}
