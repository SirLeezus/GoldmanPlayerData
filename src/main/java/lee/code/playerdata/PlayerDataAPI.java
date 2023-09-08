package lee.code.playerdata;

import lee.code.playerdata.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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

  public static Player getOnlinePlayer(String name) {
    final UUID targetID = getUniqueId(name);
    if (targetID == null) return null;
    final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetID);
    if (offlineTarget.isOnline()) return offlineTarget.getPlayer();
    else return null;
  }

  public static Player getOnlinePlayer(UUID uuid) {
    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
    if (offlinePlayer.isOnline()) return offlinePlayer.getPlayer();
    else return null;
  }

  public static void sendPlayerMessageIfOnline(UUID uuid, Component message) {
    final Player target = getOnlinePlayer(uuid);
    if (target == null) return;
    target.sendMessage(message);
  }
}
