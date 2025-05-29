package common.battle.attack;

import common.battle.entity.AbEntity;
import common.battle.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttackBlast extends AttackAb {
    protected final List<Set<Entity>> captured = new ArrayList<>();
    protected final List<List<AbEntity>> capt_blast = new ArrayList<>();
    private byte blastTime = 0;

    protected AttackBlast(Entity attacker, AttackSimple src, float sta, float end, int bt) {
        super(attacker, src, sta, end,false);
        waveType = bt;
        for (int i = 0; i < 5; i++) {
            capt_blast.add(new ArrayList<>());
            captured.add(new HashSet<>());
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
            le = model.b.inRange(touch, -dire, sta, end, true);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured.get(0).contains((Entity) e)) {
                    capt_blast.get(0).add(e);
                }
        }
        if(blastTime >= EXPLOSION_ITV && blastTime < EXPLOSION_ITV+EXPLOSION_POST){
            float sta_out = end - EXPLOSION_PIERCE_2;
            float end_out = end;
            float sta_in = sta;
            float end_in = sta + EXPLOSION_PIERCE_2;
            le = model.b.inRange(touch, -dire, sta_out, end_out, true);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured.get(1).contains((Entity) e)) {
                    capt_blast.get(1).add(e);
                }
            le2 = model.b.inRange(touch, -dire, sta_in, end_in, true);
            for (AbEntity e : le2)
                if (e instanceof Entity && !captured.get(2).contains((Entity) e)) {
                    capt_blast.get(2).add(e);
                }

        }
        if(blastTime >= 2*EXPLOSION_ITV && blastTime < 2*EXPLOSION_ITV+EXPLOSION_POST){
            float sta_out = end - EXPLOSION_PIERCE_3;
            float end_out = end - EXPLOSION_PIERCE_2;
            float sta_in = sta + EXPLOSION_PIERCE_2;
            float end_in = sta + EXPLOSION_PIERCE_3;
            le = model.b.inRange(touch, -dire, sta_out, end_out, true);
            for (AbEntity e : le)
                if (e instanceof Entity && !captured.get(3).contains((Entity) e)) {
                    capt_blast.get(3).add(e);
                }
            le2 = (model.b.inRange(touch, -dire, sta_in, end_in, true));
            for (AbEntity e : le2)
                if (e instanceof Entity && !captured.get(4).contains((Entity) e)) {
                    capt_blast.get(4).add(e);
                }
        }
    }

    @Override
    public void excuse() {
        process();
        blastTime++;
        for (int i = 0; i < 5; i++) {
            for (AbEntity e : capt_blast.get(i)) {
                if (e.isBase() && !(e instanceof Entity))
                    continue;
                atk = (int)(rawAtk * EXPLOSION_MULTI[i]);
                if (attacker != null) {
                    if (attacker.status[P_STRONG][0] != 0)
                        atk += atk * attacker.status[P_STRONG][0] / 100;
                    if (attacker.status[P_WEAK][0] != 0)
                        atk = atk * attacker.status[P_WEAK][1] / 100;
                }
                if (e instanceof Entity) {
                    e.damaged(this);
                    captured.get(i).add((Entity) e);
                }
            }
        }
    }
}
