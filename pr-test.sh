#!/usr/bin/bash
git push -d origin $1
git checkout main
git pull
git checkout -b $1
echo "XXXXXXXXXXXXXXXXXXX"
git status
echo "change for branch $1" >> README.md 
git add --all 
git commit -m "change for branch $1" 
git push origin $1
