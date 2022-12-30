git log --merges --pretty=format:"%s" $(git describe --tags --abbrev=0 @^)..@
