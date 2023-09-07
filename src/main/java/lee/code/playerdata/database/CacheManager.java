package lee.code.playerdata.database;

import lee.code.playerdata.database.cache.CachePlayers;
import lombok.Getter;

public class CacheManager {
  @Getter private final CachePlayers cachePlayers;

  public CacheManager(DatabaseManager databaseManager) {
    this.cachePlayers = new CachePlayers(databaseManager);
  }
}
