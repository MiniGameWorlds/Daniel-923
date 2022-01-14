# DodgeBall
- 마인크래프트 플러그인
- 2팀으로 나뉘어 화염구를 던져 점수를 획득하는 게임
- 팀게임, 점수제


# 필요 플러그인
- 해당 게임 플러그인 DodgeBall 을 이용하기 위해서는 아래 MiniGameWorld 링크 들어가셔서 MiniGame을 다운 받으셔야 이용가능
- [MiniGameWorld 다운](https://github.com/MiniGameWorlds/MiniGameWorld)


# 다운로드 방법
- 우측 Releases 클릭 후 Aseets 아래에 있는 DodgeBall-1.0.0.jar파일을 다운로드 받으시면 됩니다. 
- 이후 다운로드 된 DodgeBall-1.0.0.jar파일은 plugin폴더에 넣으시면 됩니다.


# 게임 진행 방법
- 마인크래프트 접속한 후 명령어`/mw menu` 작성 후 DodgeBall 이라고 써있는 아이템을 클릭하면 게임 시작

# 룰
- 화염구를 우측클릭으로 다른 사람(상대편)을 맞출 시 1점 추가
- 화염구를 좌클릭으로 잡으면(반사하면) 1점 추가
- 제한 시간 내에 한 팀의 팀원이 모두 아웃되면 게임 종료 
- 만약 제한 시간 내에 안 끝날 시 점수가 높은 팀이 승



# 맵 가이드
- 자유도 

# config 가이드
- 대기장소, 위치 정하기
- redLocation: 레드팀이 게임 시작시 텔레포트되는 위치
- blueLocation: 팀이 게임 시작시 텔레포트되는 위치
```
custom-data: 
    redLocation:
      ==: org.bukkit.Location
      world: world
      x: 71.0
      y: 67.0
      z: 6.0
      pitch: 0.0
      yaw: 0.0
    blueLocation:
      ==: org.bukkit.Location
      world: world
      x: 87.0
      y: 70.0
      z: -6.0
      pitch: 0.0
      yaw: 0.0
