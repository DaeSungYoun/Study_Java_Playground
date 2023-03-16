# 명령어

* [`clone`](#clone), [`switch`](#switch), [`commit`](#commit), [`reset`](#reset), [`revert`](#revert), [`branch`](#branch), [`config`](#config), [`merge`](#merge), [`rebase`](#rebase), [`log`](#log), [`push`](#push), [`pull`](#pull), [`restore`](#restore), [`checkout`](#checkout), [`stash`](#stash), [`remote`](#remote), [`reflog`](#reflog), [`cherry-pick`](#cherry-pick), [`fetch`](#fetch), [`status`](#status), [`init`](#init), [`add`](#add), [`diff`](#diff)

* ## clone

  * git clone {git 주소} : 명령어를 실행하는 경로에 clone
  * git clone {git 주소} {경로} : {경로}에 clone

* ## switch

  * git switch {branch name} : {branch name} 브랜치로 전환
  * git switch -c {branch name} : {branch name} 브랜치를 새로 만들면서 {branch name} 브랜치로 전환

* ## commit
  
  * git commit -m "{커밋 메세지}" : git 커밋 & 메세지
  * git commit -am “{커밋 메세지}” : git add + git 커밋 & 메세지, untracked 파일이 없을때 가능함
  * git commit --amend : 직전에 커밋한 메세지 변경 가능, 에디터 창 뜸
    * staging aread에 파일이 있으면 커밋에 포함도 시킴
  * git commit --amend -m "{커밋 메세지}" : 메세지 수정
  * git commit --allow-empty -m "{커밋 메세지}" : 메세지만 커밋하는 경우에 사용

* ## reset
  
  * git reset -—soft : repository에서 staging area로 이동
  * git reset -—mixed (default) : repository에서 working directory로 이동
  * git reset -—hard : 수정사항 완전히 삭제
    * git reset -—hard : 이렇게 실행하면 바로 직전 commit hash가 HEAD가 됨
    * git reset -—hard {commit hash} : HEAD를 해당 commit hash로 보냄, 중간에 있던 커밋들은 전부 삭제됨
    * git reset —-hard HEAD~2 : 현재 HEAD에서 뒤로 2단계까지 커밋 삭제
    * git reset --hard HEAD^ : 현재 HEAD에서 뒤로 1단계까지 커밋 삭제
  * git reset -merge : 바로 이전 병합 취소

* ## revert
  
  * git revert {commit hash} : 선택한 commit hash의 변경점들을 제외한 커밋을 새로 만듦
  * git revert --no-commit {되돌릴 커밋 해시} : 커밋하지않은 상태로 revert
  * git revert —continue : revert를 진행할 때 conflict이 발생했다면, conflict를 해결 후 이어서 revert를 진행할 때 사용
  * git revert —abort : revert를 진행할 떄 conflict가 발생했을때 revert를 취소하고 싶을때 사용
  * git revert —skip : 아직 뭔지 모르겠음

* ## branch

  * git branch : 해당 Repository의 Local 브랜치 목록 조회
  * git branch {branch name} : 브랜치 생성
  * git branch -v : 로컬 브랜치 별로 마지막 커밋 내역과 함께 조회
  * git branch -r : 원격 브랜치 목록 보기
  * git branch -a : 로컬/원격 브랜치 목록 보기
  * git branch --all : 해당 Repository의 Server 브랜치 목록 조회
  * git branch -m {branch name} {change branch name} : 브랜치 이름 바꾸기 {branch name}에서 {change branch name}으로
  * git branch --merged : 이미 merge된 브랜치 조회(*가 표시되지 않은 브랜치는 삭제 가능)
  * git branch --no-merged : 아직 merge 되지 않은 브랜치 조회
  * git branch -d {branch name} : 브랜치 삭제하기
    * git branch | grep -v '^*' | xargs git branch -d : 로컬 브랜치 모두 삭제
  * git branch -D {branch name} : 삭제할 브랜치에 다른 브랜치로 적용되지 않은 내용의 커밋이 있을 시에 강제 삭제

* ## config

  * Git은 지역(Local), 전역(Global), 시스템(System) 이렇게 3가지 범위로 설정 가능
  * {범위} : --local, --global, --system
  * git config {범위} --list : config 정보 확인
  * git config {범위} {key} : {key}의 value 조회
  * git config {범위} {key} {value} : {key}에 {value} 수정
  * git config {범위} --unset {key} : {key} 삭제(value도 함께 삭제)
  * git config --global init.defaultBranchmain : 기본 브랜치를 main 브랜치로 설정
  * git config --global core.editor "code --wait --disable-extensions" : VSCode로 기본 에디터 변경
  * git config pull.rebase false : pull 기본 전략을 merge로 설정
  * git config pull.rebase true : pull 기본 전략을 rebase로 설정

* ## merge
  
  * git merge {땡겨와서 합칠 브랜치 명} : 현재 브랜치로 가져올 브랜치 명을 입력
  * git merge --abort : 당장 충돌 해결이 어려울 경우 아래 명령어로 merge 중단
    * 충돌을 해결했다면 git add로 해결된 파일을 추가 후 git commit으로 머지를 이어서 진행함
  * git merge --squash (대상 브랜치) : 대상 브랜치의 여러 커밋들을 한번에 묶어서 merge함
  * git merge —continue : conflict 발생 후 소스를 수정한다음 머지를 이어서 진행할떄 사용

* ## rebase

  * git rebase {갖다 붙힐 브랜치 명} : 현재 브랜치를 갖다 붙힐 브랜치 명을 입력
  * git rebase --abort : 당장 충돌 해결이 어려울 경우 아래 명령어로 merge 중단
  * git rebase --continue : 충돌 해결 후, git add {파일} 파일 추가 후, rebase를 이어서 진행할때 사용
  * git rebase -i : 과거 커밋 내역을 다양한 방법으로 수정 가능
  * git rebase --onto (도착 브랜치) (출발 브랜치) (이동할 브랜치) : 잔가지 옮기기

* ## log
  
  * git log
  * git log —patch
  * git log —oneline : 정렬(최근게 제일 위로)
  * git log --oneline --graph
  * git log —oneline —reverse : 정렬(옛날게 제일 위로)
  * git log {branch name}..{branch_name}
  * git log master..test : master 브랜치와 test 브랜치 사이의 로그만 출력
  * git log --graph --all --pretty=format:'%C(yellow)[%ad]%C(reset) %C(green)[%h]%C(reset) | %C(white)%s %C(bold red){{%an}}%C(reset) %C(blue)%d%C(reset)' --date=short

* ## push

  * git push -u origin main : “-u” 또는 “—set upstream”으로 현재 브랜치와 명시된 원격 브랜치 기본 연결, 연결 이후에는 git push만 해도 자동으로 연결된 원격 브랜치로 push됨
  * git push origin —delete {branch name} → 원격 브랜치 삭제
  * git push —set-upstream origin {변경할 branch name}
  * push 삭제(가장 마지막에 push한 commit을 지우고 싶을 때)
    1. git log : 삭제할 commit 확인
    2. git reset HEAD^ : 마지막 commit 삭제
    3. git push -f origin {branch name} : 원격지에 삭제한 commit을 push

* ## pull

  * git pull —no-rebase : merge 방식
  * git pull —rebase : rebase 방식

* ## restore

  * git restore --staged (파일명) : 파일을 staging area에서 working directory로 이동
  * git restore (파일명) : working directory에서도 제거

* ## checkout

  * git checkout HEAD^ : 현재 HEAD에서 1단계 전으로 이동
    * git checkout HEAD^^^ : 현재 HEAD에서 3단계 전으로 이동
  * git checkout HEAD~ : 현재 HEAD에서 1단계 전으로 이동
    * git checkout HEAD~~~ : 현재 HEAD에서 3단계 전으로 이동
    * git checkout HEAD~4 : 현재 HEAD에서 4단계 전으로 이동
  * git checkout {커밋 해쉬} : 입력한 커밋 해쉬로 HEAD 이동
  * git checkout - : HEAD 이동을 한단계 되돌리기
  * git checkout -b feature/TDEV-1027 origin/feature/TDEV-1027 : 원격 브랜치에서 로컬 브랜치로 생성하며 이동

* ## stash

  * git stash list : stash 목록 조회
  * git stash -m “메세지”
  * git stash pop
  * git stash apply : index 0번째 stash를 적용하고 stash 정보는 그대로(목록에 남아있음)
  * git stash apply {stash id} : {stash id}가 없을 경우 마지막 항목을 적용
    * git stash apply stash@{1}
  * git stash drop : 제일 최신 stash(index 0번째)를 stash list에서 삭제 시킴 
  * git stash drop {stash id} : {stash id}가 없을 경우 마지막 항목을 삭제
    * git stash drop stash@{1}
  * git stash clear : stash 항목들 초기화
  * git stash -u : Git에서 관리하지않는(Untracked files)까지 임시 저장함.
  * git stash save {dddd} : stash할떄 메세지도 함께 임시 저장
  * git stash branch {branch name} : 새로운 브랜치를 생성하면서 stash에 저장

* ## remote

  * git remote : 등록된 리모트 저장소 이름 조회
  * git remote -v : 등록된 저장소 이름과 URL 표시
  * git remote update : 모든 원격 브랜치를 업데이트하여 최신 상태로 갱신
  * git remote add {리모트 저장소 이름} {URL} : 새 리모트 저장소 추가
  * git remote show {리모트 저장소 이름} : 모든 리모트 경로의 branch와 기타 정보 조회
  * git remote rm {리모트 저장소 이름} : 리모트 경로 삭제

* ## reflog

* ## cherry-pick

  * git cherry-pick {다른 브랜치 commit hash}
  * git cherry-pick {다른 브랜치 commit hash} {다른 브랜치 commit hash} {다른 브랜치 commit hash}
  * git cherry-pick {다른 브랜치 commit hash}..{다른 브랜치 commit hash}
  * git cherry-pick --continue
  * git cherry-pick --abort

* ## fetch

* ## status

* ## init

* ## add

* ## diff
