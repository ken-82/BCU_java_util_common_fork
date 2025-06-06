package common.util.stage;

import common.io.json.JsonClass;
import common.io.json.JsonField;
import common.util.BattleStatic;
import common.util.Data;

import java.util.Arrays;
import java.util.HashSet;

@JsonClass(noTag = JsonClass.NoTag.LOAD)
public class StageLimit extends Data implements BattleStatic {
    public int maxMoney = 0;
    public int globalCooldown = 0;
    public int globalCost = 0;
    public int maxUnitSpawn = 0;

    public int[] cooldownMultiplier = { 100, 100, 100, 100, 100, 100 };
    public int[] costMultiplier = { 100, 100, 100, 100, 100, 100 };
    public int[] rarityDeployLimit = { 0, 0, 0, 0, 0, 0 }; // -1 for none

    public int[] deployDuplicationTimes = { 0, 0, 0, 0, 0, 0 }; // 0 for deactivated
    public int[] deployDuplicationDelay = { 0, 0, 0, 0, 0, 0 }; // unit is frame

    public boolean coolStart = false;
    
    public int cannonMultiplier = 100; // percentage

    //FIXME change it to a list that stores stats
    public int unitHpLimit = -1; // -1 for deactivated
    public int enemyHpLimit = -1; // -1 for deactivated
    public int unitSpeedLimit = -1; // -1 for deactivated
    public int enemySpeedLimit = -1; // -1 for deactivated
    public int unitDamageLimit = -1; // -1 for deactivated
    public int enemyDamageLimit = -1; // -1 for deactivated
    public int unitTBALimit = -1; // -1 for deactivated
    public int enemyTBALimit = -1; // -1 for deactivated
    public int unitKBLimit = -1; // -1 for deactivated
    public int enemyKBLimit = -1; // -1 for deactivated
    public int unitRangeLimit = -1; // -1 for deactivated
    public int enemyRangeLimit = -1; // -1 for deactivated

    @JsonField(generic = Integer.class)
    public HashSet<Integer> bannedCatCombo = new HashSet<>();

    public StageLimit() {

    }

    public StageLimit clone() {
        StageLimit sl;

        try {
            sl = (StageLimit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();

            sl = new StageLimit();
        }

        sl.maxMoney = maxMoney;
        sl.maxUnitSpawn = maxUnitSpawn;
        sl.globalCooldown = globalCooldown;
        sl.globalCost = globalCost;
        sl.cannonMultiplier = cannonMultiplier;

        sl.bannedCatCombo.addAll(bannedCatCombo);

        sl.cooldownMultiplier = cooldownMultiplier.clone();
        sl.costMultiplier = costMultiplier.clone();
        sl.rarityDeployLimit = rarityDeployLimit.clone();
        sl.deployDuplicationTimes = deployDuplicationTimes.clone();
        sl.deployDuplicationDelay = deployDuplicationDelay.clone();

        return sl;
    }

    public StageLimit combine(StageLimit second) {
        StageLimit combined = new StageLimit();
        combined.maxMoney = second.maxMoney == 0 ? maxMoney : second.maxMoney;
        combined.globalCooldown = second.globalCooldown == 0 ? globalCooldown : second.globalCooldown;
        combined.globalCost = second.globalCost == 0 ? globalCost : second.globalCost;
        combined.maxUnitSpawn = second.maxUnitSpawn == 0 ? maxUnitSpawn : second.maxUnitSpawn;
        combined.bannedCatCombo.addAll(bannedCatCombo);
        combined.bannedCatCombo.addAll(second.bannedCatCombo);
        return combined;
    }
}