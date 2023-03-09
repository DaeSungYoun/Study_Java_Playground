# Config 설정

* ## includeIf

  * 폴더 별 Git 계정 분리하여 사용하기
    * global gitconfig에 설정

        ```text
        git config --global -e
        ```

        ```text
        아래 내용을 하단에 추가
        경로 설정 시 마지막에 "/" 필수

        [includeIf "gitdir:~/Documents/personal/"]
        path = .gitconfig-personal
        [includeIf "gitdir:~/Documents/company/"]
        path = .gitconfig-company
                    
        /Documents/personal/ 하위 경로의 프로젝트는 .gitconfig-personal 설정 값으로 우선 세팅
        /Documents/company/ 하위 경로의 프로젝트는 .gitconfig-company 설정 값으로 우선 세팅
        ```

    * .gitconfig-personal, .gitconfig-company 추가
      * .gitconfig 파일 찾아서 복제 후 이름 바꿨음

        ```text
        각각 config 파일에 원하는 값으로 입력 후 저장
        [user]
            name = {name}
            email = {email}
        ```

      * 설정 값이 제대로 되었는지 확인

        ```text
        git config user.name
        ```

* ## alias

  ```text
  [alias]
	hist = log --graph --all --pretty=format:'%C(yellow)[%ad]%C(reset) %C(green)[%h]%C(reset) | %C(white)%s %C(bold red){{%an}}%C(reset) %C(blue)%d%C(reset)' --date=short
  ```
  