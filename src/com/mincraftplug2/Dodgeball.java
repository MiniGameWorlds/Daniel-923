package com.mincraftplug2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.worldbiomusic.minigameworld.minigameframes.TeamBattleMiniGame;

public class Dodgeball extends TeamBattleMiniGame {
	// 공격자: 한 명이 맞출 시 1점 추가
	// 수비자: 공을 잡으면 1점 추가
	// 제한 시간 내에 한 팀의 팀원이 모두 아웃되면 게임 종료 
	// 만약 제한 시간 내에 안 끝날 시 점수가 높은 팀이 승

	Location redLocation, blueLocation;

	public Dodgeball() {
		super("Dodgeball", 2, 30, 3);

		getSetting().setPassUndetectableEvent(true);
		getSetting().setIcon(Material.FIRE_CHARGE);
	}

	@Override
	protected void createTeams() {
		Team r = new Team("r", 2);
		Team b = new Team("b", 2);

		r.setColor(ChatColor.RED);
		b.setColor(ChatColor.BLUE);

		this.createTeam(r);
		this.createTeam(b);

	}

	@Override
	protected void registerCustomData() {
		super.registerCustomData();

		Map<String, Object> data = getCustomData();
		data.put("redLocation", getLocation());
		data.put("blueLocation", getLocation());
	}

	@Override
	public void loadCustomData() {
		super.loadCustomData();

		this.redLocation = (Location) getCustomData().get("redLocation");
		this.blueLocation = (Location) getCustomData().get("blueLocation");
	}

	@Override
	protected void runTaskAfterStart() {
		super.runTaskAfterStart();

		Team redTeam = getTeam("r");
		Team blueTeam = getTeam("b");

		redTeam.getMembers().forEach(p -> p.teleport(this.redLocation));
		blueTeam.getMembers().forEach(p -> p.teleport(this.blueLocation));

		getPlayers().forEach(p -> p.getInventory().addItem(new ItemStack(Material.FIRE_CHARGE, 1)));

	}

	@Override
	protected void processEvent(Event event) {
		super.processEvent(event);
		//ProjectileHitEvent: 발사체가 물체에 부딪힐 때 호출됨
		if (event instanceof ProjectileHitEvent) {
			ProjectileHitEvent e = (ProjectileHitEvent) event;

			Entity hitEntity = e.getHitEntity();
			if (hitEntity == null)
				return;
			if (hitEntity instanceof Player) {
				Projectile proj = e.getEntity();

				if (proj.getShooter() instanceof Player && proj instanceof Fireball) {
					Player p = (Player) proj.getShooter();

					//같은 팀이면 리턴, 다른 팀이면 점수 추가
					if (isSameTeam(p, (Player) hitEntity)) {
						return;
					}
					//점수 주는거
					plusTeamScore(p, 1);
					hitEntity.sendMessage("You hit...");
					//setLive((Player) hitEntity, false);

				}
			}
			//ProjectileLaunchEvent: 발사체가 발사될 때 호출됩니다.
		} else if (event instanceof ProjectileLaunchEvent) {
			ProjectileLaunchEvent e = (ProjectileLaunchEvent) event;

			Projectile proj = e.getEntity();

			if (proj.getShooter() instanceof Player) {
				Player p = (Player) proj.getShooter();
				p.setCooldown(Material.FIRE_CHARGE, 20 * 4);
				proj.setVelocity(p.getLocation().getDirection());
				proj.setGravity(false);

			}
			//EntityDamageByEntityEvent: 엔터티가 엔터티에 의해 손상되었을 때 호출됩니다.
		} else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			if (e.getEntityType() == EntityType.FIREBALL) {

				//잡았을 때 점수 3점 추가/좌클릭 했을 때 반사
				if (e.getDamager() instanceof Player) {
					Player p = (Player) e.getDamager();
					plusTeamScore(p, 1);
				}
			}
		} else if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e = (PlayerInteractEvent) event;

			if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				return;
			}

			ItemStack item = e.getItem();
			if (item == null) {
				return;
			}

			if (item.getType() == Material.FIRE_CHARGE) {

				Player p = e.getPlayer();
				int remainCoolDown = p.getCooldown(Material.FIRE_CHARGE);
				if (remainCoolDown == 0) {
					Fireball fireball = p.launchProjectile(Fireball.class);
					fireball.setYield(0);

					e.setCancelled(true);

				}

			}

		}

	}

	@Override
	protected List<String> registerTutorial() {
		List<String> tutorial = new ArrayList<>();
		tutorial.add("공격자: 한 명이 맞출 시 1점 추가");
		tutorial.add("수비자: 공을 잡으면 1점 추가");
		tutorial.add("제한 시간 내에 한 팀의 팀원이 모두 아웃되면 게임 종료 ");
		tutorial.add("만약 제한 시간 내에 안 끝날 시 점수가 높은 팀이 승");

		return tutorial;
	}

}
