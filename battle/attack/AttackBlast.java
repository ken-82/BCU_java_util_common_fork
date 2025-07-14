package common.battle.attack;

import common.battle.entity.AbEntity;
import common.battle.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttackBlast extends AttackAb {
    public ContBlast handler;
    protected final Set<Entity> bcapt = new HashSet<>();

    boolean attacked = false;

    protected AttackBlast(Entity attacker, AttackSimple src, float sta, float end, int bt) {
        super(attacker, src, sta, end, false);
        waveType = bt;
    }

    @Override
    public void capture() {
        capt.clear();

        List<AbEntity> le = new ArrayList<>();
        int time = handler.getTime();
        if (time == 11 || time == 21 || time == 31)
            bcapt.clear();
        int lvl = getLevel(time);

        if (lvl == 0)
            le.addAll(model.b.inRange(touch, -dire, sta, end, excludeRightEdge));
        else if (lvl == 1)
            le.addAll(model.b.inRange(touch, -dire, sta + 100, end - 100, excludeRightEdge, 150));
        else if (lvl == 2)
            le.addAll(model.b.inRange(touch, -dire, sta + 200, end - 200, excludeRightEdge, 350));
        for (AbEntity e : le)
            if (e instanceof Entity && !bcapt.contains((Entity) e))
                capt.add(e);
    }

    @Override
    public void excuse() {
        process();

        if (attacker != null) {
            if (attacker.status[P_STRONG][0] != 0)
                atk += atk * attacker.status[P_STRONG][0] / 100;
            if (attacker.status[P_WEAK][0] != 0)
                atk = atk * attacker.status[P_WEAK][1] / 100;
            atk = (atk * (100 - (30 * getLevel(handler.getTime()))) / 100);
        }

        for (AbEntity e : capt) {
            if (e.isBase() && !(e instanceof Entity))
                continue;

            if (e instanceof Entity) {
                e.damaged(this);
                attacked = true;
                bcapt.add((Entity) e);
            }
        }
    }

    private int getLevel(int time) {
        if (time >= 11 && time <= 20)
            return 0;
        if (time >= 21 && time <= 30)
            return 1;
        if (time >= 31)
            return 2;
        return -1;
    }
}
