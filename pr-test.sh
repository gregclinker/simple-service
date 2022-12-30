#!/usr/bin/bash
branch=$1
file="test-change-${branch/\//-}"
git checkout main
#git config pull.ff only
git pull
git branch -D $branch
git push origin --delete $branch
git pull
git checkout -b $branch
git status
echo "change for branch $branch" > $file
git add --all 
git commit -m "change for branch $branch" 
git push origin $branch
