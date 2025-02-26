package common.battle.attack;

import common.CommonStatic;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeTransform;
import common.util.anim.EAnimD;
import common.util.pack.EffAnim;

public class ContBlast extends ContAb {
    protected final EAnimD<EffAnim.BlastEff> anim;
    protected final AttackBlast atkBlast;
    private int t = 0;

    protected ContBlast(AttackBlast atkBlast, float p, int lay) {
        super(atkBlast.model.b, p, lay);
        anim = (atkBlast.dire == 1 ? effas().A_E_BLAST : effas().A_BLAST).getEAnim(EffAnim.BlastEff.START);
        anim.setTime(1);
        this.atkBlast = atkBlast;
    }

    @Override
    public void draw(FakeGraphics gra, P p, float psiz) {
        FakeTransform at = gra.getTransform();
        anim.draw(gra, p, psiz);
        gra.setTransform(at);
    }

    public void drawAxis(FakeGraphics gra, P p, float siz) {
        float rat = CommonStatic.BattleConst.ratio;
        int h = (int) (640 * rat * siz);
        gra.setColor(FakeGraphics.MAGENTA);
    }

    @Override
    public void update() { // FIXME: update on same frame as attack
        t++;
        if (t == EXPLOSION_PRE){
            anim.changeAnim(EffAnim.BlastEff.EXPLODE, true);
        }
        if (t > 1 && (t - 1) % 10 == 0 && t < 3*EXPLOSION_ITV+EXPLOSION_PRE)
            CommonStatic.setSE(EXPLOSION_SE + (t / 11 - 1));
        if (t > 2*EXPLOSION_ITV+EXPLOSION_PRE+EXPLOSION_POST)
            activate = false;
        else{
            if(t >= EXPLOSION_PRE){
                sb.getAttack(atkBlast);
            }
        }
        updateAnimation();
    }

    @Override
    public void updateAnimation() {
        anim.update(false);
    }

    @Override
    public boolean IMUTime() {
        return false;
    }
}
