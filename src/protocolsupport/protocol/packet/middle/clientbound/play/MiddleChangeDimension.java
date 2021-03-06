package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.window.AbstractWindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	public MiddleChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	protected final ClientCache clientCache = cache.getClientCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final AbstractWindowsRemapper windowRemapper = WindowsRemappersRegistry.get(version);


	protected String dimension;
	protected String world;
	protected long hashedSeed;
	protected GameMode gamemodeCurrent;
	protected GameMode gamemodePrevious;
	protected boolean worldDebug;
	protected boolean worldFlat;
	//TODO: handle in <= 1.15.2 impls
	protected boolean keepEntityMetadata;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		dimension = StringSerializer.readVarIntUTF8String(serverdata);
		world = StringSerializer.readVarIntUTF8String(serverdata);
		hashedSeed = serverdata.readLong();
		gamemodeCurrent = GameMode.getById(serverdata.readByte());
		gamemodePrevious = GameMode.getById(serverdata.readByte());
		worldDebug = serverdata.readBoolean();
		worldFlat = serverdata.readBoolean();
		keepEntityMetadata = serverdata.readBoolean();
	}

	@Override
	protected void handleReadData() {
		clientCache.setCurrentDimension(dimension);
		entityCache.clearEntities();
		windowCache.setPlayerWindow(windowRemapper.get(WindowType.PLAYER, 0));
	}

}
