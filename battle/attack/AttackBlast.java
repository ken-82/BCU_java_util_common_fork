package common.battle.attack;

import common.battle.entity.AbEntity;
import common.battle.entity.Entity;
import common.util.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttackBlast extends AttackAb {
    protected final Set<Entity>[] captured = new HashSet[3];
    protected final List<AbEntity>[] capt_blast = new ArrayList[3];
    private byte blastTime = 0;

    protected AttackBlast(Entity attacker, AttackSimple src, float sta, float end, int bt) {
        super(attacker, src, sta, end,false);
        waveType = bt;
        for (int i = 0; i < 3; i++) {
            capt_blast[i] = capt;
            captured[i] = new HashSet<>();
        }
    }

    @Override
    public void capture() {
        for (List<AbEntity> list : capt_blast) {
            list.clear();
        }
        List<AbEntity> le;
        List<AbEntity> le2;
        if (blastTime < EXPLOSION_POST) {
            le = model.b.inRange(touch, -dire, sta, end, excludeRightEdge);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured[0].contains((Entity) e)) {
                    capt_blast[0].add(e);
                }
        }
        if(blastTime >= EXPLOSION_ITV && blastTime < EXPLOSION_ITV+EXPLOSION_POST){
            float sta_out = end - EXPLOSION_PIERCE_2;
            float end_out = end;
            float sta_in = sta;
            float end_in = sta + EXPLOSION_PIERCE_2;
            le = model.b.inRange(touch, -dire, sta_out, end_out, excludeRightEdge);
            le2 = model.b.inRange(touch, -dire, sta_in, end_in, excludeRightEdge);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured[1].contains((Entity) e)) {
                    capt_blast[1].add(e);
                }

            if (blastTime >= EXPLOSION_PRE) {
                for (AbEntity e : le2)
                    if (e instanceof Entity && !captured[0].contains((Entity) e)) {
                        capt_blast[1].add(e);
                    }
            }
        }
        if(blastTime >= 2*EXPLOSION_ITV && blastTime < 2*EXPLOSION_ITV+EXPLOSION_POST){
            float sta_out = end - EXPLOSION_PIERCE_3;
            float end_out = end - EXPLOSION_PIERCE_2;
            float sta_in = sta + EXPLOSION_PIERCE_2;
            float end_in = sta + EXPLOSION_PIERCE_3;
            le = model.b.inRange(touch, -dire, sta_out, end_out, excludeRightEdge);
            le2 = model.b.inRange(touch, -dire, sta_in, end_in, excludeRightEdge);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured[2].contains((Entity) e)) {
                    capt_blast[2].add(e);
                }

            if (blastTime >= EXPLOSION_PRE) {
                for (AbEntity e : le2)
                    if (e instanceof Entity && !captured[2].contains((Entity) e)) {
                        capt_blast[2].add(e);
                    }
            }
        }
    }

    @Override
    public void excuse() {
        process();
        blastTime++;
        if (attacker != null) {
            if (attacker.status[P_STRONG][0] != 0)
                atk += atk * attacker.status[P_STRONG][0] / 100;
            if (attacker.status[P_WEAK][0] != 0)
                atk = atk * attacker.status[P_WEAK][1] / 100;
        }
        for (int i = 0; i < 3; i++) {
            for (AbEntity e : capt_blast[i]) {
                if (e.isBase())
                    continue;
                atk = (int)(rawAtk * EXPLOSION_MULTI[i]);
                if (e instanceof Entity) {
                    e.damaged(this);
                    captured[i].add((Entity) e);
                }
            }
        }
    }
}
