package lee.code.playerdata.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerTable {
  @DatabaseField(id = true, canBeNull = false)
  private UUID uniqueId;

  @DatabaseField(columnName = "name", canBeNull = false)
  private String name;

  @DatabaseField(columnName = "skin", canBeNull = false)
  private String skin;

  public PlayerTable(UUID uniqueId, String name, String skin) {
    this.uniqueId = uniqueId;
    this.name = name;
    this.skin = skin;
  }
}
