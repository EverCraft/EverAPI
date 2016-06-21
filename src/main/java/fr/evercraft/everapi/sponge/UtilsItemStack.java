package fr.evercraft.everapi.sponge;

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class UtilsItemStack {

	public static Text getName(final ItemStack item){
		if(item.get(Keys.SPAWNABLE_ENTITY_TYPE).isPresent()) {
			return Text.builder(item.getTranslation())
					.append(Text.of(" "))
					.append(Text.of(item.get(Keys.SPAWNABLE_ENTITY_TYPE).get().getTranslation()))
					.build();
		}
		return Text.of(item.getTranslation());
	}
	
	public static ItemStack repairInventory(final ItemStack stack) {
		Optional<Integer> data = stack.get(Keys.ITEM_DURABILITY);
		if(data.isPresent()) {
			stack.offer(Keys.ITEM_DURABILITY, Integer.MAX_VALUE);
		}
		return stack;
	}
	
	public static int getMaxDurability(final ItemStack stack) {
		Optional<Integer> data = ItemStack.of(stack.getItem(), 1).get(Keys.ITEM_DURABILITY);
		if(data.isPresent()) {
			return data.get();
		}
		return 0;
	}
}
