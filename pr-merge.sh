#!/usr/bin/bash
git checkout main
git pull
git checkout $1
gh pr create --fill
gh pr merge $1 -m

