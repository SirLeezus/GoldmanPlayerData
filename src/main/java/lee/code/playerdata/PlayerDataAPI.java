package lee.code.playerdata;

import lee.code.playerdata.database.cache.CachePlayers;
import lee.code.playerdata.utils.CoreUtil;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerDataAPI {
  public static String getName(UUID uuid) {
    return PlayerData.getInstance().getCacheManager().getCachePlayers().getName(uuid);
  }

  public static UUID getUniqueId(String name) {
    return PlayerData.getInstance().getCacheManager().getCachePlayers().getUniqueId(name);
  }

  public static String getSkin(UUID uuid) {
    return PlayerData.getInstance().getCacheManager().getCachePlayers().getSkin(uuid);
  }

  public static ItemStack getPlayerHead(UUID uuid) {
    return CoreUtil.createPlayerHead(PlayerData.getInstance().getCacheManager().getCachePlayers().getSkin(uuid));
  }

  public static ItemStack getPlayerHead(String name) {
    final CachePlayers cachePlayers = PlayerData.getInstance().getCacheManager().getCachePlayers();
    return CoreUtil.createPlayerHead(cachePlayers.getSkin(cachePlayers.getUniqueId(name)));
  }
}
