package common.battle.attack;

import common.battle.entity.AbEntity;
import common.battle.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class AttackVolcano extends AttackAb {
	public ContVolcano handler;

	protected boolean attacked = false;

	protected final List<Entity> vcapt = new ArrayList<>();

	private byte volcTime = 40;

	public AttackVolcano(Entity e, AttackAb a, float sta, float end, int vt) {
		super(e, a, sta, end, false);
		this.isCounter = a.isCounter;
		this.sta = sta;
		this.end = end;
		this.waveType = vt;
	}

	public void capture() {
		List<AbEntity> le = this.model.b.inRange(this.touch, -this.dire, this.sta, this.end, this.excludeRightEdge);
		this.capt.clear();
		for (AbEntity e : le) {
			if (e instanceof Entity && !this.vcapt.contains(e))
				this.capt.add(e);
		}
	}

	public void excuse() {
		process();
		this.volcTime = (byte)(this.volcTime - 1);
		if (this.volcTime == 0) {
			this.volcTime = (byte)20;
			this.vcapt.clear();
		}
		this.atk = this.rawAtk;
		if (this.attacker != null) {
			if (this.attacker.status[24][0] != 0)
				this.atk += this.atk * this.attacker.status[24][0] / 100;
			if (this.attacker.status[8][0] != 0)
				this.atk = this.atk * this.attacker.status[8][1] / 100;
			if (this.attacker.dire == 1 && this.attacker.basis.canon.deco == 4)
				this.atk = (int)(this.atk * this.attacker.basis.b.t().getDecorationMagnification(this.attacker.basis.canon.deco, 3));
		}
		for (AbEntity e : this.capt) {
			if (e.isBase() && !(e instanceof Entity))
				continue;
			if (e instanceof Entity) {
				e.damaged(this);
				this.attacked = true;
				this.vcapt.add((Entity)e);
			}
		}
	}
}