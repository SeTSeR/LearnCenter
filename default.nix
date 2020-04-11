{ pkgs ? (import <nixpkgs> {}) }:
let pracPkg = { stdenv, jetbrains, jdk13, subversion, plantuml }:
stdenv.mkDerivation {
  name = "WT";
  unpackPhase = ["true"];
  installPhase = "mkdir -p $out/bin && touch $out/bin/grade_lab.py";

  buildInputs = [ jetbrains.idea-ultimate jdk13 subversion plantuml ];
};
pracDrv = pkgs.callPackage pracPkg {};
in pracDrv
