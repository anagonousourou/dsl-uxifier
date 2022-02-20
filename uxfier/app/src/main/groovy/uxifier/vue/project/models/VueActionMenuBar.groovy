package uxifier.vue.project.models

class VueActionMenuBar implements VueGeneratable {

    List<VueGeneratable> actions = new ArrayList<>()

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
        FileContext.writer.write("""<ul class="pull-right list-unstyled list-inline"> """)
        actions.forEach(action -> action.insertInTemplate())
        FileContext.writer.write("</ul>")
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        actions.add(vueGeneratable)
    }
}
