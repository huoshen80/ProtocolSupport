package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.SlotDoesntExistException;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventoryClick extends MiddleInventoryClick {

	public InventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		try {
			windowId = clientdata.readByte();
			slot = windowCache.getOpenedWindowRemapper().fromClientSlot(windowId, clientdata.readShort());
			button = clientdata.readUnsignedByte();
			actionNumber = clientdata.readShort();
			mode = clientdata.readUnsignedByte();
			itemstack = ItemStackSerializer.readItemStack(clientdata, version);
			if ((button == 0) && (mode == MODE_SHIFT_CLICK)) {
				itemstack = NetworkItemStack.NULL;
			}
		} catch (SlotDoesntExistException e) {
			mode = MODE_NOOP;
		}
	}

}