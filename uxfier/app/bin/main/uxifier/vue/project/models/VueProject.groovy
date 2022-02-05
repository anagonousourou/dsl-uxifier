package vue.project.models

class VueProject {
    String name;


}

class PackageJson{
    String name;
    String version;
    @JsonProperty("private")
    public boolean myprivate;
}
