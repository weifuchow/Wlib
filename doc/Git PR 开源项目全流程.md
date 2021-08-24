# Git PR 开源项目全流程

- fork 项目

- 设置upstream ，为正式项目的master地址

  - 如：git remote add upstream https://github.com/layer5io/meshsync.git (远程分支)

    ​        git remote -v 所有远程分支

- (fork 库)本地项目获取远程仓库的最新代码。

  - git pull upstream master

- 创建修复分支
  - git checkout -b feat/xx（切换分支 git checkout feat/xx）
- 将master的基线合并到当前分支中
  - git rebase master

- 提交修改，推送分支
  - git add * （文件添加暂存区）
  - git commit -s -m "xxx"
  - git push -f origin feat/xx

- github 主页增加pull request
  - 远程主库(master) << fork库（feat/xx）



- git命令补充
  - git reset （回退log)

  - stash 命令用法

    - git stash  保存暂存区，（后续可从另一个分支拿到）

    - https://www.cnblogs.com/zndxall/archive/2018/09/04/9586088.html