{ pkgs }: {
    deps = [
        pkgs.zip
        pkgs.graalvm17-ce
        pkgs.gradle
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}