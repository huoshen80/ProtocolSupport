package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntitySound extends MiddleEntitySound {

	public EntitySound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entitysound = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_SOUND);
		VarNumberSerializer.writeVarInt(entitysound, id);
		MiscSerializer.writeVarIntEnum(entitysound, category);
		VarNumberSerializer.writeVarInt(entitysound, entityId);
		entitysound.writeFloat(volume);
		entitysound.writeFloat(pitch);
		codec.write(entitysound);
	}

}
