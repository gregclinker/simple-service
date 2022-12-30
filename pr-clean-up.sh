#!/usr/bin/bash
git checkout main
git pull
git rm test-change-* 
git add --all 
git commit -m "pr clean up" 
git push origin main
