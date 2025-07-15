package common.battle.attack;

import common.CommonStatic;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeTransform;
import common.util.anim.EAnimD;
import common.util.pack.EffAnim;

public class ContBlast extends ContAb {
    protected final AttackBlast atk;
    protected final EAnimD<EffAnim.BlastEff> anim;
    private int t = 0;

    protected ContBlast(AttackBlast atkBlast, float p, int lay) {
        super(atkBlast.model.b, p, 8);
        atk = atkBlast;
        atk.handler = this;
        anim = (atkBlast.dire == 1 ? effas().A_E_BLAST : effas().A_BLAST).getEAnim(EffAnim.BlastEff.START);
        anim.setTime(1);
    }

    @Override
    public void draw(FakeGraphics gra, P p, float psiz) {
        FakeTransform at = gra.getTransform();
        P s = new P(atk.dire == -1 ? p.x + (100 * psiz) : p.x - (30 * psiz), p.y); // todo: correct offset for enemies
        anim.draw(gra, s, psiz);
        P.delete(s);
        gra.setTransform(at);
        if (CommonStatic.getConfig().ref)
            drawAxis(gra, p, psiz);

    }

    public void drawAxis(FakeGraphics gra, P p, float siz) {
        siz *= 1.25f;
        float rat = CommonStatic.BattleConst.ratio;
        int h = (int) (640 * rat * siz);
        float d0 = Math.min(atk.sta, atk.end); // leftmost point
        int y = (int) p.y;
        gra.setColor(FakeGraphics.MAGENTA);

        int blastLevel = atk.getLevel(t);

        if (blastLevel == 0) {
            float rawWidth = Math.abs(atk.sta - atk.end); // raw length
            int x = (int) ((d0 - pos) * rat * siz + p.x);
            int w = (int) (rawWidth * rat * siz);
            if (atk.attacked)
                gra.fillRect(x, y, w, h);
            else
                gra.drawRect(x, y, w, h);
        } else {
            int x1 = (int) ((d0 - EXPLOSION_SHIFT * blastLevel - pos) * rat * siz + p.x);
            int x2 = (int) ((Math.max(atk.sta, atk.end) - pos + EXPLOSION_SHIFT * (blastLevel - 1)) * rat * siz + p.x);
            int w = (int) (EXPLOSION_SHIFT * rat * siz);
            if (atk.attacked) {
                gra.fillRect(x1, y, w, h);
                gra.fillRect(x2, y, w, h);
            }
            else {
                gra.drawRect(x1, y, w, h);
                gra.drawRect(x2, y, w, h);
            }
        }
    }

    @Override
    public void update() {
        t++;
        if (atk.attacked)
            atk.attacked = false;

        if (t == EXPLOSION_PRE)
            anim.changeAnim(EffAnim.BlastEff.EXPLODE, true);
        if (t == 10)
            CommonStatic.setSE(EXPLOSION_SE);
        else if (t == 20)
            CommonStatic.setSE(EXPLOSION_SE + 1);
        else if (t == 30)
            CommonStatic.setSE(EXPLOSION_SE + 2);
        if (t >= 10)
            sb.getAttack(atk);
        if (t == 44)
            activate = false;
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

    public int getTime() {
        return t;
    }
}
