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
        if (attacked)
            attacked = false;
        List<AbEntity> le = new ArrayList<>();
        int time = handler.getTime();
        if ((time - 1) % 10 == 0)
            bcapt.clear();

        if (time >= 11 && time <= 20) // todo: figure out why this isn't capturing anything
            le.addAll(model.b.inRange(touch, -dire, sta, end, excludeRightEdge));
        else if (time >= 21 && time <= 30)
            le.addAll(model.b.inRange(touch, -dire, sta + 100, end - 100, excludeRightEdge, 150));
        else if (time >= 31)
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
}
