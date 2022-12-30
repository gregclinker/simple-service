#!/usr/bin/bash
git checkout main
git pull
#git branch -D $1
git push origin --delete $1
git pull
git checkout -b $1
echo "XXXXXXXXXXXXXXXXXXX"
git status
echo "change for branch $1" >> README.md 
git add --all 
git commit -m "change for branch $1" 
git push origin $1
