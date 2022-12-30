tag=v1.3
#
./pr-clean-up.sh
#
./pr-test.sh patch/test11 ; ./pr-test.sh feature/test21 ; ./pr-test.sh release/test31
#
./pr-merge.sh patch/test11
./pr-merge.sh feature/test21
./pr-merge.sh release/test31
#
git checkout main ; git pull ; git tag $tag ; git push origin $tag
#
git log --merges --pretty=format:"%s" $(git describe --tags --abbrev=0 @^)..@
