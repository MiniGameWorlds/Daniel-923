package com.mincraftplug2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import com.worldbiomusic.minigameworld.minigameframes.SoloMiniGame;

/*
 * # 주제: 방탈출 게임


# 목표
- 방에서 최대한 빨리 탈출하여 버튼을 누르자


# 클리어 조건
- 올라가서 버튼을 누르면 완료
- 버튼 누르는 데 걸린 시간 표시
- 버튼 누른 시간 - 시작한 시간 = 버튼 누르는 데 걸린 시간


# 사망
- 떨어지면 사망(떨어지는 이벤트에 의해 받는 데미지를 즉사로/낙사 데미지 최대로)
- 죽으면 게임 종료
*/

/*Player Listener에서 PlayerInteract 이벤트를 이용하여 
 * action이 RIGHT_CLICK이고 block type이 버튼인지 확인한다.
*/
public class RoomEscape extends SoloMiniGame {

	public RoomEscape() {
		super("RoomEscape", 120, 5); //배포할 때는 대기시간을 10초로 변경하기
		getSetting().setIcon(Material.STONE_BUTTON);
		registerTask();
	}

	//등록 후 호출
	private void registerTask() {
		getTaskManager().registerTask("minusTask", new Runnable() {

			@Override
			public void run() {
				minusScore(1);
			}
		});
	}

	@Override
	protected void initGameSettings() {

	}

	@Override
	protected void runTaskAfterStart() {
		super.runTaskAfterStart();

		plusScore(getTimeLimit());

		//20틱이 1초
		//이름, 몇초 후에 시작, 몇초마다 반복
		getTaskManager().runTaskTimer("minusTask", 0, 20);
	}

	@Override
	protected void processEvent(Event event) {

		if (event instanceof EntityDamageEvent) {
			EntityDamageEvent e1 = (EntityDamageEvent) event;
			Player p = (Player) e1.getEntity();

			if (e1.getCause() == DamageCause.FALL) {
				p.sendMessage("낙사");
				finishGame();
			}
		} else if (event instanceof PlayerInteractEvent) {
			PlayerInteractEvent e2 = (PlayerInteractEvent) event;
			Block clickblock = e2.getClickedBlock();
			//허공에 클릭했을 때 발생되는 것
			if (clickblock == null) {
				return;
			}
			Material blocktype = clickblock.getType();

			if (e2.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (blocktype == Material.STONE_BUTTON) {
					finishGame();
				}
			}
		}

	}

	@Override
	protected List<String> registerTutorial() {

		List<String> tutorial = new ArrayList<>();
		tutorial.add("목표: 방에서 최대한 빨리 탈출하여 버튼을 누르자");
		tutorial.add("클리어 조건: 올라가서 버튼을 누르면 완료");
		tutorial.add("점수: 120점에서 1초마다 1점씩 감점");
		tutorial.add("사망 조건: 떨어지면 사망(낙사)");

		return tutorial;
	}

}
