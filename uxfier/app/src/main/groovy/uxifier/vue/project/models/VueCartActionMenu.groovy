package uxifier.vue.project.models

class VueCartActionMenu implements VueGeneratable {
    String label
    boolean useIcon
    boolean displayCartCount
    VueCartPreview cartPreview

    VueCartActionMenu(String label, boolean displayCartCount, boolean useIcon) {
        this.label = label
        this.displayCartCount = displayCartCount
        this.useIcon = useIcon
    }

    def setCartPreview(VueCartPreview cartPreview){
        this.cartPreview = cartPreview
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
        if(this.cartPreview !=null){
            FileContext.writer.write("""<vaadin-tab @mouseover="upHere = true" @mouseleave="upHere = false">${this.label}""")
        }
        else{
            FileContext.writer.write("""<vaadin-tab>${this.label}""")
        }

        if(this.useIcon){
            FileContext.writer.write("""   <vaadin-icon icon="vaadin:cart"></vaadin-icon>\n""")
        }
        if (this.displayCartCount) {
            FileContext.writer.write(""" <span class="cart-item-num">( 0 )</span>\n""")
        }
        FileContext.writer.write("</vaadin-tab>")

        if(this.cartPreview !=null){
            this.cartPreview.insertInTemplate()
        }

    }
}
