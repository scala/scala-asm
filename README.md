
# A fork of ASM for the Scala Compiler

[<img src="https://img.shields.io/travis/scala/scala-asm.svg"/>](https://travis-ci.org/scala/scala-asm)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-asm.svg"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-asm)

This repository contains a fork of the ASM Java bytecode manipulation and analysis framework for the Scala compiler.


## Structure

This is a fork (a git clone) of the offical asm repository at https://gitlab.ow2.org/asm/asm.

Tags of the form `ASM_X_Y*` come from the upstream repo. Tags named `vX.Y.Z-scala-n` are created in this repo and used to release our fork under `"org.scala-lang.modules" % "scala-asm"`.

For each ASM release there's a corresponding branch in this repo, e.g., [`s-9.5`](https://github.com/scala/scala-asm/commits/s-9.5) for ASM 9.5. These branches start at the corresponding ASM release tag, our customizations are commits on top.

The following changes are applied:
  - The package name is changed to `scala.tools.asm`
  - Unused files are removed, including certain source files (e.g., package `org.objectweb.asm.xml`)
  - Customizations are applied in commits marked `[asm-cherry-pick]`

We start a new branch for each ASM release and re-apply our changes in order to keep track what we changed. Besides, it would be non-trivial to merge in changes from upstream, as we deleted many files and moved all sources to a different directory.


## Upgrading to a new version of ASM

### Set up remotes

To avoid confusion, let's not call the remote `origin`:

  - `git remote rename origin upstream-github`

If you haven't done so, add the upstream repostiory (https://gitlab.ow2.org/asm/asm) as a remote
  - `git remote add upstream-asm https://gitlab.ow2.org/asm/asm`

### Make the new version

Pull changes / tags from upstream
  - `git fetch upstream-asm --tags`

Review the upstream changes to see if there's anything that requires attention, updates to scripts in this repo, or similar
  - https://asm.ow2.io/versions.html
  - `git push upstream-github --tags`
  - https://github.com/scala/scala-asm/compare/ASM_9_4...ASM_9_5

Create a new branch `s-x.y.z` starting at the ASM tag for version x.y.z:
  - `git checkout -b s-9.5 ASM_9_5`

Get the script to prepare the sources
  - In the history, find the commit "Script for deleting unused files and preparing sources"
    - `git log --oneline upstream-github/s-9.4 | grep 'Script for deleting'`
      ```
      af285877 [asm-cherry-pick] Script for deleting unused files and preparing sources
      ```
  - Cherry-pick 
    - `git cherry-pick af285877`

Run the script
  - `./scripts/selectAndPrepareSources`

Push the branch, verify that the commits created by the script look correct
  - `git push --set-upstream upstream-github s-9.5`

Get the build infrastructure
  - In the history, find the commit "Build infrastructure" commit
    - `git log --oneline upstream-github/s-9.4 | grep 'Build infra'`
      ```
      06ff86de [asm-cherry-pick] Build infrastructure
      ```
  - Cherry-pick the commit
    - `git cherry-pick 06ff86de`

Check that the build works correctly
  - make sure you're on JDK 8 (`java -version`)
  - `sbt clean update test publishLocal`
  - `for f in target/*.jar; do unzip -l $f; done`

Cherry-pick all commits that went on top of the previous branch
  - Check the history of the previous branch
    - `git log --oneline --graph --first-parent upstream-github/s-9.4`
      ```
      * 3c41f954 (tag: v9.4.0-scala-1, upstream-github/s-9.4, SethTisue/s-9.4) [asm-cherry-pick] fix class names for experimental API check
      * f2e29937 [asm-cherry-pick] Call interpreter.copyInstruction consistently
      * 6a0507c2 [asm-cherry-pick] Ensure instructions belong only to one list
      * fc950013 [asm-cherry-pick] Build infrastructure
      * e5c4e053 Update `@links`, `@associates`
      * 011b74ef Update imports
      * f5172721 Update package clauses
      * e43e3ae9 Move sources to src/main/java/scala/tools/asm
      * 57f2b81b Convert all line endings to unix
      * 04f0c125 Remove unused files
      * 89cc9093 [asm-cherry-pick] Script for deleting unused files and preparing sources
      * d7888a87 (tag: ASM_9_4) Merge branch 'code-smells' into 'master'
      ```
  - Cherry-pick the commits that are not yet included (`fc950013..upstream-github/s-9.4` - note that the a range `A..B` includes `B` but not `A`)
    - Check your commit range
      - `git log --oneline fc950013..upstream-github/s-9.4`
    - Cherry-pick the commits
      - `git cherry-pick fc950013..upstream-github/s-9.4`

Rebase and clean up
  - Make sure that all commits have the the `[asm-cherry-pick]` flag
  - Clean up the history
  - If there are changes to the build infrastructure, squash them into the "Build infrastructure" commit

Check that the build works correctly
  - `sbt clean update test publishLocal`
  - `for f in target/*.jar; do unzip -l $f; done`

Push the branch to your fork, check everything
  - `git push YOUR_REMOTE_NAME s-9.5`

Push the branch to scala/scala-asm
  - `git push upstream-github s-9.5`
  - Check the build on travis: https://app.travis-ci.com/github/scala/scala-asm/builds

Create and push a tag to create a release
  - `git tag -s -m "scala-asm 9.5.0-scala-1" v9.5.0-scala-1 s-9.5`
  - `git push upstream-github --tags`
  - Check the build on travis: https://app.travis-ci.com/github/scala/scala-asm/builds
  - Check and release the staging repository on sonatype: https://oss.sonatype.org/
  - Make yourself a sandwich while waiting for artifacts to appear at https://repo1.maven.org/maven2/org/scala-lang/modules/scala-asm/
