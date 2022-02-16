package uxifier.vue.project.models

class VueActionMenu implements VueGeneratable{
    String label


    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""
<li class="menu-item">
    ${this.label}
</li>
""")
    }
}
