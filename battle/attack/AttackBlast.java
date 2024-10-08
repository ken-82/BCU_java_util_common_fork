package common.battle.attack;

import common.battle.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class AttackBlast extends AttackAb {
    protected final Set<Entity> captured = new HashSet<>();

    protected AttackBlast(Entity attacker, AttackSimple src, int bt) {
        super(attacker, src, 75, -75, false);
        waveType = bt;
    }

    @Override
    public void capture() {
        capt.clear();
        List<AbEntity> le;
        List<AbEntity> le2;
        if (blastTime < EXPLOSION_POST) {
            le = model.b.inRange(touch, -dire, sta, end, excludeRightEdge);
            le2 = model.b.inRange(touch, -dire, sta, end, excludeRightEdge);
            atk = rawAtk;
            for (AbEntity e : le)
                if (e instanceof Entity && !captured.contains((Entity) e)) {
                    capt.add(e);
                }

            if (blastTime >= EXPLOSION_PRE) {
                for (AbEntity e : le2)
                    if (e instanceof Entity && !captured.contains((Entity) e)) {
                        capt.add(e);
                    }
            }
        }
        //System.out.println("test");
        if(blastTime >= EXPLOSION_ITV && blastTime < EXPLOSION_ITV+EXPLOSION_POST){
            float sta_out = end - (dire == 1 ? -EXPLOSION_PIERCE_2 : EXPLOSION_PIERCE_2);
            float end_out = end;
            float sta_in = sta;
            float end_in = sta - (dire == 1 ? EXPLOSION_PIERCE_2 : -EXPLOSION_PIERCE_2);
            le = model.b.inRange(touch, -dire, sta_out, end_out, excludeRightEdge);
            le2 = model.b.inRange(touch, -dire, sta_in, end_in, excludeRightEdge);
            System.out.println("d0 " + sta_out + "d1 " + end_out + "d20 " + sta_in + "d21 " + end_in);
            atk = (int)(rawAtk*0.7);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured.contains((Entity) e)) {
                    capt.add(e);
                }

    }

    @Override
    public void excuse() {

    }
}
