package fr.evercraft.everapi.sponge;

import java.util.Optional;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

public class UtilsInventory {

	public static void repair(final Inventory inventory) {
		for(Inventory stack : inventory.slots()){
			Optional<ItemStack> item = stack.poll();
			if(item.isPresent()) {
				stack.offer(UtilsItemStack.repairInventory(item.get()));
			}
		}
	}
}
