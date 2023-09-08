package lee.code.playerdata.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CoreUtil {
  public static String getPlayerSkin(Player player) {
    GameProfile profile = ((CraftPlayer) player).getProfile();
    Property property = profile.getProperties().get("textures").iterator().next();
    return property.getValue();
  }

  public static void applyHeadSkin(ItemMeta itemMeta, String base64) {
    try {
      final SkullMeta skullMeta = (SkullMeta) itemMeta;
      final GameProfile profile = new GameProfile(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"), null);
      profile.getProperties().put("textures", new Property("textures", base64));
      if (skullMeta != null) {
        final Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
        mtd.setAccessible(true);
        mtd.invoke(skullMeta, profile);
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  public static ItemStack createPlayerHead(String skin) {
    final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
    final ItemMeta headMeta = head.getItemMeta();
    applyHeadSkin(headMeta, skin);
    head.setItemMeta(headMeta);
    return head;
  }

  public static List<String> getOnlinePlayers() {
    return Bukkit.getOnlinePlayers().stream()
      .filter(player -> !player.getGameMode().equals(GameMode.SPECTATOR))
      .map(Player::getName)
      .collect(Collectors.toList());
  }
}
