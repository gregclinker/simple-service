#!/usr/bin/bash
git checkout main
git pull
git push -d origin $1
git checkout -b $1
git status
echo "change for branch $1" >> README.md 
git add --all 
git commit -m "change for branch $1" 
git push origin $1
