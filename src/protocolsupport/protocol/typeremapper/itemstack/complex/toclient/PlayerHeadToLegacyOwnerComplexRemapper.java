package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;

public class PlayerHeadToLegacyOwnerComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		remap(tag, CommonNBT.PLAYERHEAD_ITEM_PROFILE, "SkullOwner");
		return tag;
	}

	public static void remap(NBTCompound tag, String tagname, String newtagname) {
		NBTCompound gameprofileTag = tag.getTagOfType(tagname, NBTType.COMPOUND);
		if (gameprofileTag != null) {
			NBTString nameTag = gameprofileTag.getTagOfType(GameProfileSerializer.NAME_KEY, NBTType.STRING);
			if (nameTag != null) {
				tag.setTag(newtagname, nameTag);
			} else {
				tag.removeTag(tagname);
			}
		}
	}

}
