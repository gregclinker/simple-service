#!/usr/bin/bash
git checkout master
git pull
git push -d origin $1
git checkout -b $1
echo "change for branch $1" >> README.md 
git add --all 
git commit -m "change for branch $1" 
git push origin $1
