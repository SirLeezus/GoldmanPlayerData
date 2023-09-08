package lee.code.playerdata;

import lee.code.playerdata.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerDataAPI {

  public static String getName(UUID uuid) {
    return Bukkit.getOfflinePlayer(uuid).getName();
  }

  public static UUID getUniqueId(String name) {
    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(name);
    if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) return null;
    else return offlinePlayer.getUniqueId();
  }

  public static String getSkin(UUID uuid) {
    return PlayerData.getInstance().getCacheManager().getCachePlayers().getSkin(uuid);
  }

  public static ItemStack getPlayerHead(UUID uuid) {
    return CoreUtil.createPlayerHead(getSkin(uuid));
  }
}
