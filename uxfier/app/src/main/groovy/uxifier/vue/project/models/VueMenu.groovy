package uxifier.vue.project.models

class VueMenu extends VueComponent {
    String label
    String link
    String icon

    @Override
    def registerDependencies(PackageJson packageJson) {

    }



    @Override
    def insertInTemplate() {
        FileContext.writer.write(""" 
    <vaadin-tab>
      <a tabindex="-1">""")
                if(this.icon !=null && !this.icon.isBlank()){
                    FileContext.writer.write("""   <vaadin-icon icon="vaadin:${icon}"></vaadin-icon>""")
                }


    FileContext.writer.write("""<span>${this.label}</span>
      </a>
    </vaadin-tab>""")
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
    def registerSelfInComponents() {
        //
    }
}

class VueMenuBar extends VueComponent {
    List<VueGeneratable> menus = new ArrayList<>();
    private String applicationName
    void setApplicationName(String appname){
        this.applicationName = appname
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        println("Registering vaadin-core in ")
    }
    @Override
    Object insertInData() {
        return menus.forEach(m -> m.insertInData())
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
 <vaadin-app-layout primary-section="drawer">
    <vaadin-drawer-toggle slot="navbar"></vaadin-drawer-toggle>""")
                if(this.applicationName != null  && !this.applicationName.isBlank()){
                    FileContext.writer.write(""" <h1 slot="navbar">${this.applicationName}</h1>""")
                }
        FileContext.writer.write(  """
    <vaadin-tabs slot="drawer" orientation="vertical">
"""
        )
        menus.forEach(m -> m.insertInTemplate())
        FileContext.writer.write("""
    </vaadin-tabs>
 </vaadin-app-layout>
"""
        )

    }
}
