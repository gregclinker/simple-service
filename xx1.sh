./pr
tag=v1.2
#
pr-test.sh patch/test1 ; ./pr-test.sh feature/test2 ; ./pr-test.sh release/test3
#
pr-merge.sh patch/test1
pr-merge.sh feature/test2
pr-merge.sh release/test3
#
git checkout main ; git pull ; git tag $tag ; git push origin $tag
