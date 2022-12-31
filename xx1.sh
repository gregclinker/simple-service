
branches=(	
		"patch/test11" 
		"patch/test12" 
		"feature/test21" 
		"feature/test22" 
		"release/test31"
	)
master=main

pullMain () {
	git checkout $master
	git pull
}

createBranch () {
	pullMain
	branch=$1
	file="test-change-${branch/\//-}"
	git branch -D $branch
	git push origin --delete $branch
	git pull
	git checkout -b $branch
	git status
	echo "change for branch $branch" > $file
	git add --all 
	git commit -m "change for branch $branch" 
	git push origin $branch

	echo "created branch $branch"
}

mergeBranch () {
	pullMain
	git checkout $1
	gh pr create --fill
	gh pr merge $1 -m

	echo "merged branch $1"
}

createTag () {
	pullMain
	git tag $1 
	git push origin $1

	echo "created tag $1"
}

cleanUp () {
	pullMain
	git rm test-change-* 
	git add --all 
	git commit -m "pr clean up" 
	git push origin main
	echo "finished clean up"
}

cleanUp
for branch in ${branches[@]}; do
  createBranch $branch
done
for branch in ${branches[@]}; do
  mergeBranch $branch
done
createTag $1
git log --merges --pretty=format:"%s" $(git describe --tags --abbrev=0 @^)..@
