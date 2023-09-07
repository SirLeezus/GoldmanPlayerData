package lee.code.playerdata;

import lee.code.playerdata.database.CacheManager;
import lee.code.playerdata.database.DatabaseManager;
import lee.code.playerdata.listeners.JoinListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerData extends JavaPlugin {
  @Getter private static PlayerData instance;
  @Getter private CacheManager cacheManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    instance = this;
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(databaseManager);

    databaseManager.initialize(false);
    registerListeners();
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new JoinListener(this), this);
  }
}
