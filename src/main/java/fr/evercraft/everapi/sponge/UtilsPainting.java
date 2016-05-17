package fr.evercraft.everapi.sponge;

import java.util.Optional;

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
	
	public int getNumero() {
		int cpt = 0;
		while(cpt < values().length && !values()[cpt].getArt().equals(this.art)){
			cpt++;
		}
		return cpt;
	}
	
	public UtilsPainting next() {
		int numero = this.getNumero() + 1;
		if(numero >= values().length) {
			numero = 0;
		}
		return values()[numero];
	}
	
	public Art getArt(){
		return this.art;
	}
	
	public static Optional<UtilsPainting> get(final Art art) {
		UtilsPainting paint = null;
		int cpt = 0;
		while(cpt < values().length && paint == null){
			if (values()[cpt]. getArt().equals(art)){
				paint = values()[cpt];
			}
			cpt++;
		}
		return Optional.ofNullable(paint);
	}
}
