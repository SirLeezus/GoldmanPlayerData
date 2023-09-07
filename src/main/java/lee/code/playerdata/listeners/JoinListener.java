package lee.code.playerdata.listeners;

import lee.code.playerdata.PlayerData;
import lee.code.playerdata.database.cache.CachePlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
  private final PlayerData playerData;

  public JoinListener(PlayerData playerData) {
    this.playerData = playerData;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    final Player player = e.getPlayer();
    final CachePlayers cachePlayers = playerData.getCacheManager().getCachePlayers();
    if (!cachePlayers.hasPlayerData(player.getUniqueId())) cachePlayers.createPlayerData(player);
    else cachePlayers.updatePlayerData(player);
  }
}
