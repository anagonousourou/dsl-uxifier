package uxifier.vue.project.models

class VueCartActionMenu implements VueGeneratable{
    String label

    boolean displayCartCount

    VueCartActionMenu(String label, boolean displayCartCount) {
        this.label = label
        this.displayCartCount = displayCartCount
    }
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
        FileContext.writer.write("""<vaadin-tab>${this.label}""")
        if(this.displayCartCount){
            FileContext.writer.write("""<span class="cart-item-num">0</span>""")
        }
        FileContext.writer.write("</vaadin-tab>")

    }
}
