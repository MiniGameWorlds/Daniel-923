package com.mincraftplug2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.minigameframes.SoloMiniGame;
import com.worldbiomusic.minigameworld.util.Utils;

public class Soloplay extends SoloMiniGame {

	public Soloplay() {
		super("Sologame", 15, 5);
		getSetting().setPassUndetectableEvent(true);
	}

	@Override
	protected void initGameSettings() {
		//미니게임 초기화 될 때 실행되는 메소드
	}

	@Override
	protected void runTaskAfterStart() {

		super.runTaskAfterStart();
		getSoloPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 30));

	}

	@Override
	protected void processEvent(Event event) {

		if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				Player p = e.getPlayer();
				Fireball fireball = p.launchProjectile(Fireball.class);
				fireball.setYield(0);

				e.setCancelled(true);
			}
		} else if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e1 = (ProjectileHitEvent) event;
			Projectile proj = e1.getEntity();
			Utils.info("여기 e");

			if (proj.getShooter() instanceof Player) {
				Player p = (Player) proj.getShooter();
				Block hitEntity = e1.getHitBlock();
				Material block = hitEntity.getType();
				if (block == null)
					return;
				if (block == Material.GLOWSTONE) {
					plusScore(p, 1);
					Utils.info("여기 proj");
				}

			}

		}
	}

	@Override
	protected List<String> registerTutorial() {
		List<String> tutorial = new ArrayList<>();
		tutorial.add("우측 클릭을 통해 스노우볼 or 파이어볼 날렸을 때 1점 추가");
		return tutorial;
	}

}
