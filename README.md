# A fork of ASM for the Scala Compiler

[<img src="https://img.shields.io/travis/scala/scala-asm.svg"/>](https://travis-ci.org/scala/scala-asm)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-asm.svg"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-asm)

This repository contains a fork of the ASM Java bytecode manipulation and analysis framework for the Scala compiler.

## Upgrading to a new version of ASM

If you haven't done so, add the upstream repostiory (https://gitlab.ow2.org/asm/asm) as a remote
  - `git remote add upstream-asm https://gitlab.ow2.org/asm/asm`

Pull changes / tags from upstream
  - `git fetch upstream-asm --tags`

Create a new branch `s-x.y.z` starting at the ASM tag for version x.y.z:
  - `git checkout -b s-5.2 ASM_5_2`

Get the script to prepare the sources
  - Check the history of the previous branch
    - `git log --oneline s-5.1`
      ```
      679bfd52 [asm-cherry-pick] Script for deleting unused files and preparing sources
      3d2e7dc4 (tag: ASM_5_1) Fixed bug #317606.
      ```
  - Cherry-pick the commit "Script for deleting unused files and preparing sources"
    - `git cherry-pick 679bfd52`

Run the script
  - `./scripts/selectAndPrepareSources`

Push the branch, verify that the commits created by the script look correct
  - `git push --set-upstream YOUR_REMOTE_NAME s-5.2`

Get the build infrastructure
  - Check the history of the previous branch
    - `git log --oneline s-5.1`
      ```
      5caad9a8 [asm-cherry-pick] Build infrastructure
      1b2484be Remove trailing whitespace
      ```
  - Cherry-pick the commit "Build infrastructure"
    - `git cherry-pick 5caad9a8`

Check that the build works correctly
  - `sbt clean update test publishLocal`
  - `for f in target/*.jar; do unzip -l $f; done`

Cherry-pick all commits that went on top of the previous branch
  - Check the history of the previous branch
    - `git log --oneline --graph s-5.1`
      ```
      * cdf0cf00 (tag: v5.1.0-scala-2, upstream-github/s-5.1, lrytz-github/s-5.1, s-5.1) [asm-cherry-pick] Support Java 9 bytecode format
      * ee4ea3bc [asm-cherry-pick] Fill exception message for max String literal length
      * a42a7ae7 [asm-cherry-pick] Fix findItemByIndex in case of hash collisions
      * a85d4d57 [asm-cherry-pick] Call interpreter.copyInstruction consistently
      * 70822b0b [asm-cherry-pick] Multiple methods for initializing analysis values
      * 6afb7192 [asm-cherry-pick] Allow setting stack values in analysis frames
      * d8b5c2dc [asm-cherry-pick] Clarify the doc of MethodInsnNode.owner
      * ec767cad [asm-cherry-pick] Fix typos
      * ba0f8bc3 [asm-cherry-pick] Use MethodWriter to compute maxLocals / maxStack
      * 890377dd [asm-cherry-pick] Log names on method size overflow
      * 112da115 [asm-cherry-pick] Associate LabelNodes with their corresponding label
      * 107fd4da [asm-cherry-pick] Ensure instructions belong only to one list
      * 170dba1d [asm-cherry-pick] asm.CustomAttribute class
      * 5caad9a8 [asm-cherry-pick] Build infrastructure
      * 1b2484be Remove trailing whitespace
      * ae684acf Update `@links`, `@associates`
      * a7963e11 Update imports
      * a3cebd5e Update package clauses
      * 2b81e9ad Move sources to src/main/java/scala/tools/asm
      * f1cdde02 Convert all line endings to unix
      * 80c0bb7d Remove unused files
      * 679bfd52 [asm-cherry-pick] Script for deleting unused files and preparing sources
      * 3d2e7dc4 (tag: ASM_5_1) Fixed bug #317606.
      ```
  - Cherry-pick the commits that are not yet included (`5caad9a8..s-5.1` - note that the a range `A..B` includes `B` but not `A`)
    - Check your commit range
      - `git log --oneline 5caad9a8..s-5.1`
    - Cherry-pick the commits
      - `git cherry-pick 5caad9a8..s-5.1`

Rebase and clean up
  - Make sure that all commits have the the `[asm-cherry-pick]` flag
  - Clean up the history
  - If there are changes to the build infrastructure, squash them into the "Build infrastructure" commit

Check that the build works correctly
  - `sbt clean update test publishLocal`
  - `for f in target/*.jar; do unzip -l $f; done`

Push the branch to your fork, check everything
  - `git push`

Push the branch to scala/scala-asm
  - `git remote add upstream-github git@github.com:scala/scala-asm.git` if you didn't add the remote yet
  - `git push upstream-github s-5.2`
  - Check the build on travis: https://travis-ci.org/scala/scala-asm/builds

Create and push a tag to create a release
  - `git tag -s -m "scala-asm 5.2.0-scala-1" v5.2.0-scala-1 s-5.2`
  - `git push upstream-github --tags`
  - Check the build on travis: https://travis-ci.org/scala/scala-asm/builds
  - Check and release the staging repository on sonatype: https://oss.sonatype.org/
