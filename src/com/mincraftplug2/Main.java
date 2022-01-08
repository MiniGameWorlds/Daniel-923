package com.mincraftplug2;

import org.bukkit.plugin.java.JavaPlugin;

import com.worldbiomusic.minigameworld.api.MiniGameWorld;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("서버가 활성화되었습니다.");
		//미니게임 버전
		MiniGameWorld mw = MiniGameWorld.create("0.3.2");
		mw.registerMiniGame(new Dodgeball());
		//mw.registerMiniGame(new Soloplay());
	}

	@Override
	public void onDisable() {
		getLogger().info("서버가 비활성화되었습니다.");
	}

}
