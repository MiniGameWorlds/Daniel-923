package com.mincraftplug2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.minigameframes.SoloMiniGame;

public class Findglowstone extends SoloMiniGame {

	long starttime, findtime, finaltime;

	public Findglowstone() {
		super("Sologame", 60, 5);
		getSetting().setPassUndetectableEvent(true);
		getSetting().setIcon(Material.SNOWBALL);
	}

	@Override
	protected void initGameSettings() {
		//미니게임 초기화 될 때 실행되는 메소드
	}

	@Override
	protected void runTaskAfterStart() {

		super.runTaskAfterStart();
		getSoloPlayer().getInventory().addItem(new ItemStack(Material.SNOWBALL, 1));
		starttime = System.currentTimeMillis();
	}

	@Override
	protected void processEvent(Event event) {

		if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e1 = (ProjectileHitEvent) event;
			Projectile proj = e1.getEntity();

			if (proj.getShooter() instanceof Player && proj instanceof Snowball) {
				Player p = (Player) proj.getShooter();
				Block hitEntity = e1.getHitBlock();
				Material block = hitEntity.getType();

				if (block == null)
					return;
				if (block == Material.GLOWSTONE) {
					//발광석을 찾는 시간으로 순위를 매긴다
					//찾은 시간 - 시작한 시간 = 발광석 찾는데 걸린 시간
					//plusScore(p, 1);
					findtime = System.currentTimeMillis();
					finaltime = (findtime - starttime) / 1000;
					p.sendMessage("찾는 데 걸린 시간: " + finaltime + "초");

					//getTimeLimit()은 현재 플레이타임

					/*(플레이 타임 / 구간 숫자) 으로 나눠서 해당 구간에 들어오면 점수를 할당
					- 12초 간격으로 다섯 구간으로 나뉨
					- 0~12, 13~24, 25~36, 37~48, 49~60 
					- 100점, 80점, 60점, 40점, 20점*/

					if (finaltime > getTimeLimit() * 0.8) {
						plusScore(p, 20);
					} else if (finaltime > getTimeLimit() * 0.6) {
						plusScore(p, 40);

					} else if (finaltime > getTimeLimit() * 0.4) {
						plusScore(p, 60);

					} else if (finaltime > getTimeLimit() * 0.2) {
						plusScore(p, 80);

					} else if (finaltime <= getTimeLimit() * 0.2) {
						plusScore(p, 100);
					}

					finishGame();

				}

			}

		} else if (event instanceof PlayerDropItemEvent) {
			((PlayerDropItemEvent) event).setCancelled(true);
		} else if (event instanceof ProjectileLaunchEvent) {
			Projectile proj = ((ProjectileLaunchEvent) event).getEntity();

			if (proj.getShooter() instanceof Player && proj instanceof Snowball) {
				Player p = (Player) proj.getShooter();
				p.getInventory().addItem(new ItemStack(Material.SNOWBALL));
			}
		}

	}

	@Override
	protected List<String> registerTutorial() {
		List<String> tutorial = new ArrayList<>();
		tutorial.add("목표: 발광석을 빨리 찾아서 눈덩이로 맞추자");
		tutorial.add("총 플레이타임은 60초");
		tutorial.add("12초 간격으로 점수를 20~100점을 지급한다");

		return tutorial;
	}

}
