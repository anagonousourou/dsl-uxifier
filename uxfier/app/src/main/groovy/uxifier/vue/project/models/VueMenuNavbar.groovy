package uxifier.vue.project.models

class VueMenuNavbar extends VueComponent {
    List<VueGeneratable> menus = new ArrayList<>()
    String applicationName

    void setApplicationName(String appname){
        this.applicationName = appname
    }
    @Override
    def registerDependencies(PackageJson packageJson) {
        println("Registering vaadin-core in ")
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return this.menus.add(vueGeneratable)
    }

    @Override
    def registerSelfInComponents() {
        //
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        FileContext.writer.write("""
import "@vaadin/app-layout";
import "@vaadin/app-layout/vaadin-drawer-toggle";
import "@vaadin/icon";
import "@vaadin/icons";
import "@vaadin/tabs";
""")
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""
 <vaadin-app-layout >""")
        if(this.applicationName != null && !this.applicationName.isBlank()){
            FileContext.writer.write("""<h1 slot="navbar">${this.applicationName}</h1>""")
        }

        FileContext.writer.write("""
    <vaadin-tabs slot="navbar">
"""
        )
        menus.forEach(m -> m.insertInTemplate())
        FileContext.writer.write("""
    </vaadin-tabs>
 </vaadin-app-layout>
"""
        )

    }

    @Override
    Object insertSelfInStyle() {
        return FileContext.writer.write("""
 vaadin-tabs {
   margin: auto;
 }
""")
    }
}

class VueMenuItemNavbar extends VueComponent {
    String label
    String link

    @Override
    def registerDependencies(PackageJson packageJson) {

    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write(""" 
    <vaadin-tab>
      <a tabindex="-1" > 
        ${this.label}
      </a>
    </vaadin-tab>""")
    }

}
