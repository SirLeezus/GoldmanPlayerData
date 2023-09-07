package lee.code.playerdata.database.cache;

import lee.code.playerdata.database.DatabaseManager;
import lee.code.playerdata.database.handlers.DatabaseHandler;
import lee.code.playerdata.database.tables.PlayerTable;
import lee.code.playerdata.utils.CoreUtil;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {
  private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, UUID> nameCache = new ConcurrentHashMap<>();

  public CachePlayers(DatabaseManager databaseManager) {
    super(databaseManager);
  }

  public PlayerTable getPlayerTable(UUID uuid) {
    return playersCache.get(uuid);
  }

  public void setPlayerTable(PlayerTable playerTable) {
    playersCache.put(playerTable.getUniqueId(), playerTable);
    nameCache.put(playerTable.getName(), playerTable.getUniqueId());
  }

  public boolean hasPlayerData(UUID uuid) {
    return playersCache.containsKey(uuid);
  }

  public void createPlayerData(Player player) {
    final PlayerTable playerTable = new PlayerTable(player.getUniqueId(), player.getName(), CoreUtil.getPlayerSkin(player));
    setPlayerTable(playerTable);
    createPlayerDatabase(playerTable);
  }

  public void updatePlayerData(Player player) {
    final PlayerTable playerTable = getPlayerTable(player.getUniqueId());
    nameCache.remove(playerTable.getName());
    playerTable.setName(player.getName());
    playerTable.setSkin(CoreUtil.getPlayerSkin(player));
    setPlayerTable(playerTable);
    updatePlayerDatabase(playerTable);
  }

  public String getName(UUID uuid) {
    return getPlayerTable(uuid).getName();
  }

  public String getSkin(UUID uuid) {
    return getPlayerTable(uuid).getSkin();
  }

  public UUID getUniqueId(String name) {
    if (!nameCache.containsKey(name)) return null;
    return nameCache.get(name);
  }
}
