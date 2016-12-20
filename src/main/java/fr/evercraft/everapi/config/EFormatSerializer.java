package fr.evercraft.everapi.config;

import java.util.List;

import org.spongepowered.api.text.TextTemplate;

import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.format.EFormatListString;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.format.EFormatTemplate;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class EFormatSerializer implements TypeSerializer<EFormat> {

	@Override
	public EFormat deserialize(TypeToken<?> type, ConfigurationNode node) throws ObjectMappingException {
		if (node.getValue() instanceof List) {
			return new EFormatListString(node.getList(TypeToken.of(String.class)));
		} else if (node.getValue() instanceof String) {
			return new EFormatString(node.getString(""));
		} else {
			return new EFormatTemplate(node.getValue(TypeToken.of(TextTemplate.class)));
		}
	}

	@Override
	public void serialize(TypeToken<?> type, EFormat format, ConfigurationNode node) throws ObjectMappingException {
		if (format instanceof EFormatListString) {
			node.setValue(((EFormatListString) format).getMessage());
		} else if (format instanceof EFormatString) {
			node.setValue(((EFormatString) format).getMessage());
		} else if (format instanceof EFormatTemplate) {
			node.setValue(((EFormatTemplate) format).getMessage());
		} else {
			throw new ObjectMappingException();
		}
	}

}
